package com.secondhand.tradingplatformadmincontroller.controller.front.article.SportsSpecial;

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
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialEvaluation;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialEvaluationService;

import java.util.List;
import java.util.Map;

/**
 * @description : SportsSpecialEvaluation 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontSportsSpecialEvaluationController")
@Api(value="/front/sportsSpecialEvaluation", description="SportsSpecialEvaluation 控制器")
@RequestMapping("/front/sportsSpecialEvaluation")
public class SportsSpecialEvaluationController extends BaseController {

    @Autowired
    private SportsSpecialEvaluationService sportsSpecialEvaluationService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSportsSpecialEvaluationList(@ApiParam(name = "sportsSpecialEvaluation", value = "SportsSpecialEvaluation 实体类") @RequestBody SportsSpecialEvaluation sportsSpecialEvaluation) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = sportsSpecialEvaluation.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> sportsSpecialEvaluationPage = new Page(current, size);
            sportsSpecialEvaluationPage = sportsSpecialEvaluationService.mySelectPageWithParam(sportsSpecialEvaluationPage, sportsSpecialEvaluation);
            resJson.setRecordsTotal(sportsSpecialEvaluationPage.getTotal());
            resJson.setData(sportsSpecialEvaluationPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取sportsSpecialEvaluationMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{sportsSpecialEvaluationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{sportsSpecialEvaluationId}", notes = "根据id获取sportsSpecialEvaluationMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSportsSpecialEvaluationByIdForMap( @ApiParam(name = "id", value = "sportsSpecialEvaluationId") @PathVariable("sportsSpecialEvaluationId") Long sportsSpecialEvaluationId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> sportsSpecialEvaluation = sportsSpecialEvaluationService.mySelectMapById(sportsSpecialEvaluationId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(sportsSpecialEvaluation);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除sportsSpecialEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除sportsSpecialEvaluation")
    @ResponseBody
    public JsonResult<SportsSpecialEvaluation> fakeDeleteById(@ApiParam(name = "id", value = "sportsSpecialEvaluationId") @RequestBody Long sportsSpecialEvaluationId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialEvaluation/delete");
                sportsSpecialEvaluationService.myFakeDeleteById(sportsSpecialEvaluationId);
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
     * @description : 根据ids批量假删除sportsSpecialEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除sportsSpecialEvaluation")
    @ResponseBody
    public JsonResult<SportsSpecialEvaluation> fakeBatchDelete(@ApiParam(name = "ids", value = "sportsSpecialEvaluationIds") @RequestBody List<Long> sportsSpecialEvaluationIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialEvaluation/batch_delete");
                resJson.setSuccess(sportsSpecialEvaluationService.myFakeBatchDelete(sportsSpecialEvaluationIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改sportsSpecialEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改sportsSpecialEvaluation")
    @ResponseBody
    public JsonResult<SportsSpecialEvaluation> sportsSpecialEvaluationCreateUpdate(@ApiParam(name = "sportsSpecialEvaluation", value = "SportsSpecialEvaluation实体类") @RequestBody SportsSpecialEvaluation sportsSpecialEvaluation){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialEvaluation/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                sportsSpecialEvaluation.setUserId(userId);
                sportsSpecialEvaluation.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
                sportsSpecialEvaluation = sportsSpecialEvaluationService.mySportsSpecialEvaluationCreateUpdate(sportsSpecialEvaluation);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(sportsSpecialEvaluation);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
