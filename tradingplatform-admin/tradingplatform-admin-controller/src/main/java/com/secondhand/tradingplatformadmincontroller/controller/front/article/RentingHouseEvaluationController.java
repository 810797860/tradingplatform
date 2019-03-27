package com.secondhand.tradingplatformadmincontroller.controller.front.article;

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
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouseEvaluation;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouseEvaluationService;

import java.util.List;
import java.util.Map;

/**
 * @description : RentingHouseEvaluation 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontRentingHouseEvaluationController")
@Api(value="/front/rentingHouseEvaluation", description="RentingHouseEvaluation 控制器")
@RequestMapping("/front/rentingHouseEvaluation")
public class RentingHouseEvaluationController extends BaseController {

    @Autowired
    private RentingHouseEvaluationService rentingHouseEvaluationService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getRentingHouseEvaluationList(@ApiParam(name = "rentingHouseEvaluation", value = "RentingHouseEvaluation 实体类") @RequestBody RentingHouseEvaluation rentingHouseEvaluation) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = rentingHouseEvaluation.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> rentingHouseEvaluationPage = new Page(current, size);
            rentingHouseEvaluationPage = rentingHouseEvaluationService.mySelectPageWithParam(rentingHouseEvaluationPage, rentingHouseEvaluation);
            resJson.setRecordsTotal(rentingHouseEvaluationPage.getTotal());
            resJson.setData(rentingHouseEvaluationPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取rentingHouseEvaluationMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{rentingHouseEvaluationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{rentingHouseEvaluationId}", notes = "根据id获取rentingHouseEvaluationMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRentingHouseEvaluationByIdForMap( @ApiParam(name = "id", value = "rentingHouseEvaluationId") @PathVariable("rentingHouseEvaluationId") Long rentingHouseEvaluationId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> rentingHouseEvaluation = rentingHouseEvaluationService.mySelectMapById(rentingHouseEvaluationId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(rentingHouseEvaluation);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除rentingHouseEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除rentingHouseEvaluation")
    @ResponseBody
    public JsonResult<RentingHouseEvaluation> fakeDeleteById(@ApiParam(name = "id", value = "rentingHouseEvaluationId") @RequestBody Long rentingHouseEvaluationId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseEvaluation/delete");
                rentingHouseEvaluationService.myFakeDeleteById(rentingHouseEvaluationId);
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
     * @description : 根据ids批量假删除rentingHouseEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除rentingHouseEvaluation")
    @ResponseBody
    public JsonResult<RentingHouseEvaluation> fakeBatchDelete(@ApiParam(name = "ids", value = "rentingHouseEvaluationIds") @RequestBody List<Long> rentingHouseEvaluationIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseEvaluation/batch_delete");
                resJson.setSuccess(rentingHouseEvaluationService.myFakeBatchDelete(rentingHouseEvaluationIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改rentingHouseEvaluation
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改rentingHouseEvaluation")
    @ResponseBody
    public JsonResult<RentingHouseEvaluation> rentingHouseEvaluationCreateUpdate(@ApiParam(name = "rentingHouseEvaluation", value = "RentingHouseEvaluation实体类") @RequestBody RentingHouseEvaluation rentingHouseEvaluation){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseEvaluation> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseEvaluation/create_update");
                rentingHouseEvaluation = rentingHouseEvaluationService.myRentingHouseEvaluationCreateUpdate(rentingHouseEvaluation);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(rentingHouseEvaluation);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
