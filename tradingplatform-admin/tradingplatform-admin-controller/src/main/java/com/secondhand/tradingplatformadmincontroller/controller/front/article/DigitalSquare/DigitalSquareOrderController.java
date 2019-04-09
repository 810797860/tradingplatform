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
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareOrder;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareOrderService;

import java.util.List;
import java.util.Map;

/**
 * @description : DigitalSquareOrder 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontDigitalSquareOrderController")
@Api(value="/front/digitalSquareOrder", description="DigitalSquareOrder 控制器")
@RequestMapping("/front/digitalSquareOrder")
public class DigitalSquareOrderController extends BaseController {

    @Autowired
    private DigitalSquareOrderService digitalSquareOrderService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getDigitalSquareOrderList(@ApiParam(name = "digitalSquareOrder", value = "DigitalSquareOrder 实体类") @RequestBody DigitalSquareOrder digitalSquareOrder) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = digitalSquareOrder.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> digitalSquareOrderPage = new Page(current, size);
            digitalSquareOrderPage = digitalSquareOrderService.mySelectPageWithParam(digitalSquareOrderPage, digitalSquareOrder);
            resJson.setRecordsTotal(digitalSquareOrderPage.getTotal());
            resJson.setData(digitalSquareOrderPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取digitalSquareOrderMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{digitalSquareOrderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{digitalSquareOrderId}", notes = "根据id获取digitalSquareOrderMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getDigitalSquareOrderByIdForMap( @ApiParam(name = "id", value = "digitalSquareOrderId") @PathVariable("digitalSquareOrderId") Long digitalSquareOrderId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> digitalSquareOrder = digitalSquareOrderService.mySelectMapById(digitalSquareOrderId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(digitalSquareOrder);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除digitalSquareOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除digitalSquareOrder")
    @ResponseBody
    public JsonResult<DigitalSquareOrder> fakeDeleteById(@ApiParam(name = "id", value = "digitalSquareOrderId") @RequestBody Long digitalSquareOrderId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareOrder/delete");
                digitalSquareOrderService.myFakeDeleteById(digitalSquareOrderId);
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
     * @description : 根据ids批量假删除digitalSquareOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除digitalSquareOrder")
    @ResponseBody
    public JsonResult<DigitalSquareOrder> fakeBatchDelete(@ApiParam(name = "ids", value = "digitalSquareOrderIds") @RequestBody List<Long> digitalSquareOrderIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareOrder/batch_delete");
                resJson.setSuccess(digitalSquareOrderService.myFakeBatchDelete(digitalSquareOrderIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改digitalSquareOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改digitalSquareOrder")
    @ResponseBody
    public JsonResult<DigitalSquareOrder> digitalSquareOrderCreateUpdate(@ApiParam(name = "digitalSquareOrder", value = "DigitalSquareOrder实体类") @RequestBody DigitalSquareOrder digitalSquareOrder){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareOrder/create_update");
                digitalSquareOrder = digitalSquareOrderService.myDigitalSquareOrderCreateUpdate(digitalSquareOrder);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(digitalSquareOrder);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
