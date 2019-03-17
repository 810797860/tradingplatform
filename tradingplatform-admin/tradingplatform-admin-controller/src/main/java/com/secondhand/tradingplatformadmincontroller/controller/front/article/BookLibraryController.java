package com.secondhand.tradingplatformadmincontroller.controller.front.article;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibraryService;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : BookLibrary 控制器
 * @since : Create in 2019-03-16
 */
@Controller("frontBookLibraryController")
@Api(value = "/front/bookLibrary", description = "BookLibrary 控制器")
@RequestMapping("/front/bookLibrary")
public class BookLibraryController extends BaseController {

    @Autowired
    private BookLibraryService bookLibraryService;


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getBookLibraryList(@ApiParam(name = "bookLibrary", value = "BookLibrary 实体类") @RequestBody BookLibrary bookLibrary) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = bookLibrary.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> bookLibraryPage = new Page(current, size);
        bookLibraryPage = bookLibraryService.mySelectPageWithParam(bookLibraryPage, bookLibrary);
        resJson.setRecordsTotal(bookLibraryPage.getTotal());
        resJson.setData(bookLibraryPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取bookLibraryMap
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @GetMapping(value = "/get_map_by_id/{bookLibraryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{bookLibraryId}", notes = "根据id获取bookLibraryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getBookLibraryByIdForMap(@ApiParam(name = "id", value = "bookLibraryId") @PathVariable("bookLibraryId") Long bookLibraryId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> bookLibrary = bookLibraryService.mySelectMapById(bookLibraryId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(bookLibrary);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> fakeDeleteById(@ApiParam(name = "id", value = "bookLibraryId") @RequestBody Long bookLibraryId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<BookLibrary> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/bookLibrary/delete");
            bookLibraryService.myFakeDeleteById(bookLibraryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> fakeBatchDelete(@ApiParam(name = "ids", value = "bookLibraryIds") @RequestBody List<Long> bookLibraryIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<BookLibrary> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/bookLibrary/batch_delete");
            resJson.setSuccess(bookLibraryService.myFakeBatchDelete(bookLibraryIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> bookLibraryCreateUpdate(@ApiParam(name = "bookLibrary", value = "BookLibrary实体类") @RequestBody BookLibrary bookLibrary) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<BookLibrary> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/bookLibrary/create_update");
            bookLibrary = bookLibraryService.myBookLibraryCreateUpdate(bookLibrary);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(bookLibrary);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
