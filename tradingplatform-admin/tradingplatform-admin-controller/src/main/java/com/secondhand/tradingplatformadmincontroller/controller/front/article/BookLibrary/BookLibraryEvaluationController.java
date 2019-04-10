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
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryEvaluation;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryEvaluationService;

import java.util.List;
import java.util.Map;

/**
 * @description : BookLibraryEvaluation 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontBookLibraryEvaluationController")
@Api(value="/front/bookLibraryEvaluation", description="BookLibraryEvaluation 控制器")
@RequestMapping("/front/bookLibraryEvaluation")
public class BookLibraryEvaluationController extends BaseController {

    @Autowired
    private BookLibraryEvaluationService bookLibraryEvaluationService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getBookLibraryEvaluationList(@ApiParam(name = "bookLibraryEvaluation", value = "BookLibraryEvaluation 实体类") @RequestBody BookLibraryEvaluation bookLibraryEvaluation) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = bookLibraryEvaluation.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> bookLibraryEvaluationPage = new Page(current, size);
            bookLibraryEvaluationPage = bookLibraryEvaluationService.mySelectPageWithParam(bookLibraryEvaluationPage, bookLibraryEvaluation);
            resJson.setRecordsTotal(bookLibraryEvaluationPage.getTotal());
            resJson.setData(bookLibraryEvaluationPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取bookLibraryEvaluationMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{bookLibraryEvaluationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{bookLibraryEvaluationId}", notes = "根据id获取bookLibraryEvaluationMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getBookLibraryEvaluationByIdForMap( @ApiParam(name = "id", value = "bookLibraryEvaluationId") @PathVariable("bookLibraryEvaluationId") Long bookLibraryEvaluationId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> bookLibraryEvaluation = bookLibraryEvaluationService.mySelectMapById(bookLibraryEvaluationId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(bookLibraryEvaluation);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除bookLibraryEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除bookLibraryEvaluation")
    @ResponseBody
    public JsonResult<BookLibraryEvaluation> fakeDeleteById(@ApiParam(name = "id", value = "bookLibraryEvaluationId") @RequestBody Long bookLibraryEvaluationId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryEvaluation/delete");
                bookLibraryEvaluationService.myFakeDeleteById(bookLibraryEvaluationId);
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
     * @description : 根据ids批量假删除bookLibraryEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除bookLibraryEvaluation")
    @ResponseBody
    public JsonResult<BookLibraryEvaluation> fakeBatchDelete(@ApiParam(name = "ids", value = "bookLibraryEvaluationIds") @RequestBody List<Long> bookLibraryEvaluationIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryEvaluation/batch_delete");
                resJson.setSuccess(bookLibraryEvaluationService.myFakeBatchDelete(bookLibraryEvaluationIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改bookLibraryEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改bookLibraryEvaluation")
    @ResponseBody
    public JsonResult<BookLibraryEvaluation> bookLibraryEvaluationCreateUpdate(@ApiParam(name = "bookLibraryEvaluation", value = "BookLibraryEvaluation实体类") @RequestBody BookLibraryEvaluation bookLibraryEvaluation){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryEvaluation/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                bookLibraryEvaluation.setUserId(userId);
                bookLibraryEvaluation.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
                bookLibraryEvaluation = bookLibraryEvaluationService.myBookLibraryEvaluationCreateUpdate(bookLibraryEvaluation);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(bookLibraryEvaluation);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
