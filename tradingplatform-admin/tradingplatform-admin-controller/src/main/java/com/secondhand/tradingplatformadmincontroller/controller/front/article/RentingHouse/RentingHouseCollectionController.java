package com.secondhand.tradingplatformadmincontroller.controller.front.article.RentingHouse;

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
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseCollection;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseCollectionService;

import java.util.List;
import java.util.Map;

/**
 * @description : RentingHouseCollection 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontRentingHouseCollectionController")
@Api(value="/front/rentingHouseCollection", description="RentingHouseCollection 控制器")
@RequestMapping("/front/rentingHouseCollection")
public class RentingHouseCollectionController extends BaseController {

    @Autowired
    private RentingHouseCollectionService rentingHouseCollectionService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getRentingHouseCollectionList(@ApiParam(name = "rentingHouseCollection", value = "RentingHouseCollection 实体类") @RequestBody RentingHouseCollection rentingHouseCollection) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = rentingHouseCollection.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> rentingHouseCollectionPage = new Page(current, size);
            rentingHouseCollectionPage = rentingHouseCollectionService.mySelectPageWithParam(rentingHouseCollectionPage, rentingHouseCollection);
            resJson.setRecordsTotal(rentingHouseCollectionPage.getTotal());
            resJson.setData(rentingHouseCollectionPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取rentingHouseCollectionMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{rentingHouseCollectionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{rentingHouseCollectionId}", notes = "根据id获取rentingHouseCollectionMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getRentingHouseCollectionByIdForMap( @ApiParam(name = "id", value = "rentingHouseCollectionId") @PathVariable("rentingHouseCollectionId") Long rentingHouseCollectionId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> rentingHouseCollection = rentingHouseCollectionService.mySelectMapById(rentingHouseCollectionId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(rentingHouseCollection);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除rentingHouseCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除rentingHouseCollection")
    @ResponseBody
    public JsonResult<RentingHouseCollection> fakeDeleteById(@ApiParam(name = "id", value = "rentingHouseCollectionId") @RequestBody Long rentingHouseCollectionId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseCollection/delete");
                rentingHouseCollectionService.myFakeDeleteById(rentingHouseCollectionId);
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
     * @description : 根据ids批量假删除rentingHouseCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除rentingHouseCollection")
    @ResponseBody
    public JsonResult<RentingHouseCollection> fakeBatchDelete(@ApiParam(name = "ids", value = "rentingHouseCollectionIds") @RequestBody List<Long> rentingHouseCollectionIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseCollection/batch_delete");
                resJson.setSuccess(rentingHouseCollectionService.myFakeBatchDelete(rentingHouseCollectionIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改rentingHouseCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改rentingHouseCollection")
    @ResponseBody
    public JsonResult<RentingHouseCollection> rentingHouseCollectionCreateUpdate(@ApiParam(name = "rentingHouseCollection", value = "RentingHouseCollection实体类") @RequestBody RentingHouseCollection rentingHouseCollection){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<RentingHouseCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/rentingHouseCollection/create_update");
                rentingHouseCollection = rentingHouseCollectionService.myRentingHouseCollectionCreateUpdate(rentingHouseCollection);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(rentingHouseCollection);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
