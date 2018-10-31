package com.secondhand.tradingplatformadmincontroller.controller.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformadminservice.service.system.FormFieldService;

/**
 * @description : FormField 控制器
 * @author : zhangjk
 * @since : Create in 2018-10-30
 */
@RestController
@Api(value="/admin/formField", description="FormField 控制器")
@RequestMapping("/admin/formField")
public class FormFieldController extends BaseController {

    @Autowired
    private FormFieldService formFieldService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<FormField> getFormFieldList(@ApiParam(name = "FormField", value = "FormField 实体类") @RequestBody FormField formField) {
            TableJson<FormField> resJson = new TableJson<>();
            Page resPage = formField.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<FormField> formFieldPage = new Page<FormField>(current, size);
            formFieldPage = formFieldService.selectPageWithParam(formFieldPage, formField);
            resJson.setRecordsTotal(formFieldPage.getTotal());
            resJson.setData(formFieldPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取formField
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @GetMapping(value = "/get_by_id/{formFieldId}", produces = {"application/json"})
    @ApiOperation(value = "/get_by_id/{formFieldId}", notes = "根据id获取formField")
    public JsonResult<FormField> getFormFieldById( @ApiParam(name = "id",value = "formFieldId") @PathVariable("formFieldId") Long formFieldId) {
            JsonResult<FormField> resJson = new JsonResult<>();
            FormField formField = formFieldService.selectOneByObj(formFieldId);
            resJson.setData(formField);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取formFieldMap
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @GetMapping(value = "/get_map_by_id/{formFieldId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{formFieldId}", notes = "根据id获取formFieldMap")
    public JsonResult<Map<String, Object>> getFormFieldByIdForMap( @ApiParam(name = "id", value = "formFieldId") @PathVariable("formFieldId") Long formFieldId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> formField = formFieldService.selectMapById(formFieldId);
            resJson.setData(formField);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除formField
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除formField")
    public JsonResult<FormField> fakeDeleteById(@ApiParam(name = "id", value = "formFieldId") @RequestBody Long formFieldId){
            JsonResult<FormField> resJson = new JsonResult<>();
            resJson.setSuccess(formFieldService.fakeDeleteById(formFieldId));
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除formField
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除formField")
    public JsonResult<FormField> fakeBatchDelete(@ApiParam(name = "ids", value = "formFieldIds") @RequestBody List<Long> formFieldIds){
            JsonResult<FormField> resJson = new JsonResult<>();
            resJson.setSuccess(formFieldService.fakeBatchDelete(formFieldIds));
            return resJson;
    }

    /**
     * @description : 新增或修改formField
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改formField")
    public JsonResult<FormField> formFieldCreateUpdate(@ApiParam(name = "FormField", value = "FormField实体类") @RequestBody FormField formField){
            formField = formFieldService.formFieldCreateUpdate(formField);
            JsonResult<FormField> resJson = new JsonResult<>();
            resJson.setData(formField);
            resJson.setSuccess(true);
            return resJson;
    }
}
