package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleResources;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleResourcesService;

/**
 * @description : RoleResources 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-12
 */
@RestController
@Api(value="/admin/roleResources", description="RoleResources 控制器")
@RequestMapping("/admin/roleResources")
public class RoleResourcesController extends BaseController {

    @Autowired
    private RoleResourcesService roleResourcesService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到roleResources的列表页面")
    public String toRoleResourcesList(@ApiParam(name = "Model", value = "model") Model model) {
        return "roleResources/tabulation";
    }

    /**
     * @description : 跳转到修改roleResources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/{roleResourcesId}/update.html")
    @ApiOperation(value = "/{roleResourcesId}/update.html", notes = "跳转到修改页面")
    public String toUpdateRoleResources(@ApiParam(name = "Model", value = "model") Model model, @PathVariable(value = "roleResourcesId") Long roleResourcesId) {
        //静态注入要回显的数据
        Map<String, Object> roleResources = roleResourcesService.mySelectMapById(roleResourcesId);
        model.addAttribute("roleResources", roleResources);
        return "roleResources/newRoleResources";
    }

    /**
     * @description : 跳转到新增roleResources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateRoleResources(@ApiParam(name = "Model", value = "model") Model model) {
        return "roleResources/newRoleResources";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Resources> getResourcesList(@ApiParam(name = "RoleResources", value = "RoleResources 实体类") @RequestBody RoleResources roleResources) {
            TableJson<Resources> resJson = new TableJson<>();
            Page resPage = roleResources.getPage();
            roleResources.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
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
    @PostMapping(value = "/query_enable_create", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query_enable_create", notes="获取可以增加的权限")
    public TableJson<Resources> getEnableCreateList(@ApiParam(name = "RoleResources", value = "RoleResources 实体类") @RequestBody RoleResources roleResources) {
        TableJson<Resources> resJson = new TableJson<>();
        Page resPage = roleResources.getPage();
        roleResources.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
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
    @GetMapping(value = "/get_map_by_id/{roleResourcesId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{roleResourcesId}", notes = "根据id获取roleResourcesMap")
    public JsonResult<Map<String, Object>> getRoleResourcesByIdForMap( @ApiParam(name = "id", value = "roleResourcesId") @PathVariable("roleResourcesId") Long roleResourcesId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> roleResources = roleResourcesService.mySelectMapById(roleResourcesId);
            resJson.setData(roleResources);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据roleId和resourcesId假删除roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据roleId和resourcesId假删除roleResources")
    public JsonResult<RoleResources> fakeDeleteById(@ApiParam(name = "RoleResources", value = "RoleResources实体类") @RequestBody RoleResources roleResources){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleResources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleResources/delete");
                roleResourcesService.myFakeDeleteByRoleResources(roleResources);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
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
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据roleId和resourcesIds批量假删除roleResources")
    public JsonResult<RoleResources> fakeBatchDelete(@ApiParam(name = "parameter", value = "批量假删除的参数") @RequestBody Map<String, Object> parameter){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleResources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleResources/batch_delete");
                Long roleId = Long.valueOf(parameter.get("roleId").toString());
                List<Integer> resourcesIds = (List<Integer>) parameter.get("resourcesIds");
                //这里不判空了，让前端判
                resJson.setSuccess(roleResourcesService.myFakeBatchDelete(roleId, resourcesIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改roleResources")
    public JsonResult<RoleResources> roleResourcesCreateUpdate(@ApiParam(name = "RoleResources", value = "RoleResources实体类") @RequestBody RoleResources roleResources){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleResources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleResources/create_update");
                roleResources = roleResourcesService.myRoleResourcesCreateUpdate(roleResources);
                resJson.setData(roleResources);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
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
    @PostMapping(value = "/batch_create", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_create", notes = "批量新增roleResources")
    public JsonResult<RoleResources> roleResourcesBatchCreate(@ApiParam(name = "parameter", value = "批量新增roleResources的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleResources> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleResources/batch_create");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> resourcesIds = (List<Integer>) parameter.get("resourcesIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleResourcesService.myRoleResourcesBatchCreate(roleId, resourcesIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
