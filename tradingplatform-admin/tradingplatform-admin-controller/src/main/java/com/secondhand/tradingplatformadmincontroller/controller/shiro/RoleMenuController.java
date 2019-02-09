package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuService;
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
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleMenu;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleMenuService;

import javax.servlet.http.HttpSession;

/**
 * @description : RoleMenu 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-02
 */
@Controller
@Api(value="/admin/roleMenu", description="RoleMenu 控制器")
@RequestMapping("/admin/roleMenu")
public class RoleMenuController extends BaseController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/{roleId}/tabulation.html")
    @ApiOperation(value = "/{roleId}/tabulation.html", notes = "跳转到roleMenu的列表页面")
    public String toRoleMenuList(@ApiParam(name = "model", value = "Model") Model model,
                                 @ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId) {

        List<Menu> allRoleMenus = menuService.mySelectAllList();
        //根据所选角色找菜单
        List<Menu> selectedRoleMenus = roleMenuService.mySelectSelectedList(roleId);
        //静态注入
        //静态注入角色id
        model.addAttribute("roleId", roleId);
        //静态注入所有菜单
        model.addAttribute("allRoleMenus", allRoleMenus);
        //静态注入所选菜单
        model.addAttribute("selectedRoleMenus", selectedRoleMenus);
        return "system/role/roleMenu";
    }

    /**
     * @description : 跳转到修改或新增roleMenu的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{roleMenuId}/update.html", "/create.html"})
    @ApiOperation(value = "/{roleMenuId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyRoleMenu(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "roleMenuId", value = "RoleMenuId") @PathVariable(value = "roleMenuId", required = false) Long roleMenuId) {

        Map<String, Object> roleMenu = new HashMap<>();
        //判空
        if (roleMenuId != null) {
            //根据roleMenuId查找记录回显的数据
            roleMenu = roleMenuService.mySelectMapById(roleMenuId);
        }
        //静态注入
        model.addAttribute("roleMenu", roleMenu);
        return "system/roleMenu/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Menu> getRoleMenuList(@ApiParam(name = "roleMenu", value = "RoleMenu 实体类") @RequestBody RoleMenu roleMenu,
                                           @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

            //根据roleId找menuId，放进去条件里
            Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
            roleMenu.setRoleId(roleId);
            TableJson<Menu> resJson = new TableJson<>();
            Page resPage = roleMenu.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Menu> menuPage = new Page<Menu>(current, size);
            menuPage = roleMenuService.mySelectPageWithParam(menuPage, roleMenu);
            resJson.setRecordsTotal(menuPage.getTotal());
            resJson.setData(menuPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 获取可以增加的菜单
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @PostMapping(value = "/query_enable_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_enable_create", notes="获取可以增加的菜单")
    @ResponseBody
    public TableJson<Menu> getEnableCreateList(@ApiParam(name = "RoleMenu", value = "RoleMenu 实体类") @RequestBody RoleMenu roleMenu) {
        TableJson<Menu> resJson = new TableJson<>();
        Page resPage = roleMenu.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Menu> menuPage = new Page<Menu>(current, size);
        menuPage = roleMenuService.mySelectEnableCreatePage(menuPage, roleMenu);
        resJson.setRecordsTotal(menuPage.getTotal());
        resJson.setData(menuPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取roleMenuMap
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @GetMapping(value = "/get_map_by_id/{roleMenuId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{roleMenuId}", notes = "根据id获取roleMenuMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRoleMenuByIdForMap( @ApiParam(name = "id", value = "roleMenuId") @PathVariable("roleMenuId") Long roleMenuId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> roleMenu = roleMenuService.mySelectMapById(roleMenuId);
            resJson.setData(roleMenu);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除roleMenu
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除roleMenu")
    @ResponseBody
    public JsonResult<RoleMenu> fakeDeleteById(@ApiParam(name = "id", value = "roleMenuId") @RequestBody Long roleMenuId, @ApiParam(name = "session", value = "客户端会话") HttpSession session){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleMenu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleMenu/delete");
                RoleMenu roleMenu = new RoleMenu();
                Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
                roleMenu.setMenuId(roleMenuId);
                roleMenu.setRoleId(roleId);
                roleMenuService.myFakeDeleteByRoleMenu(roleMenu);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据roleId和menuIds批量假删除roleMenu
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据roleId和menuIds批量假删除roleMenu")
    @ResponseBody
    public JsonResult<RoleMenu> fakeBatchDelete(@ApiParam(name = "parameter", value = "批量假删除的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleMenu> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleMenu/batch_delete");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> menuIds = (List<Integer>) parameter.get("menuIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleMenuService.myFakeBatchDelete(roleId, menuIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改roleMenu
     * @author : zhangjk
     * @since : Create in 2018-12-02
     */
    @PostMapping(value = "/create_update/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update/{roleId}", notes = "新增或修改roleMenu")
    @ResponseBody
    public JsonResult<RoleMenu> roleMenuCreateUpdate(@ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId,
                                                     @ApiParam(name = "menuIds", value = "菜单Ids") @RequestBody List<Long> menuIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RoleMenu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/roleMenu/create_update");
                roleMenuService.myUpdateRoleMenu(roleId, menuIds);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 批量新增roleMenu
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/batch_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_create", notes = "批量新增roleMenu")
    @ResponseBody
    public JsonResult<RoleMenu> roleMenuBatchCreate(@ApiParam(name = "parameter", value = "批量新增roleMenu的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RoleMenu> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/roleMenu/batch_create");
            Long roleId = Long.valueOf(parameter.get("roleId").toString());
            List<Integer> menuIds = (List<Integer>) parameter.get("menuIds");
            //这里不判空了，让前端判
            resJson.setSuccess(roleMenuService.myRoleMenuBatchCreate(roleId, menuIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
