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
import com.secondhand.tradingplatformadminentity.entity.shiro.MenuButton;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuButtonService;

import javax.servlet.http.HttpSession;

/**
 * @description : MenuButton 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-06
 */
@RestController
@Api(value="/admin/menuButton", description="MenuButton 控制器")
@RequestMapping("/admin/menuButton")
public class MenuButtonController extends BaseController {

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-12-06
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到menuButton的列表页面")
    public String toMenuButtonList(@ApiParam(name = "model", value = "Model") Model model) {
        return "menuButton/tabulation";
    }

    /**
     * @description : 跳转到修改menuButton的页面
     * @author : zhangjk
     * @since : Create in 2018-12-06
     */
    @GetMapping(value = "/{menuButtonId}/update.html")
    @ApiOperation(value = "/{menuButtonId}/update.html", notes = "跳转到修改页面")
    public String toUpdateMenuButton(@ApiParam(name = "model", value = "Model") Model model, @PathVariable(value = "menuButtonId") Long menuButtonId) {
        //静态注入要回显的数据
        Map<String, Object> menuButton = menuButtonService.mySelectMapById(menuButtonId);
        model.addAttribute("menuButton", menuButton);
        return "menuButton/newMenuButton";
    }

    /**
     * @description : 跳转到新增menuButton的页面
     * @author : zhangjk
     * @since : Create in 2018-12-06
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateMenuButton(@ApiParam(name = "model", value = "Model") Model model) {
        return "menuButton/newMenuButton";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Button> getButtonList(@ApiParam(name = "MenuButton", value = "MenuButton 实体类") @RequestBody MenuButton menuButton) {
        TableJson<Button> resJson = new TableJson<>();
        Page resPage = menuButton.getPage();
        menuButton.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Button> buttonPage = new Page<Button>(current, size);
        buttonPage = menuButtonService.mySelectPageWithParam(buttonPage, menuButton);
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
    public TableJson<Button> getEnableCreateList(HttpSession session, @ApiParam(name = "MenuButton", value = "MenuButton 实体类") @RequestBody MenuButton menuButton) {
        TableJson<Button> resJson = new TableJson<>();
        Page resPage = menuButton.getPage();
        menuButton.setDeleted(false);
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Button> buttonPage = new Page<Button>(current, size);
        buttonPage = menuButtonService.mySelectEnableCreatePage(buttonPage, menuButton);
        resJson.setRecordsTotal(buttonPage.getTotal());
        resJson.setData(buttonPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取menuButtonMap
     * @author : zhangjk
     * @since : Create in 2018-12-06
     */
    @GetMapping(value = "/get_map_by_id/{menuButtonId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{menuButtonId}", notes = "根据id获取menuButtonMap")
    public JsonResult<Map<String, Object>> getMenuButtonByIdForMap( @ApiParam(name = "id", value = "menuButtonId") @PathVariable("menuButtonId") Long menuButtonId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> menuButton = menuButtonService.mySelectMapById(menuButtonId);
            resJson.setData(menuButton);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据menuId和buttonId假删除menuButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据menuId和buttonId假删除menuButton")
    public JsonResult<MenuButton> fakeDeleteById(@ApiParam(name = "MenuButton", value = "MenuButton实体类") @RequestBody MenuButton menuButton){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<MenuButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/menuButton/delete");
            menuButtonService.myFakeDeleteByMenuButton(menuButton);
            resJson.setSuccess(true);
        }catch (UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据menuId和buttonIds批量假删除menuButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据menuId和buttonIds批量假删除menuButton")
    public JsonResult<MenuButton> fakeBatchDelete(@ApiParam(name = "parameter", value = "批量假删除的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<MenuButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/menuButton/batch_delete");
            Long menuId = Long.valueOf(parameter.get("menuId").toString());
            List<Integer> buttonIds = (List<Integer>) parameter.get("buttonIds");
            //这里不判空了，让前端判
            resJson.setSuccess(menuButtonService.myFakeBatchDelete(menuId, buttonIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改menuButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改menuButton")
    public JsonResult<MenuButton> menuButtonCreateUpdate(@ApiParam(name = "MenuButton", value = "MenuButton实体类") @RequestBody MenuButton menuButton){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<MenuButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/menuButton/create_update");
            menuButton = menuButtonService.myMenuButtonCreateUpdate(menuButton);
            resJson.setData(menuButton);
            resJson.setSuccess(true);
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 批量新增menuButton
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/batch_create", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_create", notes = "批量新增menuButton")
    public JsonResult<MenuButton> menuButtonBatchCreate(@ApiParam(name = "parameter", value = "批量新增menuButton的参数") @RequestBody Map<String, Object> parameter){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<MenuButton> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/menuButton/batch_create");
            Long menuId = Long.valueOf(parameter.get("menuId").toString());
            List<Integer> buttonIds = (List<Integer>) parameter.get("buttonIds");
            //这里不判空了，让前端判
            resJson.setSuccess(menuButtonService.myMenuButtonBatchCreate(menuId, buttonIds));
        }catch(UnauthorizedException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
