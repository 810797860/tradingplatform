package com.secondhand.tradingplatformadmincontroller.controller.front.article.DigitalSquare;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquare;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareService;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : DigitalSquare 控制器
 * @since : Create in 2019-03-17
 */
@Controller("frontDigitalSquareController")
@Api(value = "/front/digitalSquare", description = "DigitalSquare 控制器")
@RequestMapping("/front/digitalSquare")
public class DigitalSquareController extends BaseController {

    @Autowired
    private DigitalSquareService digitalSquareService;


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getDigitalSquareList(@ApiParam(name = "digitalSquare", value = "DigitalSquare 实体类") @RequestBody DigitalSquare digitalSquare) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = digitalSquare.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> digitalSquarePage = new Page(current, size);
        digitalSquarePage = digitalSquareService.mySelectPageWithParam(digitalSquarePage, digitalSquare);
        resJson.setRecordsTotal(digitalSquarePage.getTotal());
        resJson.setData(digitalSquarePage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取digitalSquareMap
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @GetMapping(value = "/get_map_by_id/{digitalSquareId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{digitalSquareId}", notes = "根据id获取digitalSquareMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getDigitalSquareByIdForMap(@ApiParam(name = "id", value = "digitalSquareId") @PathVariable("digitalSquareId") Long digitalSquareId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> digitalSquare = digitalSquareService.mySelectMapById(digitalSquareId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(digitalSquare);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> fakeDeleteById(@ApiParam(name = "id", value = "digitalSquareId") @RequestBody Long digitalSquareId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<DigitalSquare> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/digitalSquare/delete");
            digitalSquareService.myFakeDeleteById(digitalSquareId);
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
     * @description : 根据ids批量假删除digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> fakeBatchDelete(@ApiParam(name = "ids", value = "digitalSquareIds") @RequestBody List<Long> digitalSquareIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<DigitalSquare> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/digitalSquare/batch_delete");
            resJson.setSuccess(digitalSquareService.myFakeBatchDelete(digitalSquareIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-03-17
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> digitalSquareCreateUpdate(@ApiParam(name = "digitalSquare", value = "DigitalSquare实体类") @RequestBody DigitalSquare digitalSquare) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<DigitalSquare> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/digitalSquare/create_update");
            Session session = subject.getSession();
            Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
            digitalSquare.setUserId(userId);
            digitalSquare.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            digitalSquare = digitalSquareService.myDigitalSquareCreateUpdate(digitalSquare);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(digitalSquare);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 立即购买digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @GetMapping(value = "/settlement/{digitalSquareId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/settlement/{digitalSquareId}", notes = "立即购买digitalSquare")
    @ResponseBody
    public JsonResult<Float> toSettlement(@ApiParam(name = "id", value = "digitalSquareId") @PathVariable(value = "digitalSquareId") Long digitalSquareId) throws ClientException, CustomizeException {

        Subject subject = SecurityUtils.getSubject();
        JsonResult<Float> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/digitalSquareOrder/create_update");
            Session session = SecurityUtils.getSubject().getSession();
            //先找出这个人余款有多少
            User user = (User) session.getAttribute(MagicalValue.USER_SESSION);
            Float balance = user.getBalance();
            balance = digitalSquareService.mySettlementById(digitalSquareId, balance, user.getId());

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
