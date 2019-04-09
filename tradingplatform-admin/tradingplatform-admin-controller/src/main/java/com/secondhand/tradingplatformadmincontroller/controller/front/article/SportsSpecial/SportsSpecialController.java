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
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecial;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialService;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : SportsSpecial 控制器
 * @since : Create in 2019-03-16
 */
@Controller("frontSportsSpecialController")
@Api(value = "/front/sportsSpecial", description = "SportsSpecial 控制器")
@RequestMapping("/front/sportsSpecial")
public class SportsSpecialController extends BaseController {

    @Autowired
    private SportsSpecialService sportsSpecialService;


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSportsSpecialList(@ApiParam(name = "sportsSpecial", value = "SportsSpecial 实体类") @RequestBody SportsSpecial sportsSpecial) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = sportsSpecial.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> sportsSpecialPage = new Page(current, size);
        sportsSpecialPage = sportsSpecialService.mySelectPageWithParam(sportsSpecialPage, sportsSpecial);
        resJson.setRecordsTotal(sportsSpecialPage.getTotal());
        resJson.setData(sportsSpecialPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取sportsSpecialMap
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @GetMapping(value = "/get_map_by_id/{sportsSpecialId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{sportsSpecialId}", notes = "根据id获取sportsSpecialMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSportsSpecialByIdForMap(@ApiParam(name = "id", value = "sportsSpecialId") @PathVariable("sportsSpecialId") Long sportsSpecialId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> sportsSpecial = sportsSpecialService.mySelectMapById(sportsSpecialId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(sportsSpecial);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> fakeDeleteById(@ApiParam(name = "id", value = "sportsSpecialId") @RequestBody Long sportsSpecialId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SportsSpecial> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/sportsSpecial/delete");
            sportsSpecialService.myFakeDeleteById(sportsSpecialId);
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
     * @description : 根据ids批量假删除sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> fakeBatchDelete(@ApiParam(name = "ids", value = "sportsSpecialIds") @RequestBody List<Long> sportsSpecialIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SportsSpecial> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/sportsSpecial/batch_delete");
            resJson.setSuccess(sportsSpecialService.myFakeBatchDelete(sportsSpecialIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-03-16
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> sportsSpecialCreateUpdate(@ApiParam(name = "sportsSpecial", value = "SportsSpecial实体类") @RequestBody SportsSpecial sportsSpecial) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SportsSpecial> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/sportsSpecial/create_update");
            sportsSpecial = sportsSpecialService.mySportsSpecialCreateUpdate(sportsSpecial);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(sportsSpecial);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }
}
