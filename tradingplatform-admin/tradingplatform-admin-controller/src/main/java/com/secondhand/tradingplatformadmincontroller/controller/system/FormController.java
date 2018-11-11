package com.secondhand.tradingplatformadmincontroller.controller.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformadminservice.service.system.FormService;

/**
 * @description : Form 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-11
 */
@RestController
@Api(value="/admin/form", description="Form 控制器")
@RequestMapping("/admin/form")
public class FormController extends BaseController {

    @Autowired
    private FormService formService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到form的列表页面")
    public String toFormList(Model model) {
        return "form/tabulation";
    }

    /**
     * @description : 跳转到修改form的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/{formId}/update.html")
    @ApiOperation(value = "/{formId}/update.html", notes = "跳转到修改页面")
    public String toUpdateForm(Model model, @PathVariable(value = "formId") Long formId) {
        //静态注入要回显的数据
        Map<String, Object> form = formService.mySelectMapById(formId);
        model.addAttribute("form", form);
        return "form/newForm";
    }

    /**
     * @description : 跳转到新增form的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateForm(Model model) {
        return "form/newForm";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Form> getFormList(@ApiParam(name = "Form", value = "Form 实体类") @RequestBody Form form) {
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
    @GetMapping(value = "/get_map_by_id/{formId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{formId}", notes = "根据id获取formMap")
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
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除form")
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
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除form")
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
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改form")
    public JsonResult<Form> formCreateUpdate(@ApiParam(name = "Form", value = "Form实体类") @RequestBody Form form){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Form> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/form/create_update");
                form = formService.myFormCreateUpdate(form);
                resJson.setData(form);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
