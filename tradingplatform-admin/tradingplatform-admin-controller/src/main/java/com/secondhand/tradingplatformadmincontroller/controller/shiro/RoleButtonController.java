package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleButton;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleButtonService;

/**
 * @description : RoleButton 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-04
 */
@RestController
@Api(value="/admin/roleButton", description="RoleButton 控制器")
@RequestMapping("/admin/roleButton")
public class RoleButtonController extends BaseController {

    @Autowired
    private RoleButtonService roleButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到roleButton的列表页面")
    public String toRoleButtonList(@ApiParam(name = "model", value = "Model") Model model) {
        return "roleButton/tabulation";
    }

    /**
     * @description : 跳转到修改roleButton的页面
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/{roleButtonId}/update.html")
    @ApiOperation(value = "/{roleButtonId}/update.html", notes = "跳转到修改页面")
    public String toUpdateRoleButton(@ApiParam(name = "model", value = "Model") Model model, @PathVariable(value = "roleButtonId") Long roleButtonId) {
        //静态注入要回显的数据
        Map<String, Object> roleButton = roleButtonService.mySelectMapById(roleButtonId);
        model.addAttribute("roleButton", roleButton);
        return "roleButton/newRoleButton";
    }

    /**
     * @description : 跳转到新增roleButton的页面
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateRoleButton(@ApiParam(name = "model", value = "Model") Model model) {
        return "roleButton/newRoleButton";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Button> getRoleButtonList(@ApiParam(name = "RoleButton", value = "RoleButton 实体类") @RequestBody RoleButton roleButton) {
            TableJson<Button> resJson = new TableJson<>();
            Page resPage = roleButton.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Button> buttonPage = new Page<Button>(current, size);
            buttonPage = roleButtonService.mySelectPageWithParam(buttonPage, roleButton);
            resJson.setRecordsTotal(buttonPage.getTotal());
            resJson.setData(buttonPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 获取可以增加的按钮
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query_enable_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_enable_create", notes="获取可以增加的按钮")
    public TableJson<Button> getEnableCreateList(@ApiParam(name = "RoleButton", value = "RoleButton 实体类") @RequestBody RoleButton roleButton) {
        TableJson<Button> resJson = new TableJson<>();
        Page resPage = roleButton.getPage();
        roleButton.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Button> buttonPage = new Page<Button>(current, size);
        buttonPage = roleButtonService.mySelectEnableCreatePage(buttonPage, roleButton);
        resJson.setRecordsTotal(buttonPage.getTotal());
        resJson.setData(buttonPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取roleButtonMap
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/get_map_by_id/{roleButtonId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{roleButtonId}", notes = "根据id获取roleButtonMap")
    public JsonResult<Map<String, Object>> getRoleButtonByIdForMap( @ApiParam(name = "id", value = "roleButtonId") @PathVariable("roleButtonId") Long roleButtonId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> roleButton = roleButtonService.mySelectMapById(roleButtonId);
            resJson.setData(roleButton);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据roleId和buttonId假删除roleButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据roleId和buttonId假删除roleButton")
    public JsonResult<RoleButton> fakeDeleteById(@ApiParam(name = "RoleButton", value = "RoleButton实体类") @RequestBody RoleButton roleButton){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleButton/delete");
            roleButtonService.myFakeDeleteByRoleButton(roleButton);
            resJson.setSuccess(true);
        }catch (UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据roleId和buttonIds批量假删除roleButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据roleId和buttonIds批量假删除roleButton")
    public JsonResult<RoleButton> fakeBatchDelete(@ApiParam(name = "parameter", value = "批量假删除的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleButton/batch_delete");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> buttonIds = (List<Integer>) parameter.get("buttonIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleButtonService.myFakeBatchDelete(roleId, buttonIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改roleButton
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改roleButton")
    public JsonResult<RoleButton> roleButtonCreateUpdate(@ApiParam(name = "RoleButton", value = "RoleButton实体类") @RequestBody RoleButton roleButton){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleButton> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleButton/create_update");
                roleButton = roleButtonService.myRoleButtonCreateUpdate(roleButton);
                resJson.setData(roleButton);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 批量新增roleButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/batch_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_create", notes = "批量新增roleButton")
    public JsonResult<RoleButton> roleButtonBatchCreate(@ApiParam(name = "parameter", value = "批量新增roleButton的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleButton/batch_create");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> buttonIds = (List<Integer>) parameter.get("buttonIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleButtonService.myRoleButtonBatchCreate(roleId, buttonIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
