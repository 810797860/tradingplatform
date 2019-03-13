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
import com.secondhand.tradingplatformadminentity.entity.admin.system.Form;
import com.secondhand.tradingplatformadminservice.service.admin.system.FormService;

import javax.servlet.http.HttpSession;

/**
 * @description : Form 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-11
 */
@Controller("adminFormController")
@Api(value="/admin/form", description="Form 控制器")
@RequestMapping("/admin/form")
public class FormController extends BaseController {

    @Autowired
    private FormService formService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到form的列表页面")
    public String toFormList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                             @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/form/tabulation";
    }

    /**
     * @description : 跳转到修改或新增form的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{formId}/update.html", "/create.html"})
    @ApiOperation(value = "/{formId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyForm(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "formId", value = "FormId") @PathVariable(value = "formId", required = false) Long formId) {

        Map<String, Object> form = new HashMap<>();
        //判空
        if (formId != null) {
            //根据formId查找记录回显的数据
            form = formService.mySelectMapById(formId);
        }
        //静态注入
        model.addAttribute("form", form);
        return "system/form/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Form> getFormList(@ApiParam(name = "form", value = "Form 实体类") @RequestBody Form form) {
            TableJson<Form> resJson = new TableJson<>();
            Page resPage = form.getPage();
            form.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Form> formPage = new Page<Form>(current, size);
            formPage = formService.mySelectPageWithParam(formPage, form);
            resJson.setRecordsTotal(formPage.getTotal());
            resJson.setData(formPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取formMap
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/get_map_by_id/{formId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{formId}", notes = "根据id获取formMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getFormByIdForMap( @ApiParam(name = "id", value = "formId") @PathVariable("formId") Long formId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> form = formService.mySelectMapById(formId);
            resJson.setData(form);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除form
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除form")
    @ResponseBody
    public JsonResult<Form> fakeDeleteById(@ApiParam(name = "id", value = "formId") @RequestBody Long formId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Form> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/form/delete");
                formService.myFakeDeleteById(formId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除form
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除form")
    @ResponseBody
    public JsonResult<Form> fakeBatchDelete(@ApiParam(name = "ids", value = "formIds") @RequestBody List<Long> formIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Form> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/form/batch_delete");
                resJson.setSuccess(formService.myFakeBatchDelete(formIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改form
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改form")
    @ResponseBody
    public JsonResult<Form> formCreateUpdate(@ApiParam(name = "Form", value = "Form实体类") @RequestBody Form form){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Form> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/form/create_update");
                form = formService.myFormCreateUpdate(form);
                resJson.setData(form);
                resJson.setCode(200);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改form同是加权限（后台列表开发用）
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @PostMapping(value = "/create_update_with_resources", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update_with_resources", notes = "新增或修改form同是加权限（后台列表开发用）")
    public JsonResult<Form> formCreateUpdateWithResources(@ApiParam(name = "Form", value = "Form实体类") @RequestBody Form form){
        JsonResult<Form> resJson = new JsonResult<>();
        form = formService.myFormCreateUpdateWithResources(form);
        resJson.setData(form);
        resJson.setSuccess(true);
        return resJson;
    }
}
