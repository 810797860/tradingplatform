package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.baomidou.mybatisplus.plugins.Page;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;

import javax.servlet.http.HttpSession;

/**
 * @author : zhangjk
 * @description : Role 控制器
 * @since : Create in 2018-11-13
 */
@Controller("adminRoleController")
@Api(value = "/admin/role", description = "Role 控制器")
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到role的列表页面")
    public String toRoleList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        List<Role> roleList = roleService.mySelectAllList();
        Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
        //静态注入
        //静态注入所有的角色
        model.addAttribute("roleList", roleList);
        //注入用户id
        model.addAttribute("userId", userId);
        return "system/role/tabulation";
    }

    /**
     * @description : 跳转到修改或新增role的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{roleId}/update.html", "/create.html"})
    @ApiOperation(value = "/{roleId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyRole(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "roleId", value = "RoleId") @PathVariable(value = "roleId", required = false) Long roleId) {

        Map<String, Object> role = new HashMap<>();
        //判空
        if (roleId != null) {
            //根据roleId查找记录回显的数据
            role = roleService.mySelectMapById(roleId);
        }
        //静态注入
        model.addAttribute("role", role);
        return "system/role/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Role> getRoleList(@ApiParam(name = "Role", value = "Role 实体类") @RequestBody Role role) {
        TableJson<Role> resJson = new TableJson<>();
        Page resPage = role.getPage();
        role.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Role> rolePage = new Page<Role>(current, size);
        rolePage = roleService.mySelectPageWithParam(rolePage, role);
        resJson.setRecordsTotal(rolePage.getTotal());
        resJson.setData(rolePage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取roleMap
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/get_map_by_id/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{roleId}", notes = "根据id获取roleMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRoleByIdForMap(@ApiParam(name = "id", value = "roleId") @PathVariable("roleId") Long roleId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> role = roleService.mySelectMapById(roleId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(role);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除role
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/delete/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete/{roleId}", notes = "根据id假删除role")
    @ResponseBody
    public JsonResult<Role> fakeDeleteById(@ApiParam(name = "id", value = "roleId") @PathVariable("roleId") Long roleId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<Role> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/role/delete");
            roleService.myFakeDeleteById(roleId);
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
     * @description : 根据ids批量假删除role
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除role")
    @ResponseBody
    public JsonResult<Role> fakeBatchDelete(@ApiParam(name = "ids", value = "roleIds") @RequestBody List<Long> roleIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<Role> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/role/batch_delete");
            resJson.setSuccess(roleService.myFakeBatchDelete(roleIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改role
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改role")
    @ResponseBody
    public JsonResult<Role> roleCreateUpdate(@ApiParam(name = "Role", value = "Role实体类") @RequestBody Role role) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<Role> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/role/create_update");
            role = roleService.myRoleCreateUpdate(role);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(role);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 拖拽角色zTree
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/create_update_drag/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update_drag/{roleId}", notes = "拖拽角色zTree")
    @ResponseBody
    public JsonResult<Role> roleCreateUpdateDrag(@ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId, @ApiParam(name = "Role", value = "Role实体类") @RequestBody Role role) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<Role> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/role/create_update");
            role.setId(roleId);
            role = roleService.myRoleCreateUpdate(role);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(role);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
