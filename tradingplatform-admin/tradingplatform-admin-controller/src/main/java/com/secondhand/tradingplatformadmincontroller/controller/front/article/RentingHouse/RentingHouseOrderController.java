package com.secondhand.tradingplatformadmincontroller.controller.front.article.RentingHouse;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.BusinessSelectItem;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
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
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseOrder;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseOrderService;

import java.util.List;
import java.util.Map;

/**
 * @description : RentingHouseOrder 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontRentingHouseOrderController")
@Api(value="/front/rentingHouseOrder", description="RentingHouseOrder 控制器")
@RequestMapping("/front/rentingHouseOrder")
public class RentingHouseOrderController extends BaseController {

    @Autowired
    private RentingHouseOrderService rentingHouseOrderService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getRentingHouseOrderList(@ApiParam(name = "rentingHouseOrder", value = "RentingHouseOrder 实体类") @RequestBody RentingHouseOrder rentingHouseOrder) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = rentingHouseOrder.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> rentingHouseOrderPage = new Page(current, size);
            rentingHouseOrderPage = rentingHouseOrderService.mySelectPageWithParam(rentingHouseOrderPage, rentingHouseOrder);
            resJson.setRecordsTotal(rentingHouseOrderPage.getTotal());
            resJson.setData(rentingHouseOrderPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取rentingHouseOrderMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{rentingHouseOrderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{rentingHouseOrderId}", notes = "根据id获取rentingHouseOrderMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRentingHouseOrderByIdForMap( @ApiParam(name = "id", value = "rentingHouseOrderId") @PathVariable("rentingHouseOrderId") Long rentingHouseOrderId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> rentingHouseOrder = rentingHouseOrderService.mySelectMapById(rentingHouseOrderId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(rentingHouseOrder);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除rentingHouseOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除rentingHouseOrder")
    @ResponseBody
    public JsonResult<RentingHouseOrder> fakeDeleteById(@ApiParam(name = "id", value = "rentingHouseOrderId") @RequestBody Long rentingHouseOrderId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseOrder/delete");
                rentingHouseOrderService.myFakeDeleteById(rentingHouseOrderId);
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
     * @description : 根据ids批量假删除rentingHouseOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除rentingHouseOrder")
    @ResponseBody
    public JsonResult<RentingHouseOrder> fakeBatchDelete(@ApiParam(name = "ids", value = "rentingHouseOrderIds") @RequestBody List<Long> rentingHouseOrderIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseOrder/batch_delete");
                resJson.setSuccess(rentingHouseOrderService.myFakeBatchDelete(rentingHouseOrderIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改rentingHouseOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改rentingHouseOrder")
    @ResponseBody
    public JsonResult<RentingHouseOrder> rentingHouseOrderCreateUpdate(@ApiParam(name = "rentingHouseOrder", value = "RentingHouseOrder实体类") @RequestBody RentingHouseOrder rentingHouseOrder){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseOrder/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                rentingHouseOrder.setUserId(userId);
                rentingHouseOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_SHOPPING_CART);
                rentingHouseOrder = rentingHouseOrderService.myRentingHouseOrderCreateUpdate(rentingHouseOrder);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(rentingHouseOrder);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
