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
import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformadminservice.service.system.FormService;

/**
 * @description : Form 控制器
 * @author : zhangjk
 * @since : Create in 2018-10-30
 */
@RestController
@Api(value="/admin/form", description="Form 控制器")
@RequestMapping("/admin/form")
public class FormController extends BaseController {

    @Autowired
    private FormService formService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Form> getFormList(@ApiParam(name = "Form", value = "Form 实体类") @RequestBody Form form) {
            TableJson<Form> resJson = new TableJson<>();
            Page resPage = form.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Form> formPage = new Page<Form>(current, size);
            formPage = formService.selectPageWithParam(formPage, form);
            resJson.setRecordsTotal(formPage.getTotal());
            resJson.setData(formPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取form
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @GetMapping(value = "/get_by_id/{formId}", produces = {"application/json"})
    @ApiOperation(value = "/get_by_id/{formId}", notes = "根据id获取form")
    public JsonResult<Form> getFormById( @ApiParam(name = "id",value = "formId") @PathVariable("formId") Long formId) {
            JsonResult<Form> resJson = new JsonResult<>();
            Form form = formService.selectOneByObj(formId);
            resJson.setData(form);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取formMap
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @GetMapping(value = "/get_map_by_id/{formId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{formId}", notes = "根据id获取formMap")
    public JsonResult<Map<String, Object>> getFormByIdForMap( @ApiParam(name = "id", value = "formId") @PathVariable("formId") Long formId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> form = formService.selectMapById(formId);
            resJson.setData(form);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除form
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除form")
    public JsonResult<Form> fakeDeleteById(@ApiParam(name = "id", value = "formId") @RequestBody Long formId){
            JsonResult<Form> resJson = new JsonResult<>();
            resJson.setSuccess(formService.fakeDeleteById(formId));
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除form
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除form")
    public JsonResult<Form> fakeBatchDelete(@ApiParam(name = "ids", value = "formIds") @RequestBody List<Long> formIds){
            JsonResult<Form> resJson = new JsonResult<>();
            resJson.setSuccess(formService.fakeBatchDelete(formIds));
            return resJson;
    }

    /**
     * @description : 新增或修改form
     * @author : zhangjk
     * @since : Create in 2018-10-30
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改form")
    public JsonResult<Form> formCreateUpdate(@ApiParam(name = "Form", value = "Form实体类") @RequestBody Form form){
            form = formService.formCreateUpdate(form);
            JsonResult<Form> resJson = new JsonResult<>();
            resJson.setData(form);
            resJson.setSuccess(true);
            return resJson;
    }
}
