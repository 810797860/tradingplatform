package com.secondhand.tradingplatformadmincontroller.controller.admin.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.MenuButtonService;
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
import com.secondhand.tradingplatformadminentity.entity.admin.system.SelectItem;
import com.secondhand.tradingplatformadminservice.service.admin.system.SelectItemService;

import javax.servlet.http.HttpSession;

/**
 * @description : SelectItem 控制器
 * @author : zhangjk
 * @since : Create in 2019-02-05
 */
@Controller
@Api(value="/admin/selectItem", description="SelectItem 控制器")
@RequestMapping("/admin/selectItem")
public class SelectItemController extends BaseController {

    @Autowired
    private SelectItemService selectItemService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到selectItem的列表页面")
    public String toSelectItemList(@ApiParam(name = "model", value = "Model") Model model,
                                   @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                   @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/selectItem/tabulation";
    }

    /**
     * @description : 跳转到修改或新增selectItem的页面
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @GetMapping(value = {"/{selectItemId}/update.html", "/create.html"})
    @ApiOperation(value = "/{selectItemId}/update.html、/create.html", notes = "跳转到修改或新增selectItem的页面")
    public String toModifySelectItem(@ApiParam(name = "model", value = "Model") Model model,
    @ApiParam(name = "selectItemId", value = "SelectItemId") @PathVariable(value = "selectItemId", required = false) Long selectItemId) {
        Map<String, Object> selectItem = new HashMap<>();
        //判空
        if (selectItemId != null) {
            selectItem = selectItemService.mySelectMapById(selectItemId);
        }
        String pid = "/admin/selectItem/tabulation.html?menuId=180";
        //静态注入
        //静态注入根据selectItemId查找记录回显的数据
        model.addAttribute("selectItem", selectItem);
        //静态注入父级枚举url
        model.addAttribute("pid", pid);
        return "system/selectItem/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSelectItemList(@ApiParam(name = "selectItem", value = "SelectItem 实体类") @RequestBody SelectItem selectItem) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = selectItem.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> selectItemPage = new Page(current, size);
            selectItemPage = selectItemService.mySelectPageWithParam(selectItemPage, selectItem);
            resJson.setRecordsTotal(selectItemPage.getTotal());
            resJson.setData(selectItemPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取selectItemMap
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @GetMapping(value = "/get_map_by_id/{selectItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{selectItemId}", notes = "根据id获取selectItemMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSelectItemByIdForMap( @ApiParam(name = "id", value = "selectItemId") @PathVariable("selectItemId") Long selectItemId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> selectItem = selectItemService.mySelectMapById(selectItemId);
            resJson.setData(selectItem);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除selectItem
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除selectItem")
    @ResponseBody
    public JsonResult<SelectItem> fakeDeleteById(@ApiParam(name = "id", value = "selectItemId") @RequestBody Long selectItemId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SelectItem> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/selectItem/delete");
                selectItemService.myFakeDeleteById(selectItemId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除selectItem
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除selectItem")
    @ResponseBody
    public JsonResult<SelectItem> fakeBatchDelete(@ApiParam(name = "ids", value = "selectItemIds") @RequestBody List<Long> selectItemIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SelectItem> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/selectItem/batch_delete");
                resJson.setSuccess(selectItemService.myFakeBatchDelete(selectItemIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改selectItem
     * @author : zhangjk
     * @since : Create in 2019-02-05
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改selectItem")
    @ResponseBody
    public JsonResult<SelectItem> selectItemCreateUpdate(@ApiParam(name = "selectItem", value = "SelectItem实体类") @RequestBody SelectItem selectItem){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SelectItem> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/selectItem/create_update");
                selectItem = selectItemService.mySelectItemCreateUpdate(selectItem);
                resJson.setData(selectItem);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 通过pid获取List<selectItem>
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/get_list_by_pid/{pid}")
    @ApiOperation(value = "/get_list_by_pid/{pid}", notes = "通过pid获取List<selectItem>")
    @ResponseBody
    public JsonResult<List<SelectItem>> getSelectItemByPidForList( @ApiParam(name = "pid", value = "selectItem的父级id") @PathVariable("pid") Long pid){
        JsonResult<List<SelectItem>> resJson = new JsonResult<>();
        List<SelectItem> selectItemList = selectItemService.myGetItemsByPid(pid);
        resJson.setData(selectItemList);
        resJson.setSuccess(true);
        return resJson;
    }
}
