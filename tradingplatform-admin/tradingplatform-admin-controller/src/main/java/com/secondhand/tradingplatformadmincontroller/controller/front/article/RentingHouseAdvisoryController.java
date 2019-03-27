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
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouseAdvisory;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouseAdvisoryService;

import java.util.List;
import java.util.Map;

/**
 * @description : RentingHouseAdvisory 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontRentingHouseAdvisoryController")
@Api(value="/front/rentingHouseAdvisory", description="RentingHouseAdvisory 控制器")
@RequestMapping("/front/rentingHouseAdvisory")
public class RentingHouseAdvisoryController extends BaseController {

    @Autowired
    private RentingHouseAdvisoryService rentingHouseAdvisoryService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getRentingHouseAdvisoryList(@ApiParam(name = "rentingHouseAdvisory", value = "RentingHouseAdvisory 实体类") @RequestBody RentingHouseAdvisory rentingHouseAdvisory) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = rentingHouseAdvisory.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> rentingHouseAdvisoryPage = new Page(current, size);
            rentingHouseAdvisoryPage = rentingHouseAdvisoryService.mySelectPageWithParam(rentingHouseAdvisoryPage, rentingHouseAdvisory);
            resJson.setRecordsTotal(rentingHouseAdvisoryPage.getTotal());
            resJson.setData(rentingHouseAdvisoryPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取rentingHouseAdvisoryMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{rentingHouseAdvisoryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{rentingHouseAdvisoryId}", notes = "根据id获取rentingHouseAdvisoryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRentingHouseAdvisoryByIdForMap( @ApiParam(name = "id", value = "rentingHouseAdvisoryId") @PathVariable("rentingHouseAdvisoryId") Long rentingHouseAdvisoryId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> rentingHouseAdvisory = rentingHouseAdvisoryService.mySelectMapById(rentingHouseAdvisoryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(rentingHouseAdvisory);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除rentingHouseAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除rentingHouseAdvisory")
    @ResponseBody
    public JsonResult<RentingHouseAdvisory> fakeDeleteById(@ApiParam(name = "id", value = "rentingHouseAdvisoryId") @RequestBody Long rentingHouseAdvisoryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseAdvisory/delete");
                rentingHouseAdvisoryService.myFakeDeleteById(rentingHouseAdvisoryId);
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
     * @description : 根据ids批量假删除rentingHouseAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除rentingHouseAdvisory")
    @ResponseBody
    public JsonResult<RentingHouseAdvisory> fakeBatchDelete(@ApiParam(name = "ids", value = "rentingHouseAdvisoryIds") @RequestBody List<Long> rentingHouseAdvisoryIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseAdvisory/batch_delete");
                resJson.setSuccess(rentingHouseAdvisoryService.myFakeBatchDelete(rentingHouseAdvisoryIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改rentingHouseAdvisory
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改rentingHouseAdvisory")
    @ResponseBody
    public JsonResult<RentingHouseAdvisory> rentingHouseAdvisoryCreateUpdate(@ApiParam(name = "rentingHouseAdvisory", value = "RentingHouseAdvisory实体类") @RequestBody RentingHouseAdvisory rentingHouseAdvisory){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseAdvisory> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseAdvisory/create_update");
                rentingHouseAdvisory = rentingHouseAdvisoryService.myRentingHouseAdvisoryCreateUpdate(rentingHouseAdvisory);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(rentingHouseAdvisory);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
