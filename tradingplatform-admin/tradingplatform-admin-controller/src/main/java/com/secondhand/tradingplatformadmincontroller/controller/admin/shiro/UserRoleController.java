package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Role;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.MenuButtonService;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.RoleService;
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

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.UserRole;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.UserRoleService;

import javax.servlet.http.HttpSession;

/**
 * @description : UserRole 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-22
 */
@Controller
@Api(value="/admin/userRole", description="UserRole 控制器")
@RequestMapping("/admin/userRole")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MenuButtonService menuButtonService;

    @Autowired
    private RoleService roleService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到userRole的列表页面")
    public String toUserRoleList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                 @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/userRole/tabulation";
    }

    /**
     * @description : 跳转到修改或新增userRole的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{userId}/{userRoleId}/update.html", "/{userId}/create.html"})
    @ApiOperation(value = "/{userId}/{userRoleId}/update.html、/{userId}/create.html", notes = "跳转到修改或新增页面")
    public String toModifyUserRole(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "userId", value = "用户id") @PathVariable("userId") Long userId,
                               @ApiParam(name = "userRoleId", value = "用户角色id") @PathVariable(value = "userRoleId", required = false) Long userRoleId) {

        //静态注入
        //找所有的角色
        List<Role> allRoleList = roleService.mySelectAllList();
        //找所选的角色(现在默认只有一个)
        List<Role> selectedRoleList = userRoleService.mySelectSelectedList(userId);
        //静态注入用户id
        model.addAttribute("userId", userId);
        //静态注入所有的角色
        model.addAttribute("allRoleList", allRoleList);
        //静态注入所选的角色
        model.addAttribute("selectedRoleList", selectedRoleList);
        return "system/user/userRole";
    }

    /**
     * @description : 获取可以增加的角色
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PostMapping(value = "/query_enable_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_enable_create", notes="获取分页列表")
    @ResponseBody
    public TableJson<Role> getEnableCreateList(@ApiParam(name = "UserRole", value = "UserRole 实体类") @RequestBody UserRole userRole) {
        TableJson<Role> resJson = new TableJson<>();
        Page resPage = userRole.getPage();
        userRole.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Role> rolePage = new Page<Role>(current, size);
        rolePage = userRoleService.mySelectEnableCreatePage(rolePage, userRole);
        resJson.setRecordsTotal(rolePage.getTotal());
        resJson.setData(rolePage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Role> getRoleList(@ApiParam(name = "UserRole", value = "UserRole 实体类") @RequestBody UserRole userRole) {
            TableJson<Role> resJson = new TableJson<>();
            Page resPage = userRole.getPage();
            userRole.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Role> rolePage = new Page<Role>(current, size);
            rolePage = userRoleService.mySelectPageWithParam(rolePage, userRole);
            resJson.setRecordsTotal(rolePage.getTotal());
            resJson.setData(rolePage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取userRoleMap
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @GetMapping(value = "/get_map_by_id/{userRoleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{userRoleId}", notes = "根据id获取userRoleMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getUserRoleByIdForMap( @ApiParam(name = "id", value = "userRoleId") @PathVariable("userRoleId") Long userRoleId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> userRole = userRoleService.mySelectMapById(userRoleId);
            resJson.setData(userRole);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据userId和roleId假删除userRole
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据userId和roleId假删除userRole")
    @ResponseBody
    public JsonResult<UserRole> fakeDeleteByUserRole(@ApiParam(name = "UserRole", value = "UserRole实体类") @RequestBody UserRole userRole){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<UserRole> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/userRole/delete");
                userRoleService.myFakeDeleteByUserRole(userRole);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据userId和roleIds批量假删除userRole
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据userId和roleIds批量假删除userRole")
    @ResponseBody
    public JsonResult<UserRole> fakeBatchDelete(@ApiParam(name = "parameter", value = "批量假删除的参数") @RequestBody Map<String, Object> parameter){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<UserRole> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/userRole/batch_delete");
                Long userId = Long.valueOf(parameter.get("userId").toString());
                List<Integer> roleIds = (List<Integer>) parameter.get("roleIds");
                resJson.setSuccess(userRoleService.myFakeBatchDelete(userId, roleIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改userRole
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PostMapping(value = "/create_update/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update/{userId}", notes = "新增或修改userRole")
    @ResponseBody
    public JsonResult<UserRole> userRoleCreateUpdate(@ApiParam(name = "userId", value = "用户id") @PathVariable("userId") Long userId, @ApiParam(name = "roleIds", value = "角色id") @RequestBody List<Long> roleIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<UserRole> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/userRole/create_update");
                userRoleService.myUpdateUserRole(userId, roleIds);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
