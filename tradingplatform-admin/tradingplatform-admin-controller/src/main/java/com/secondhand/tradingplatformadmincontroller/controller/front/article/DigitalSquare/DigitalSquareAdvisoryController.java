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
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareAdvisory;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareAdvisoryService;

import java.util.List;
import java.util.Map;

/**
 * @description : DigitalSquareAdvisory 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontDigitalSquareAdvisoryController")
@Api(value="/front/digitalSquareAdvisory", description="DigitalSquareAdvisory 控制器")
@RequestMapping("/front/digitalSquareAdvisory")
public class DigitalSquareAdvisoryController extends BaseController {

    @Autowired
    private DigitalSquareAdvisoryService digitalSquareAdvisoryService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getDigitalSquareAdvisoryList(@ApiParam(name = "digitalSquareAdvisory", value = "DigitalSquareAdvisory 实体类") @RequestBody DigitalSquareAdvisory digitalSquareAdvisory) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = digitalSquareAdvisory.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> digitalSquareAdvisoryPage = new Page(current, size);
            digitalSquareAdvisoryPage = digitalSquareAdvisoryService.mySelectPageWithParam(digitalSquareAdvisoryPage, digitalSquareAdvisory);
            resJson.setRecordsTotal(digitalSquareAdvisoryPage.getTotal());
            resJson.setData(digitalSquareAdvisoryPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取digitalSquareAdvisoryMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{digitalSquareAdvisoryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{digitalSquareAdvisoryId}", notes = "根据id获取digitalSquareAdvisoryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getDigitalSquareAdvisoryByIdForMap( @ApiParam(name = "id", value = "digitalSquareAdvisoryId") @PathVariable("digitalSquareAdvisoryId") Long digitalSquareAdvisoryId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> digitalSquareAdvisory = digitalSquareAdvisoryService.mySelectMapById(digitalSquareAdvisoryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(digitalSquareAdvisory);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除digitalSquareAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除digitalSquareAdvisory")
    @ResponseBody
    public JsonResult<DigitalSquareAdvisory> fakeDeleteById(@ApiParam(name = "id", value = "digitalSquareAdvisoryId") @RequestBody Long digitalSquareAdvisoryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareAdvisory/delete");
                digitalSquareAdvisoryService.myFakeDeleteById(digitalSquareAdvisoryId);
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
     * @description : 根据ids批量假删除digitalSquareAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除digitalSquareAdvisory")
    @ResponseBody
    public JsonResult<DigitalSquareAdvisory> fakeBatchDelete(@ApiParam(name = "ids", value = "digitalSquareAdvisoryIds") @RequestBody List<Long> digitalSquareAdvisoryIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareAdvisory/batch_delete");
                resJson.setSuccess(digitalSquareAdvisoryService.myFakeBatchDelete(digitalSquareAdvisoryIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改digitalSquareAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改digitalSquareAdvisory")
    @ResponseBody
    public JsonResult<DigitalSquareAdvisory> digitalSquareAdvisoryCreateUpdate(@ApiParam(name = "digitalSquareAdvisory", value = "DigitalSquareAdvisory实体类") @RequestBody DigitalSquareAdvisory digitalSquareAdvisory){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareAdvisory/create_update");
                digitalSquareAdvisory = digitalSquareAdvisoryService.myDigitalSquareAdvisoryCreateUpdate(digitalSquareAdvisory);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(digitalSquareAdvisory);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
