package com.secondhand.tradingplatformadmincontroller.controller.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RestController
@Api(value="/admin/formField", description="FormField 控制器")
@RequestMapping("/admin/formField")
public class FormFieldController extends BaseController {

    @Autowired
    private FormFieldService formFieldService;

    @Autowired
    private SelectItemService selectItemService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到formField的列表页面")
    public String toFormFieldList(Model model) {
        return "formField/tabulation";
    }

    /**
     * @description : 跳转到修改formField的页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/{formFieldId}/update.html")
    @ApiOperation(value = "/{formFieldId}/update.html", notes = "跳转到修改页面")
    public String toUpdateFormField(Model model, @PathVariable(value = "formFieldId") Long formFieldId) {
        //静态注入要回显的数据
        Map<String, Object> formField = formFieldService.mySelectMapById(formFieldId);
        //静态注入展示类型及字段类型
        List<SelectItem> displayType = selectItemService.getAllItemsByPid(SystemSelectItem.DISPLAY_TYPE);
        model.addAttribute("formField", formField);
        model.addAttribute("displayType", displayType);
        return "formField/newFormField";
    }

    /**
     * @description : 跳转到新增formField的页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateFormField(Model model) {
        //静态注入展示类型及字段类型
        List<SelectItem> displayType = selectItemService.getAllItemsByPid(SystemSelectItem.DISPLAY_TYPE);
        model.addAttribute("displayType", displayType);
        return "formField/newFormField";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<FormField> getFormFieldList(@ApiParam(name = "FormField", value = "FormField 实体类") @RequestBody FormField formField) {
            TableJson<FormField> resJson = new TableJson<>();
            Page resPage = formField.getPage();
            formField.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<FormField> formFieldPage = new Page<FormField>(current, size);
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
    @GetMapping(value = "/get_map_by_id/{formFieldId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{formFieldId}", notes = "根据id获取formFieldMap")
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
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除formField")
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
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除formField")
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
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改formField")
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
}
