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
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecialAdvisory;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecialAdvisoryService;

import java.util.List;
import java.util.Map;

/**
 * @description : SportsSpecialAdvisory 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontSportsSpecialAdvisoryController")
@Api(value="/front/sportsSpecialAdvisory", description="SportsSpecialAdvisory 控制器")
@RequestMapping("/front/sportsSpecialAdvisory")
public class SportsSpecialAdvisoryController extends BaseController {

    @Autowired
    private SportsSpecialAdvisoryService sportsSpecialAdvisoryService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSportsSpecialAdvisoryList(@ApiParam(name = "sportsSpecialAdvisory", value = "SportsSpecialAdvisory 实体类") @RequestBody SportsSpecialAdvisory sportsSpecialAdvisory) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = sportsSpecialAdvisory.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> sportsSpecialAdvisoryPage = new Page(current, size);
            sportsSpecialAdvisoryPage = sportsSpecialAdvisoryService.mySelectPageWithParam(sportsSpecialAdvisoryPage, sportsSpecialAdvisory);
            resJson.setRecordsTotal(sportsSpecialAdvisoryPage.getTotal());
            resJson.setData(sportsSpecialAdvisoryPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取sportsSpecialAdvisoryMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{sportsSpecialAdvisoryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{sportsSpecialAdvisoryId}", notes = "根据id获取sportsSpecialAdvisoryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSportsSpecialAdvisoryByIdForMap( @ApiParam(name = "id", value = "sportsSpecialAdvisoryId") @PathVariable("sportsSpecialAdvisoryId") Long sportsSpecialAdvisoryId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> sportsSpecialAdvisory = sportsSpecialAdvisoryService.mySelectMapById(sportsSpecialAdvisoryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(sportsSpecialAdvisory);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除sportsSpecialAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除sportsSpecialAdvisory")
    @ResponseBody
    public JsonResult<SportsSpecialAdvisory> fakeDeleteById(@ApiParam(name = "id", value = "sportsSpecialAdvisoryId") @RequestBody Long sportsSpecialAdvisoryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialAdvisory/delete");
                sportsSpecialAdvisoryService.myFakeDeleteById(sportsSpecialAdvisoryId);
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
     * @description : 根据ids批量假删除sportsSpecialAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除sportsSpecialAdvisory")
    @ResponseBody
    public JsonResult<SportsSpecialAdvisory> fakeBatchDelete(@ApiParam(name = "ids", value = "sportsSpecialAdvisoryIds") @RequestBody List<Long> sportsSpecialAdvisoryIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialAdvisory/batch_delete");
                resJson.setSuccess(sportsSpecialAdvisoryService.myFakeBatchDelete(sportsSpecialAdvisoryIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改sportsSpecialAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改sportsSpecialAdvisory")
    @ResponseBody
    public JsonResult<SportsSpecialAdvisory> sportsSpecialAdvisoryCreateUpdate(@ApiParam(name = "sportsSpecialAdvisory", value = "SportsSpecialAdvisory实体类") @RequestBody SportsSpecialAdvisory sportsSpecialAdvisory){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialAdvisory/create_update");
                sportsSpecialAdvisory = sportsSpecialAdvisoryService.mySportsSpecialAdvisoryCreateUpdate(sportsSpecialAdvisory);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(sportsSpecialAdvisory);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
