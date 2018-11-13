package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
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
    public String toRoleResourcesList(Model model) {
        return "roleResources/tabulation";
    }

    /**
     * @description : 跳转到修改roleResources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/{roleResourcesId}/update.html")
    @ApiOperation(value = "/{roleResourcesId}/update.html", notes = "跳转到修改页面")
    public String toUpdateRoleResources(Model model, @PathVariable(value = "roleResourcesId") Long roleResourcesId) {
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
    public String toCreateRoleResources(Model model) {
        return "roleResources/newRoleResources";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<RoleResources> getRoleResourcesList(@ApiParam(name = "RoleResources", value = "RoleResources 实体类") @RequestBody RoleResources roleResources) {
            TableJson<RoleResources> resJson = new TableJson<>();
            Page resPage = roleResources.getPage();
            roleResources.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<RoleResources> roleResourcesPage = new Page<RoleResources>(current, size);
            roleResourcesPage = roleResourcesService.mySelectPageWithParam(roleResourcesPage, roleResources);
            resJson.setRecordsTotal(roleResourcesPage.getTotal());
            resJson.setData(roleResourcesPage.getRecords());
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
     * @description : 根据id假删除roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除roleResources")
    public JsonResult<RoleResources> fakeDeleteById(@ApiParam(name = "id", value = "roleResourcesId") @RequestBody Long roleResourcesId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleResources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleResources/delete");
                roleResourcesService.myFakeDeleteById(roleResourcesId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除roleResources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除roleResources")
    public JsonResult<RoleResources> fakeBatchDelete(@ApiParam(name = "ids", value = "roleResourcesIds") @RequestBody List<Long> roleResourcesIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleResources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleResources/batch_delete");
                resJson.setSuccess(roleResourcesService.myFakeBatchDelete(roleResourcesIds));
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
}