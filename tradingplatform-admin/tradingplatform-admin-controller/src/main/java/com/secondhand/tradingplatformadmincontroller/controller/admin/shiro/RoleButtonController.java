package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
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
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.RoleButton;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.RoleButtonService;

/**
 * @description : RoleButton 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-04
 */
@Controller("adminRoleButtonController")
@Api(value="/admin/roleButton", description="RoleButton 控制器")
@RequestMapping("/admin/roleButton")
public class RoleButtonController extends BaseController {

    @Autowired
    private RoleButtonService roleButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/{roleId}/tabulation.html")
    @ApiOperation(value = "/{roleId}/tabulation.html", notes = "跳转到roleButton的列表页面")
    public String toRoleButtonList(@ApiParam(name = "model", value = "Model") Model model,
                                 @ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId) {

        //根据所选角色找菜单
        List<Button> roleButtons = roleButtonService.mySelectSelectedList(roleId);
        //静态注入
        //静态注入角色id
        model.addAttribute("roleId", roleId);
        //静态注入所选菜单
        model.addAttribute("roleButtons", roleButtons);
        return "system/role/roleButton";
    }

    /**
     * @description : 跳转到修改或新增roleButton的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{roleButtonId}/update.html", "/create.html"})
    @ApiOperation(value = "/{roleButtonId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyRoleButton(@ApiParam(name = "model", value = "Model") Model model,
                                   @ApiParam(name = "roleButtonId", value = "RoleButtonId") @PathVariable(value = "roleButtonId", required = false) Long roleButtonId) {

        Map<String, Object> roleButton = new HashMap<>();
        //判空
        if (roleButtonId != null) {
            //根据roleButtonId查找记录回显的数据
            roleButton = roleButtonService.mySelectMapById(roleButtonId);
        }
        //静态注入
        model.addAttribute("roleButton", roleButton);
        return "system/roleButton/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public JsonResult<Map<String, Object>> getRoleButtonByIdForMap( @ApiParam(name = "id", value = "roleButtonId") @PathVariable("roleButtonId") Long roleButtonId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> roleButton = roleButtonService.mySelectMapById(roleButtonId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
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
    @ResponseBody
    public JsonResult<RoleButton> fakeDeleteById(@ApiParam(name = "RoleButton", value = "RoleButton实体类") @RequestBody RoleButton roleButton){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleButton/delete");
            roleButtonService.myFakeDeleteByRoleButton(roleButton);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        }catch (UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
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
    @ResponseBody
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
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改roleButton
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @PostMapping(value = "/create_update/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update/{roleId}", notes = "新增或修改roleButton")
    @ResponseBody
    public JsonResult<RoleButton> roleButtonCreateUpdate(@ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId,
                                                     @ApiParam(name = "buttonIds", value = "按钮Ids") @RequestBody List<Long> buttonIds){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleButton/create_update");
            roleButtonService.myUpdateRoleButton(roleId, buttonIds);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
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
    @ResponseBody
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
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
