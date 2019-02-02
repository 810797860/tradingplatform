package com.secondhand.tradingplatformadmincontroller.controller.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuButtonService;
import com.secondhand.tradingplatformadminservice.service.system.SelectItemService;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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
import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformadminservice.service.system.FormFieldService;

/**
 * @description : FormField 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-09
 */
@Controller
@Api(value="/admin/formField", description="FormField 控制器")
@RequestMapping("/admin/formField")
public class FormFieldController extends BaseController {

    @Autowired
    private FormFieldService formFieldService;

    @Autowired
    private SelectItemService selectItemService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到form的列表页面")
    public String toFormList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                             @ApiParam(name = "formId", value = "表单id") Long formId) {

        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId);
        List<SelectItem> showTypes = selectItemService.getItemsByPid(SystemSelectItem.DISPLAY_TYPE);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        //注入表单id
        model.addAttribute("formId", formId);
        //注入展示类型
        model.addAttribute("showTypes", showTypes);
        return "system/formField/tabulation";
    }

    /**
     * @description : 跳转到修改或新增formField的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{formFieldId}/update.html", "/create.html"})
    @ApiOperation(value = "/{formFieldId}/update.html、/create.html", notes = "跳转到修改或新增formField页面")
    public String toModifyForm(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "formFieldId", value = "FormId") @PathVariable(value = "formFieldId", required = false) Long formFieldId) {

        Map<String, Object> formField = new HashMap<>();
        //判空
        if (formFieldId != null) {
            formField = formFieldService.mySelectMapById(formFieldId);
        }
        //静态注入展示类型及字段类型
        List<SelectItem> displayType = selectItemService.getAllItemsByPid(SystemSelectItem.DISPLAY_TYPE);
        //静态注入
        //根据formFieldId查找记录回显的数据
        model.addAttribute("formField", formField);
        //静态注入展示类型及字段类型
        model.addAttribute("displayType", displayType);
        return "system/formField/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getFormFieldList(@ApiParam(name = "FormField", value = "FormField 实体类") @RequestBody FormField formField) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = formField.getPage();
            formField.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> formFieldPage = new Page(current, size);
            formFieldPage = formFieldService.mySelectPageWithParam(formFieldPage, formField);
            resJson.setRecordsTotal(formFieldPage.getTotal());
            resJson.setData(formFieldPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取formFieldMap
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/get_map_by_id/{formFieldId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{formFieldId}", notes = "根据id获取formFieldMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getFormFieldByIdForMap( @ApiParam(name = "id", value = "formFieldId") @PathVariable("formFieldId") Long formFieldId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> formField = formFieldService.mySelectMapById(formFieldId);
            resJson.setData(formField);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除formField
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除formField")
    @ResponseBody
    public JsonResult<FormField> fakeDeleteById(@ApiParam(name = "id", value = "formFieldId") @RequestBody Long formFieldId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<FormField> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/formField/delete");
                formFieldService.myFakeDeleteById(formFieldId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除formField
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除formField")
    @ResponseBody
    public JsonResult<FormField> fakeBatchDelete(@ApiParam(name = "ids", value = "formFieldIds") @RequestBody List<Long> formFieldIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<FormField> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/formField/batch_delete");
                resJson.setSuccess(formFieldService.myFakeBatchDelete(formFieldIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改formField
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改formField")
    @ResponseBody
    public JsonResult<FormField> formFieldCreateUpdate(@ApiParam(name = "FormField", value = "FormField实体类") @RequestBody FormField formField){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<FormField> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/formField/create_update");
                formField = formFieldService.myFormFieldCreateUpdate(formField);
                resJson.setData(formField);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据表单id更新FormField(开发后台时方便自己用的)
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PostMapping(value = "/update_by_form_id", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/update_by_form_id", notes = "根据表单id更新FormField")
    @ResponseBody
    public JsonResult<FormField> formFieldUpdateByFormId(@ApiParam(name = "formId", value = "表单id") @RequestBody Long formId){
        //因为是开发后台时方便自己用的，所以就不检测是否有权限了
        JsonResult<FormField> resJson = new JsonResult<>();
        resJson.setSuccess(formFieldService.formFieldUpdateByFormId(formId));
        return resJson;
    }
}
