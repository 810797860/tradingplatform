package com.secondhand.tradingplatformadmincontroller.controller.front.article.SportsSpecial;

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
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialOrder;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialOrderService;

import java.util.List;
import java.util.Map;

/**
 * @description : SportsSpecialOrder 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontSportsSpecialOrderController")
@Api(value="/front/sportsSpecialOrder", description="SportsSpecialOrder 控制器")
@RequestMapping("/front/sportsSpecialOrder")
public class SportsSpecialOrderController extends BaseController {

    @Autowired
    private SportsSpecialOrderService sportsSpecialOrderService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSportsSpecialOrderList(@ApiParam(name = "sportsSpecialOrder", value = "SportsSpecialOrder 实体类") @RequestBody SportsSpecialOrder sportsSpecialOrder) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = sportsSpecialOrder.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> sportsSpecialOrderPage = new Page(current, size);
            sportsSpecialOrderPage = sportsSpecialOrderService.mySelectPageWithParam(sportsSpecialOrderPage, sportsSpecialOrder);
            resJson.setRecordsTotal(sportsSpecialOrderPage.getTotal());
            resJson.setData(sportsSpecialOrderPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取sportsSpecialOrderMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{sportsSpecialOrderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{sportsSpecialOrderId}", notes = "根据id获取sportsSpecialOrderMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSportsSpecialOrderByIdForMap( @ApiParam(name = "id", value = "sportsSpecialOrderId") @PathVariable("sportsSpecialOrderId") Long sportsSpecialOrderId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> sportsSpecialOrder = sportsSpecialOrderService.mySelectMapById(sportsSpecialOrderId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(sportsSpecialOrder);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除sportsSpecialOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除sportsSpecialOrder")
    @ResponseBody
    public JsonResult<SportsSpecialOrder> fakeDeleteById(@ApiParam(name = "id", value = "sportsSpecialOrderId") @RequestBody Long sportsSpecialOrderId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialOrder/delete");
                sportsSpecialOrderService.myFakeDeleteById(sportsSpecialOrderId);
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
     * @description : 根据ids批量假删除sportsSpecialOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除sportsSpecialOrder")
    @ResponseBody
    public JsonResult<SportsSpecialOrder> fakeBatchDelete(@ApiParam(name = "ids", value = "sportsSpecialOrderIds") @RequestBody List<Long> sportsSpecialOrderIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialOrder/batch_delete");
                resJson.setSuccess(sportsSpecialOrderService.myFakeBatchDelete(sportsSpecialOrderIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改sportsSpecialOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改sportsSpecialOrder")
    @ResponseBody
    public JsonResult<SportsSpecialOrder> sportsSpecialOrderCreateUpdate(@ApiParam(name = "sportsSpecialOrder", value = "SportsSpecialOrder实体类") @RequestBody SportsSpecialOrder sportsSpecialOrder){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialOrder/create_update");
                sportsSpecialOrder = sportsSpecialOrderService.mySportsSpecialOrderCreateUpdate(sportsSpecialOrder);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(sportsSpecialOrder);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
