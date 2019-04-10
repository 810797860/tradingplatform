package com.secondhand.tradingplatformadmincontroller.controller.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceEvaluation;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceEvaluationService;

import java.util.List;
import java.util.Map;

/**
 * @description : ElectricApplianceEvaluation 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontElectricApplianceEvaluationController")
@Api(value="/front/electricApplianceEvaluation", description="ElectricApplianceEvaluation 控制器")
@RequestMapping("/front/electricApplianceEvaluation")
public class ElectricApplianceEvaluationController extends BaseController {

    @Autowired
    private ElectricApplianceEvaluationService electricApplianceEvaluationService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getElectricApplianceEvaluationList(@ApiParam(name = "electricApplianceEvaluation", value = "ElectricApplianceEvaluation 实体类") @RequestBody ElectricApplianceEvaluation electricApplianceEvaluation) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = electricApplianceEvaluation.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> electricApplianceEvaluationPage = new Page(current, size);
            electricApplianceEvaluationPage = electricApplianceEvaluationService.mySelectPageWithParam(electricApplianceEvaluationPage, electricApplianceEvaluation);
            resJson.setRecordsTotal(electricApplianceEvaluationPage.getTotal());
            resJson.setData(electricApplianceEvaluationPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取electricApplianceEvaluationMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{electricApplianceEvaluationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{electricApplianceEvaluationId}", notes = "根据id获取electricApplianceEvaluationMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getElectricApplianceEvaluationByIdForMap( @ApiParam(name = "id", value = "electricApplianceEvaluationId") @PathVariable("electricApplianceEvaluationId") Long electricApplianceEvaluationId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> electricApplianceEvaluation = electricApplianceEvaluationService.mySelectMapById(electricApplianceEvaluationId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(electricApplianceEvaluation);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除electricApplianceEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除electricApplianceEvaluation")
    @ResponseBody
    public JsonResult<ElectricApplianceEvaluation> fakeDeleteById(@ApiParam(name = "id", value = "electricApplianceEvaluationId") @RequestBody Long electricApplianceEvaluationId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceEvaluation/delete");
                electricApplianceEvaluationService.myFakeDeleteById(electricApplianceEvaluationId);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除electricApplianceEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除electricApplianceEvaluation")
    @ResponseBody
    public JsonResult<ElectricApplianceEvaluation> fakeBatchDelete(@ApiParam(name = "ids", value = "electricApplianceEvaluationIds") @RequestBody List<Long> electricApplianceEvaluationIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceEvaluation/batch_delete");
                resJson.setSuccess(electricApplianceEvaluationService.myFakeBatchDelete(electricApplianceEvaluationIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改electricApplianceEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改electricApplianceEvaluation")
    @ResponseBody
    public JsonResult<ElectricApplianceEvaluation> electricApplianceEvaluationCreateUpdate(@ApiParam(name = "electricApplianceEvaluation", value = "ElectricApplianceEvaluation实体类") @RequestBody ElectricApplianceEvaluation electricApplianceEvaluation){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceEvaluation/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                electricApplianceEvaluation.setUserId(userId);
                electricApplianceEvaluation.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
                electricApplianceEvaluation = electricApplianceEvaluationService.myElectricApplianceEvaluationCreateUpdate(electricApplianceEvaluation);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(electricApplianceEvaluation);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
