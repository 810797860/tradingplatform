package com.secondhand.tradingplatformadmincontroller.controller.front.article.DigitalSquare;

import com.baomidou.mybatisplus.plugins.Page;
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
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareEvaluation;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareEvaluationService;

import java.util.List;
import java.util.Map;

/**
 * @description : DigitalSquareEvaluation 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontDigitalSquareEvaluationController")
@Api(value="/front/digitalSquareEvaluation", description="DigitalSquareEvaluation 控制器")
@RequestMapping("/front/digitalSquareEvaluation")
public class DigitalSquareEvaluationController extends BaseController {

    @Autowired
    private DigitalSquareEvaluationService digitalSquareEvaluationService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getDigitalSquareEvaluationList(@ApiParam(name = "digitalSquareEvaluation", value = "DigitalSquareEvaluation 实体类") @RequestBody DigitalSquareEvaluation digitalSquareEvaluation) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = digitalSquareEvaluation.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> digitalSquareEvaluationPage = new Page(current, size);
            digitalSquareEvaluationPage = digitalSquareEvaluationService.mySelectPageWithParam(digitalSquareEvaluationPage, digitalSquareEvaluation);
            resJson.setRecordsTotal(digitalSquareEvaluationPage.getTotal());
            resJson.setData(digitalSquareEvaluationPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取digitalSquareEvaluationMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{digitalSquareEvaluationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{digitalSquareEvaluationId}", notes = "根据id获取digitalSquareEvaluationMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getDigitalSquareEvaluationByIdForMap( @ApiParam(name = "id", value = "digitalSquareEvaluationId") @PathVariable("digitalSquareEvaluationId") Long digitalSquareEvaluationId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> digitalSquareEvaluation = digitalSquareEvaluationService.mySelectMapById(digitalSquareEvaluationId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(digitalSquareEvaluation);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除digitalSquareEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除digitalSquareEvaluation")
    @ResponseBody
    public JsonResult<DigitalSquareEvaluation> fakeDeleteById(@ApiParam(name = "id", value = "digitalSquareEvaluationId") @RequestBody Long digitalSquareEvaluationId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareEvaluation/delete");
                digitalSquareEvaluationService.myFakeDeleteById(digitalSquareEvaluationId);
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
     * @description : 根据ids批量假删除digitalSquareEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除digitalSquareEvaluation")
    @ResponseBody
    public JsonResult<DigitalSquareEvaluation> fakeBatchDelete(@ApiParam(name = "ids", value = "digitalSquareEvaluationIds") @RequestBody List<Long> digitalSquareEvaluationIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareEvaluation/batch_delete");
                resJson.setSuccess(digitalSquareEvaluationService.myFakeBatchDelete(digitalSquareEvaluationIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改digitalSquareEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改digitalSquareEvaluation")
    @ResponseBody
    public JsonResult<DigitalSquareEvaluation> digitalSquareEvaluationCreateUpdate(@ApiParam(name = "digitalSquareEvaluation", value = "DigitalSquareEvaluation实体类") @RequestBody DigitalSquareEvaluation digitalSquareEvaluation){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareEvaluation/create_update");
                digitalSquareEvaluation = digitalSquareEvaluationService.myDigitalSquareEvaluationCreateUpdate(digitalSquareEvaluation);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(digitalSquareEvaluation);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
