package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Resources;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.RoleResources;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.RoleResourcesService;

/**
 * @author : zhangjk
 * @description : RoleResources 控制器
 * @since : Create in 2018-11-12
 */
@Controller("adminRoleResourcesController")
@Api(value = "/admin/roleResources", description = "RoleResources 控制器")
@RequestMapping("/admin/roleResources")
public class RoleResourcesController extends BaseController {

    @Autowired
    private RoleResourcesService roleResourcesService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/{roleId}/tabulation.html")
    @ApiOperation(value = "/{roleId}/tabulation.html", notes = "跳转到roleResources的列表页面")
    public String toRoleResourcesList(@ApiParam(name = "model", value = "Model") Model model,
                                      @ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId) {

        //根据所选角色找菜单
        List<Resources> selectedRoleResources = roleResourcesService.mySelectSelectedList(roleId);
        //静态注入
        //静态注入角色id
        model.addAttribute("roleId", roleId);
        //静态注入所选菜单
        model.addAttribute("selectedRoleResources", selectedRoleResources);
        return "system/role/roleResources";
    }

    /**
     * @description : 跳转到修改或新增roleResources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{roleResourcesId}/update.html", "/create.html"})
    @ApiOperation(value = "/{roleResourcesId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyRoleResources(@ApiParam(name = "model", value = "Model") Model model,
                                        @ApiParam(name = "roleResourcesId", value = "RoleResourcesId") @PathVariable(value = "roleResourcesId", required = false) Long roleResourcesId) {

        Map<String, Object> roleResources = new HashMap<>();
        //判空
        if (roleResourcesId != null) {
            //根据roleResourcesId查找记录回显的数据
            roleResources = roleResourcesService.mySelectMapById(roleResourcesId);
        }
        //静态注入
        model.addAttribute("roleResources", roleResources);
        return "system/roleResources/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Resources> getResourcesList(@ApiParam(name = "RoleResources", value = "RoleResources 实体类") @RequestBody RoleResources roleResources) {
        TableJson<Resources> resJson = new TableJson<>();
        Page resPage = roleResources.getPage();
        roleResources.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Resources> resourcesPage = new Page<Resources>(current, size);
        resourcesPage = roleResourcesService.mySelectPageWithParam(resourcesPage, roleResources);
        resJson.setRecordsTotal(resourcesPage.getTotal());
        resJson.setData(resourcesPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 获取可以增加的权限
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query_enable_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_enable_create", notes = "获取可以增加的权限")
    @ResponseBody
    public TableJson<Resources> getEnableCreateList(@ApiParam(name = "RoleResources", value = "RoleResources 实体类") @RequestBody RoleResources roleResources) {
        TableJson<Resources> resJson = new TableJson<>();
        Page resPage = roleResources.getPage();
        roleResources.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Resources> resourcesPage = new Page<Resources>(current, size);
        resourcesPage = roleResourcesService.mySelectEnableCreatePage(resourcesPage, roleResources);
        resJson.setRecordsTotal(resourcesPage.getTotal());
        resJson.setData(resourcesPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取roleResourcesMap
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/get_map_by_id/{roleResourcesId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{roleResourcesId}", notes = "根据id获取roleResourcesMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRoleResourcesByIdForMap(@ApiParam(name = "id", value = "roleResourcesId") @PathVariable("roleResourcesId") Long roleResourcesId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> roleResources = roleResourcesService.mySelectMapById(roleResourcesId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(roleResources);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据roleId和resourcesId假删除roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据roleId和resourcesId假删除roleResources")
    @ResponseBody
    public JsonResult<RoleResources> fakeDeleteById(@ApiParam(name = "RoleResources", value = "RoleResources实体类") @RequestBody RoleResources roleResources) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleResources> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/roleResources/delete");
            roleResourcesService.myFakeDeleteByRoleResources(roleResources);
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
     * @description : 根据roleId和resourcesIds批量假删除roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据roleId和resourcesIds批量假删除roleResources")
    @ResponseBody
    public JsonResult<RoleResources> fakeBatchDelete(@ApiParam(name = "parameter", value = "批量假删除的参数") @RequestBody Map<String, Object> parameter) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleResources> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/roleResources/batch_delete");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> resourcesIds = (List<Integer>) parameter.get("resourcesIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleResourcesService.myFakeBatchDelete(roleId, resourcesIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改roleResources
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @PostMapping(value = "/create_update/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update/{roleId}", notes = "新增或修改roleResources")
    @ResponseBody
    public JsonResult<RoleResources> roleResourcesCreateUpdate(@ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId,
                                                               @ApiParam(name = "resourcesIds", value = "按钮Ids") @RequestBody List<Long> resourcesIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleResources> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/roleResources/create_update");
            roleResourcesService.myUpdateRoleResources(roleId, resourcesIds);
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
     * @description : 批量新增roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/batch_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_create", notes = "批量新增roleResources")
    @ResponseBody
    public JsonResult<RoleResources> roleResourcesBatchCreate(@ApiParam(name = "parameter", value = "批量新增roleResources的参数") @RequestBody Map<String, Object> parameter) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleResources> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/roleResources/batch_create");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> resourcesIds = (List<Integer>) parameter.get("resourcesIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleResourcesService.myRoleResourcesBatchCreate(roleId, resourcesIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
