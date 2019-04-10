package com.secondhand.tradingplatformadmincontroller.controller.front.article.OtherCategories;

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
import com.secondhand.tradingplatformadminentity.entity.front.article.OtherCategories.OtherCategories;
import com.secondhand.tradingplatformadminservice.service.front.article.OtherCategories.OtherCategoriesService;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : OtherCategories 控制器
 * @since : Create in 2019-03-17
 */
@Controller("frontOtherCategoriesController")
@Api(value = "/front/otherCategories", description = "OtherCategories 控制器")
@RequestMapping("/front/otherCategories")
public class OtherCategoriesController extends BaseController {

    @Autowired
    private OtherCategoriesService otherCategoriesService;


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getOtherCategoriesList(@ApiParam(name = "otherCategories", value = "OtherCategories 实体类") @RequestBody OtherCategories otherCategories) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = otherCategories.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> otherCategoriesPage = new Page(current, size);
        otherCategoriesPage = otherCategoriesService.mySelectPageWithParam(otherCategoriesPage, otherCategories);
        resJson.setRecordsTotal(otherCategoriesPage.getTotal());
        resJson.setData(otherCategoriesPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取otherCategoriesMap
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @GetMapping(value = "/get_map_by_id/{otherCategoriesId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{otherCategoriesId}", notes = "根据id获取otherCategoriesMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getOtherCategoriesByIdForMap(@ApiParam(name = "id", value = "otherCategoriesId") @PathVariable("otherCategoriesId") Long otherCategoriesId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> otherCategories = otherCategoriesService.mySelectMapById(otherCategoriesId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(otherCategories);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除otherCategories
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除otherCategories")
    @ResponseBody
    public JsonResult<OtherCategories> fakeDeleteById(@ApiParam(name = "id", value = "otherCategoriesId") @RequestBody Long otherCategoriesId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<OtherCategories> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/otherCategories/delete");
            otherCategoriesService.myFakeDeleteById(otherCategoriesId);
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
     * @description : 根据ids批量假删除otherCategories
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除otherCategories")
    @ResponseBody
    public JsonResult<OtherCategories> fakeBatchDelete(@ApiParam(name = "ids", value = "otherCategoriesIds") @RequestBody List<Long> otherCategoriesIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<OtherCategories> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/otherCategories/batch_delete");
            resJson.setSuccess(otherCategoriesService.myFakeBatchDelete(otherCategoriesIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改otherCategories
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改otherCategories")
    @ResponseBody
    public JsonResult<OtherCategories> otherCategoriesCreateUpdate(@ApiParam(name = "otherCategories", value = "OtherCategories实体类") @RequestBody OtherCategories otherCategories) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<OtherCategories> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/otherCategories/create_update");
            Session session = subject.getSession();
            Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
            otherCategories.setUserId(userId);
            otherCategories.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            otherCategories = otherCategoriesService.myOtherCategoriesCreateUpdate(otherCategories);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(otherCategories);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
