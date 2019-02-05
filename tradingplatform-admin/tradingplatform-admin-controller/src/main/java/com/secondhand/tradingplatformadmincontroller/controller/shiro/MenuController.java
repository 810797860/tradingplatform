package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleMenu;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuButtonService;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleMenuService;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
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
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuService;

import javax.servlet.http.HttpSession;

/**
 * @description : Menu 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-29
 */
@Controller
@Api(value="/admin/menu", description="Menu 控制器")
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuButtonService menuButtonService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到menu的列表页面")
    public String toMenuList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                             @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        //找出roleId并放进去搜索条件
        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId);
        List<Menu> menuList = roleMenuService.mySelectListWithRoleId(roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        //菜单列表静态注入
        model.addAttribute("menuList", menuList);
        return "system/menu/tabulation";
    }

    /**
     * @description : 跳转到修改或新增menu的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{menuId}/update.html", "/create.html"})
    @ApiOperation(value = "/{menuId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyMenu(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "menuId", value = "MenuId") @PathVariable(value = "menuId", required = false) Long menuId) {

        Map<String, Object> menu = new HashMap<>();
        //判空
        if (menuId != null) {
            //根据menuId查找记录回显的数据
            menu = menuService.mySelectMapById(menuId);
        }
        //静态注入
        model.addAttribute("menu", menu);
        return "system/menu/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Menu> getMenuList(@ApiParam(name = "menu", value = "Menu 实体类") @RequestBody Menu menu) {
            TableJson<Menu> resJson = new TableJson<>();
            Page resPage = menu.getPage();
            resPage.setOrderByField("id");
            resPage.setAsc(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Menu> menuPage = new Page<Menu>(current, size);
            menuPage = menuService.mySelectPageWithParam(menuPage, menu);
            resJson.setRecordsTotal(menuPage.getTotal());
            resJson.setData(menuPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取menuMap
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @GetMapping(value = "/get_map_by_id/{menuId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{menuId}", notes = "根据id获取menuMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getMenuByIdForMap( @ApiParam(name = "id", value = "menuId") @PathVariable("menuId") Long menuId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> menu = menuService.mySelectMapById(menuId);
            resJson.setData(menu);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除menu
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除menu")
    @ResponseBody
    public JsonResult<Menu> fakeDeleteById(@ApiParam(name = "id", value = "menuId") @RequestBody Long menuId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Menu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/menu/delete");
                menuService.myFakeDeleteById(menuId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除menu
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除menu")
    @ResponseBody
    public JsonResult<Menu> fakeBatchDelete(@ApiParam(name = "ids", value = "menuIds") @RequestBody List<Long> menuIds, @ApiParam(name = "session", value = "客户端会话") HttpSession session){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Menu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/menu/batch_delete");
                //实际情况为单条删除
                //把该条记录先关的roleMenu记录也删除掉
                RoleMenu roleMenu = new RoleMenu();
                Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
                Long menuId = menuIds.get(0);
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuService.myFakeDeleteByRoleMenu(roleMenu);
                resJson.setSuccess(menuService.myFakeBatchDelete(menuIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改menu
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改menu")
    @ResponseBody
    public JsonResult<Menu> menuCreateUpdate(@ApiParam(name = "Menu", value = "Menu实体类") @RequestBody Menu menu, @ApiParam(name = "session", value = "客户端会话") HttpSession session){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Menu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/menu/create_update");
                //先判断是否为新增的情况
                Boolean ifInsert = menu.getId() == null ? true : false;
                menu = menuService.myMenuCreateUpdate(menu);
                //给角色配上菜单
                //判断是否为新增的情况
                if (ifInsert) {
                    //先找出角色id
                    Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menu.getId());
                    roleMenuService.myInsert(roleMenu);

                    //默认给管理员也添加
                    if (roleId != MagicalValue.ADMINISTRATOR_ID){
                        RoleMenu adminRoleMenu = new RoleMenu();
                        adminRoleMenu.setRoleId(MagicalValue.ADMINISTRATOR_ID);
                        adminRoleMenu.setMenuId(menu.getId());
                        roleMenuService.myInsert(roleMenu);
                    }
                }
                resJson.setData(menu);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
