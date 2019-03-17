package com.secondhand.tradingplatformadmincontroller.controller.admin.business;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.admin.business.OperationLog;
import com.secondhand.tradingplatformadminservice.service.admin.business.OperationLogService;

/**
 * @author : zhangjk
 * @description : OperationLog 控制器
 * @since : Create in 2018-10-28
 */
@RestController("adminOperationLogController")
@Api(value = "/admin/operationLog", description = "OperationLog 控制器")
@RequestMapping("/admin/operationLog")
public class OperationLogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-28
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    public TableJson<OperationLog> getOperationLogList(@ApiParam(name = "OperationLog", value = "OperationLog 实体类") @RequestBody OperationLog operationLog) {
        TableJson<OperationLog> resJson = new TableJson<>();
        Page resPage = operationLog.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<OperationLog> operationLogPage = new Page<OperationLog>(current, size);
        operationLogPage = operationLogService.selectPageWithParam(operationLogPage, operationLog);
        resJson.setRecordsTotal(operationLogPage.getTotal());
        resJson.setData(operationLogPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取operationLog
     * @author : zhangjk
     * @since : Create in 2018-10-28
     */
    @GetMapping(value = "/get_by_id/{operationLogId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_by_id/{operationLogId}", notes = "根据id获取operationLog")
    public JsonResult<OperationLog> getOperationLogById(@ApiParam(name = "id", value = "operationLogId") @PathVariable("operationLogId") Long operationLogId) {
        JsonResult<OperationLog> resJson = new JsonResult<>();
        OperationLog operationLog = operationLogService.selectById(operationLogId);
        resJson.setData(operationLog);
        resJson.setSuccess(true);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        return resJson;
    }

    /**
     * @description : 通过id获取operationLogMap
     * @author : zhangjk
     * @since : Create in 2018-10-28
     */
    @GetMapping(value = "/get_map_by_id/{operationLogId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{operationLogId}", notes = "根据id获取operationLogMap")
    public JsonResult<Map<String, Object>> getOperationLogByIdForMap(@ApiParam(name = "id", value = "operationLogId") @PathVariable("operationLogId") Long operationLogId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> operationLog = operationLogService.selectMapById(operationLogId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(operationLog);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除operationLog
     * @author : zhangjk
     * @since : Create in 2018-10-28
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除operationLog")
    public JsonResult<OperationLog> fakeDeleteById(@ApiParam(name = "id", value = "operationLogId") @RequestBody Long operationLogId) {
        JsonResult<OperationLog> resJson = new JsonResult<>();
        resJson.setSuccess(operationLogService.fakeDeleteById(operationLogId));
        resJson.setCode(resJson.isSuccess() == true ? MagicalValue.CODE_OF_SUCCESS : MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除operationLog
     * @author : zhangjk
     * @since : Create in 2018-10-28
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除operationLog")
    public JsonResult<OperationLog> fakeBatchDelete(@ApiParam(name = "ids", value = "operationLogIds") @RequestBody List<Long> operationLogIds) {
        JsonResult<OperationLog> resJson = new JsonResult<>();
        resJson.setSuccess(operationLogService.fakeBatchDelete(operationLogIds));
        resJson.setCode(resJson.isSuccess() == true ? MagicalValue.CODE_OF_SUCCESS : MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
        return resJson;
    }

    /**
     * @description : 新增或修改operationLog
     * @author : zhangjk
     * @since : Create in 2018-10-28
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改operationLog")
    public JsonResult<OperationLog> operationLogCreateUpdate(@ApiParam(name = "OperationLog", value = "OperationLog实体类") @RequestBody OperationLog operationLog) {
        operationLog = operationLogService.operationLogCreateUpdate(operationLog);
        JsonResult<OperationLog> resJson = new JsonResult<>();
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(operationLog);
        resJson.setSuccess(true);
        return resJson;
    }
}
