package com.secondhand.tradingplatformadmincontroller.controller.front.article.RentingHouse;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
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
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouse;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseService;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : RentingHouse 控制器
 * @since : Create in 2019-03-17
 */
@Controller("frontRentingHouseController")
@Api(value = "/front/rentingHouse", description = "RentingHouse 控制器")
@RequestMapping("/front/rentingHouse")
public class RentingHouseController extends BaseController {

    @Autowired
    private RentingHouseService rentingHouseService;


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getRentingHouseList(@ApiParam(name = "rentingHouse", value = "RentingHouse 实体类") @RequestBody RentingHouse rentingHouse) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = rentingHouse.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> rentingHousePage = new Page(current, size);
        rentingHousePage = rentingHouseService.mySelectPageWithParam(rentingHousePage, rentingHouse);
        resJson.setRecordsTotal(rentingHousePage.getTotal());
        resJson.setData(rentingHousePage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取rentingHouseMap
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @GetMapping(value = "/get_map_by_id/{rentingHouseId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{rentingHouseId}", notes = "根据id获取rentingHouseMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRentingHouseByIdForMap(@ApiParam(name = "id", value = "rentingHouseId") @PathVariable("rentingHouseId") Long rentingHouseId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> rentingHouse = rentingHouseService.mySelectMapById(rentingHouseId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(rentingHouse);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除rentingHouse
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除rentingHouse")
    @ResponseBody
    public JsonResult<RentingHouse> fakeDeleteById(@ApiParam(name = "id", value = "rentingHouseId") @RequestBody Long rentingHouseId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RentingHouse> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/rentingHouse/delete");
            rentingHouseService.myFakeDeleteById(rentingHouseId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除rentingHouse
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除rentingHouse")
    @ResponseBody
    public JsonResult<RentingHouse> fakeBatchDelete(@ApiParam(name = "ids", value = "rentingHouseIds") @RequestBody List<Long> rentingHouseIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RentingHouse> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/rentingHouse/batch_delete");
            resJson.setSuccess(rentingHouseService.myFakeBatchDelete(rentingHouseIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改rentingHouse
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改rentingHouse")
    @ResponseBody
    public JsonResult<RentingHouse> rentingHouseCreateUpdate(@ApiParam(name = "rentingHouse", value = "RentingHouse实体类") @RequestBody RentingHouse rentingHouse) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<RentingHouse> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/rentingHouse/create_update");
            Session session = subject.getSession();
            Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
            rentingHouse.setUserId(userId);
            rentingHouse.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            rentingHouse = rentingHouseService.myRentingHouseCreateUpdate(rentingHouse);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(rentingHouse);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 立即购买rentingHouse
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @GetMapping(value = "/settlement/{rentingHouseId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/settlement/{rentingHouseId}", notes = "立即购买rentingHouse")
    @ResponseBody
    public JsonResult<Float> toSettlement(@ApiParam(name = "id", value = "rentingHouseId") @PathVariable(value = "rentingHouseId") Long rentingHouseId) throws ClientException, CustomizeException {

        Subject subject = SecurityUtils.getSubject();
        JsonResult<Float> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/rentingHouseOrder/create_update");
            Session session = SecurityUtils.getSubject().getSession();
            //先找出这个人余款有多少
            User user = (User) session.getAttribute(MagicalValue.USER_SESSION);
            Float balance = user.getBalance();
            balance = rentingHouseService.mySettlementById(rentingHouseId, balance, user.getId());

            //拼接返回结果
            resJson.setData(balance);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
