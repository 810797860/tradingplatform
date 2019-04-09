package com.secondhand.tradingplatformadmincontroller.controller.front.important;

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
import com.secondhand.tradingplatformadminentity.entity.front.important.FrontSelectItem;
import com.secondhand.tradingplatformadminservice.service.front.important.FrontSelectItemService;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : FrontSelectItem 控制器
 * @since : Create in 2019-03-15
 */
@Controller("frontFrontSelectItemController")
@Api(value = "/front/frontSelectItem", description = "FrontSelectItem 控制器")
@RequestMapping("/front/frontSelectItem")
public class FrontSelectItemController extends BaseController {

    @Autowired
    private FrontSelectItemService frontSelectItemService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-15
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getFrontSelectItemList(@ApiParam(name = "frontSelectItem", value = "FrontSelectItem 实体类") @RequestBody FrontSelectItem frontSelectItem) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = frontSelectItem.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> frontSelectItemPage = new Page(current, size);
        frontSelectItemPage = frontSelectItemService.mySelectPageWithParam(frontSelectItemPage, frontSelectItem);
        resJson.setRecordsTotal(frontSelectItemPage.getTotal());
        resJson.setData(frontSelectItemPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取frontSelectItemMap
     * @author : zhangjk
     * @since : Create in 2019-03-15
     */
    @GetMapping(value = "/get_map_by_id/{frontSelectItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{frontSelectItemId}", notes = "根据id获取frontSelectItemMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getFrontSelectItemByIdForMap(@ApiParam(name = "id", value = "frontSelectItemId") @PathVariable("frontSelectItemId") Long frontSelectItemId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> frontSelectItem = frontSelectItemService.mySelectMapById(frontSelectItemId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(frontSelectItem);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除frontSelectItem
     * @author : zhangjk
     * @since : Create in 2019-03-15
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除frontSelectItem")
    @ResponseBody
    public JsonResult<FrontSelectItem> fakeDeleteById(@ApiParam(name = "id", value = "frontSelectItemId") @RequestBody Long frontSelectItemId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<FrontSelectItem> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/frontSelectItem/delete");
            frontSelectItemService.myFakeDeleteById(frontSelectItemId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除frontSelectItem
     * @author : zhangjk
     * @since : Create in 2019-03-15
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除frontSelectItem")
    @ResponseBody
    public JsonResult<FrontSelectItem> fakeBatchDelete(@ApiParam(name = "ids", value = "frontSelectItemIds") @RequestBody List<Long> frontSelectItemIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<FrontSelectItem> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/frontSelectItem/batch_delete");
            resJson.setSuccess(frontSelectItemService.myFakeBatchDelete(frontSelectItemIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改frontSelectItem
     * @author : zhangjk
     * @since : Create in 2019-03-15
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改frontSelectItem")
    @ResponseBody
    public JsonResult<FrontSelectItem> frontSelectItemCreateUpdate(@ApiParam(name = "frontSelectItem", value = "FrontSelectItem实体类") @RequestBody FrontSelectItem frontSelectItem) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<FrontSelectItem> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/frontSelectItem/create_update");
            frontSelectItem = frontSelectItemService.myFrontSelectItemCreateUpdate(frontSelectItem);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(frontSelectItem);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 通过pid获取List<frontSelectItem>
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/get_list_by_pid/{pid}")
    @ApiOperation(value = "/get_list_by_pid/{pid}", notes = "通过pid获取List<selectItem>")
    @ResponseBody
    public JsonResult<List<FrontSelectItem>> getFrontSelectItemByPidForList(@ApiParam(name = "pid", value = "frontSelectItem的父级id") @PathVariable("pid") Long pid) {
        JsonResult<List<FrontSelectItem>> resJson = new JsonResult<>();
        List<FrontSelectItem> frontSelectItemList = frontSelectItemService.myGetItemsByPid(pid);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(frontSelectItemList);
        resJson.setSuccess(true);
        return resJson;
    }
}
