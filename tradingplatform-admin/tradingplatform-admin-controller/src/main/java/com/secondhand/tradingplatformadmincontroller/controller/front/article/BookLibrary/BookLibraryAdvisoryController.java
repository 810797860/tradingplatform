package com.secondhand.tradingplatformadmincontroller.controller.front.article.BookLibrary;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryAdvisory;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryAdvisoryService;

import java.util.List;
import java.util.Map;

/**
 * @description : BookLibraryAdvisory 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontBookLibraryAdvisoryController")
@Api(value="/front/bookLibraryAdvisory", description="BookLibraryAdvisory 控制器")
@RequestMapping("/front/bookLibraryAdvisory")
public class BookLibraryAdvisoryController extends BaseController {

    @Autowired
    private BookLibraryAdvisoryService bookLibraryAdvisoryService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getBookLibraryAdvisoryList(@ApiParam(name = "bookLibraryAdvisory", value = "BookLibraryAdvisory 实体类") @RequestBody BookLibraryAdvisory bookLibraryAdvisory) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = bookLibraryAdvisory.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> bookLibraryAdvisoryPage = new Page(current, size);
            bookLibraryAdvisoryPage = bookLibraryAdvisoryService.mySelectPageWithParam(bookLibraryAdvisoryPage, bookLibraryAdvisory);
            resJson.setRecordsTotal(bookLibraryAdvisoryPage.getTotal());
            resJson.setData(bookLibraryAdvisoryPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取bookLibraryAdvisoryMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{bookLibraryAdvisoryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{bookLibraryAdvisoryId}", notes = "根据id获取bookLibraryAdvisoryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getBookLibraryAdvisoryByIdForMap( @ApiParam(name = "id", value = "bookLibraryAdvisoryId") @PathVariable("bookLibraryAdvisoryId") Long bookLibraryAdvisoryId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> bookLibraryAdvisory = bookLibraryAdvisoryService.mySelectMapById(bookLibraryAdvisoryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(bookLibraryAdvisory);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除bookLibraryAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除bookLibraryAdvisory")
    @ResponseBody
    public JsonResult<BookLibraryAdvisory> fakeDeleteById(@ApiParam(name = "id", value = "bookLibraryAdvisoryId") @RequestBody Long bookLibraryAdvisoryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryAdvisory/delete");
                bookLibraryAdvisoryService.myFakeDeleteById(bookLibraryAdvisoryId);
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
     * @description : 根据ids批量假删除bookLibraryAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除bookLibraryAdvisory")
    @ResponseBody
    public JsonResult<BookLibraryAdvisory> fakeBatchDelete(@ApiParam(name = "ids", value = "bookLibraryAdvisoryIds") @RequestBody List<Long> bookLibraryAdvisoryIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryAdvisory/batch_delete");
                resJson.setSuccess(bookLibraryAdvisoryService.myFakeBatchDelete(bookLibraryAdvisoryIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改bookLibraryAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改bookLibraryAdvisory")
    @ResponseBody
    public JsonResult<BookLibraryAdvisory> bookLibraryAdvisoryCreateUpdate(@ApiParam(name = "bookLibraryAdvisory", value = "BookLibraryAdvisory实体类") @RequestBody BookLibraryAdvisory bookLibraryAdvisory){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryAdvisory/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                bookLibraryAdvisory.setUserId(userId);
                bookLibraryAdvisory.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
                bookLibraryAdvisory = bookLibraryAdvisoryService.myBookLibraryAdvisoryCreateUpdate(bookLibraryAdvisory);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(bookLibraryAdvisory);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
