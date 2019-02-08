package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuButtonService;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminservice.service.shiro.ButtonService;

/**
 * @description : Button 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-04
 */
@Controller
@Api(value="/admin/button", description="Button 控制器")
@RequestMapping("/admin/button")
public class ButtonController extends BaseController {

    @Autowired
    private ButtonService buttonService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到button的列表页面")
    public String toButtonList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "menuId", value = "菜单id") Long menuId) {

        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/button/tabulation";
    }

    /**
     * @description : 跳转到修改或新增button的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{buttonId}/update.html", "/create.html"})
    @ApiOperation(value = "/{buttonId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyButton(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "buttonId", value = "ButtonId") @PathVariable(value = "buttonId", required = false) Long buttonId) {

        Map<String, Object> button = new HashMap<>();
        //判空
        if (buttonId != null) {
            //根据buttonId查找记录回显的数据
            button = buttonService.mySelectMapById(buttonId);
        }
        //静态注入
        model.addAttribute("button", button);
        return "system/button/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = {"/query", "/query/{menuId}"}, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query、/query/{menuId}", notes="获取分页列表")
    @ResponseBody
    public TableJson<Button> getButtonList(@ApiParam(name = "button", value = "Button 实体类") @RequestBody Button button,
                                           @ApiParam(name = "menuId", value = "菜单id") @PathVariable(name = "menuId", required = false) Long menuId) {
            TableJson<Button> resJson = new TableJson<>();
            Page resPage = button.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Button> buttonPage = new Page<Button>(current, size);
            buttonPage = buttonService.mySelectPageWithParam(buttonPage, button, menuId);
            resJson.setRecordsTotal(buttonPage.getTotal());
            resJson.setData(buttonPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取buttonMap
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/get_map_by_id/{buttonId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{buttonId}", notes = "根据id获取buttonMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getButtonByIdForMap( @ApiParam(name = "id", value = "buttonId") @PathVariable("buttonId") Long buttonId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> button = buttonService.mySelectMapById(buttonId);
            resJson.setData(button);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除button
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除button")
    @ResponseBody
    public JsonResult<Button> fakeDeleteById(@ApiParam(name = "id", value = "buttonId") @RequestBody Long buttonId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Button> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/button/delete");
                buttonService.myFakeDeleteById(buttonId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除button
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除button")
    @ResponseBody
    public JsonResult<Button> fakeBatchDelete(@ApiParam(name = "ids", value = "buttonIds") @RequestBody List<Long> buttonIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Button> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/button/batch_delete");
                resJson.setSuccess(buttonService.myFakeBatchDelete(buttonIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改button
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改button")
    @ResponseBody
    public JsonResult<Button> buttonCreateUpdate(@ApiParam(name = "Button", value = "Button实体类") @RequestBody Button button){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Button> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/button/create_update");
                button = buttonService.myButtonCreateUpdate(button);
                resJson.setData(button);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
