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
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceAdvisory;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceAdvisoryService;

import java.util.List;
import java.util.Map;

/**
 * @description : ElectricApplianceAdvisory 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontElectricApplianceAdvisoryController")
@Api(value="/front/electricApplianceAdvisory", description="ElectricApplianceAdvisory 控制器")
@RequestMapping("/front/electricApplianceAdvisory")
public class ElectricApplianceAdvisoryController extends BaseController {

    @Autowired
    private ElectricApplianceAdvisoryService electricApplianceAdvisoryService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getElectricApplianceAdvisoryList(@ApiParam(name = "electricApplianceAdvisory", value = "ElectricApplianceAdvisory 实体类") @RequestBody ElectricApplianceAdvisory electricApplianceAdvisory) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = electricApplianceAdvisory.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> electricApplianceAdvisoryPage = new Page(current, size);
            electricApplianceAdvisoryPage = electricApplianceAdvisoryService.mySelectPageWithParam(electricApplianceAdvisoryPage, electricApplianceAdvisory);
            resJson.setRecordsTotal(electricApplianceAdvisoryPage.getTotal());
            resJson.setData(electricApplianceAdvisoryPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取electricApplianceAdvisoryMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{electricApplianceAdvisoryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{electricApplianceAdvisoryId}", notes = "根据id获取electricApplianceAdvisoryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getElectricApplianceAdvisoryByIdForMap( @ApiParam(name = "id", value = "electricApplianceAdvisoryId") @PathVariable("electricApplianceAdvisoryId") Long electricApplianceAdvisoryId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> electricApplianceAdvisory = electricApplianceAdvisoryService.mySelectMapById(electricApplianceAdvisoryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(electricApplianceAdvisory);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除electricApplianceAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除electricApplianceAdvisory")
    @ResponseBody
    public JsonResult<ElectricApplianceAdvisory> fakeDeleteById(@ApiParam(name = "id", value = "electricApplianceAdvisoryId") @RequestBody Long electricApplianceAdvisoryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceAdvisory/delete");
                electricApplianceAdvisoryService.myFakeDeleteById(electricApplianceAdvisoryId);
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
     * @description : 根据ids批量假删除electricApplianceAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除electricApplianceAdvisory")
    @ResponseBody
    public JsonResult<ElectricApplianceAdvisory> fakeBatchDelete(@ApiParam(name = "ids", value = "electricApplianceAdvisoryIds") @RequestBody List<Long> electricApplianceAdvisoryIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceAdvisory/batch_delete");
                resJson.setSuccess(electricApplianceAdvisoryService.myFakeBatchDelete(electricApplianceAdvisoryIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改electricApplianceAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改electricApplianceAdvisory")
    @ResponseBody
    public JsonResult<ElectricApplianceAdvisory> electricApplianceAdvisoryCreateUpdate(@ApiParam(name = "electricApplianceAdvisory", value = "ElectricApplianceAdvisory实体类") @RequestBody ElectricApplianceAdvisory electricApplianceAdvisory){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceAdvisory/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                electricApplianceAdvisory.setUserId(userId);
                electricApplianceAdvisory.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
                electricApplianceAdvisory = electricApplianceAdvisoryService.myElectricApplianceAdvisoryCreateUpdate(electricApplianceAdvisory);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(electricApplianceAdvisory);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
