package com.secondhand.tradingplatformadmincontroller.controller.front.article.BookLibrary;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryCollection;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryCollectionService;

import java.util.List;
import java.util.Map;

/**
 * @description : BookLibraryCollection 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontBookLibraryCollectionController")
@Api(value="/front/bookLibraryCollection", description="BookLibraryCollection 控制器")
@RequestMapping("/front/bookLibraryCollection")
public class BookLibraryCollectionController extends BaseController {

    @Autowired
    private BookLibraryCollectionService bookLibraryCollectionService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getBookLibraryCollectionList(@ApiParam(name = "bookLibraryCollection", value = "BookLibraryCollection 实体类") @RequestBody BookLibraryCollection bookLibraryCollection) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = bookLibraryCollection.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> bookLibraryCollectionPage = new Page(current, size);
            bookLibraryCollectionPage = bookLibraryCollectionService.mySelectPageWithParam(bookLibraryCollectionPage, bookLibraryCollection);
            resJson.setRecordsTotal(bookLibraryCollectionPage.getTotal());
            resJson.setData(bookLibraryCollectionPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 用户获取收藏bookLibrary的ids列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/query_collection", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_collection", notes="用户获取收藏bookLibrary的ids列表")
    @ResponseBody
    public JsonResult<List<Object>> getBookLibraryAdvisoryCollectionList() {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<List<Object>> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/bookLibraryCollection/query_collection");
            Session session = subject.getSession();
            Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
            resJson.setData(bookLibraryCollectionService.mySelectCollectionList(userId));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        }catch (UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 通过id获取bookLibraryCollectionMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{bookLibraryCollectionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{bookLibraryCollectionId}", notes = "根据id获取bookLibraryCollectionMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getBookLibraryCollectionByIdForMap( @ApiParam(name = "id", value = "bookLibraryCollectionId") @PathVariable("bookLibraryCollectionId") Long bookLibraryCollectionId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> bookLibraryCollection = bookLibraryCollectionService.mySelectMapById(bookLibraryCollectionId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(bookLibraryCollection);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除bookLibraryCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除bookLibraryCollection")
    @ResponseBody
    public JsonResult<BookLibraryCollection> fakeDeleteById(@ApiParam(name = "id", value = "bookLibraryId") @RequestBody Long bookLibraryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryCollection/delete");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                bookLibraryCollectionService.myFakeDeleteById(bookLibraryId, userId);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除bookLibraryCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除bookLibraryCollection")
    @ResponseBody
    public JsonResult<BookLibraryCollection> fakeBatchDelete(@ApiParam(name = "ids", value = "bookLibraryCollectionIds") @RequestBody List<Long> bookLibraryCollectionIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryCollection/batch_delete");
                resJson.setSuccess(bookLibraryCollectionService.myFakeBatchDelete(bookLibraryCollectionIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改bookLibraryCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改bookLibraryCollection")
    @ResponseBody
    public JsonResult<BookLibraryCollection> bookLibraryCollectionCreateUpdate(@ApiParam(name = "bookLibraryCollection", value = "BookLibraryCollection实体类") @RequestBody BookLibraryCollection bookLibraryCollection){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryCollection/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                bookLibraryCollection.setUserId(userId);
                bookLibraryCollection = bookLibraryCollectionService.myBookLibraryCollectionCreateUpdate(bookLibraryCollection);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(bookLibraryCollection);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
