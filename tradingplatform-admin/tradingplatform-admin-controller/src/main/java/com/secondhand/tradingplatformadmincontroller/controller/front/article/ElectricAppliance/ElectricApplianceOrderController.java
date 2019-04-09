package com.secondhand.tradingplatformadmincontroller.controller.front.article.ElectricAppliance;

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
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceOrder;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceOrderService;

import java.util.List;
import java.util.Map;

/**
 * @description : ElectricApplianceOrder 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontElectricApplianceOrderController")
@Api(value="/front/electricApplianceOrder", description="ElectricApplianceOrder 控制器")
@RequestMapping("/front/electricApplianceOrder")
public class ElectricApplianceOrderController extends BaseController {

    @Autowired
    private ElectricApplianceOrderService electricApplianceOrderService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getElectricApplianceOrderList(@ApiParam(name = "electricApplianceOrder", value = "ElectricApplianceOrder 实体类") @RequestBody ElectricApplianceOrder electricApplianceOrder) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = electricApplianceOrder.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> electricApplianceOrderPage = new Page(current, size);
            electricApplianceOrderPage = electricApplianceOrderService.mySelectPageWithParam(electricApplianceOrderPage, electricApplianceOrder);
            resJson.setRecordsTotal(electricApplianceOrderPage.getTotal());
            resJson.setData(electricApplianceOrderPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取electricApplianceOrderMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{electricApplianceOrderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{electricApplianceOrderId}", notes = "根据id获取electricApplianceOrderMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getElectricApplianceOrderByIdForMap( @ApiParam(name = "id", value = "electricApplianceOrderId") @PathVariable("electricApplianceOrderId") Long electricApplianceOrderId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> electricApplianceOrder = electricApplianceOrderService.mySelectMapById(electricApplianceOrderId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(electricApplianceOrder);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除electricApplianceOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除electricApplianceOrder")
    @ResponseBody
    public JsonResult<ElectricApplianceOrder> fakeDeleteById(@ApiParam(name = "id", value = "electricApplianceOrderId") @RequestBody Long electricApplianceOrderId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceOrder/delete");
                electricApplianceOrderService.myFakeDeleteById(electricApplianceOrderId);
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
     * @description : 根据ids批量假删除electricApplianceOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除electricApplianceOrder")
    @ResponseBody
    public JsonResult<ElectricApplianceOrder> fakeBatchDelete(@ApiParam(name = "ids", value = "electricApplianceOrderIds") @RequestBody List<Long> electricApplianceOrderIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceOrder/batch_delete");
                resJson.setSuccess(electricApplianceOrderService.myFakeBatchDelete(electricApplianceOrderIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改electricApplianceOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改electricApplianceOrder")
    @ResponseBody
    public JsonResult<ElectricApplianceOrder> electricApplianceOrderCreateUpdate(@ApiParam(name = "electricApplianceOrder", value = "ElectricApplianceOrder实体类") @RequestBody ElectricApplianceOrder electricApplianceOrder){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceOrder/create_update");
                electricApplianceOrder = electricApplianceOrderService.myElectricApplianceOrderCreateUpdate(electricApplianceOrder);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(electricApplianceOrder);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
