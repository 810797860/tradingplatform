package com.secondhand.tradingplatformadmincontroller.controller.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
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
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceCollection;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceCollectionService;

import java.util.List;
import java.util.Map;

/**
 * @description : ElectricApplianceCollection 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontElectricApplianceCollectionController")
@Api(value="/front/electricApplianceCollection", description="ElectricApplianceCollection 控制器")
@RequestMapping("/front/electricApplianceCollection")
public class ElectricApplianceCollectionController extends BaseController {

    @Autowired
    private ElectricApplianceCollectionService electricApplianceCollectionService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getElectricApplianceCollectionList(@ApiParam(name = "electricApplianceCollection", value = "ElectricApplianceCollection 实体类") @RequestBody ElectricApplianceCollection electricApplianceCollection) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = electricApplianceCollection.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> electricApplianceCollectionPage = new Page(current, size);
            electricApplianceCollectionPage = electricApplianceCollectionService.mySelectPageWithParam(electricApplianceCollectionPage, electricApplianceCollection);
            resJson.setRecordsTotal(electricApplianceCollectionPage.getTotal());
            resJson.setData(electricApplianceCollectionPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 用户获取收藏electricAppliance的ids列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/query_collection", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_collection", notes="用户获取收藏electricAppliance的ids列表")
    @ResponseBody
    public JsonResult<List<Object>> getElectricApplianceAdvisoryCollectionList() {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<List<Object>> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/front/electricApplianceCollection/query_collection");
            Session session = subject.getSession();
            Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
            resJson.setData(electricApplianceCollectionService.mySelectCollectionList(userId));
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
     * @description : 通过id获取electricApplianceCollectionMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{electricApplianceCollectionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{electricApplianceCollectionId}", notes = "根据id获取electricApplianceCollectionMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getElectricApplianceCollectionByIdForMap( @ApiParam(name = "id", value = "electricApplianceCollectionId") @PathVariable("electricApplianceCollectionId") Long electricApplianceCollectionId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> electricApplianceCollection = electricApplianceCollectionService.mySelectMapById(electricApplianceCollectionId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(electricApplianceCollection);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除electricApplianceCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除electricApplianceCollection")
    @ResponseBody
    public JsonResult<ElectricApplianceCollection> fakeDeleteById(@ApiParam(name = "id", value = "electricApplianceCollectionId") @RequestBody Long electricApplianceCollectionId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceCollection/delete");
                electricApplianceCollectionService.myFakeDeleteById(electricApplianceCollectionId);
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
     * @description : 根据ids批量假删除electricApplianceCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除electricApplianceCollection")
    @ResponseBody
    public JsonResult<ElectricApplianceCollection> fakeBatchDelete(@ApiParam(name = "ids", value = "electricApplianceCollectionIds") @RequestBody List<Long> electricApplianceCollectionIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceCollection/batch_delete");
                resJson.setSuccess(electricApplianceCollectionService.myFakeBatchDelete(electricApplianceCollectionIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改electricApplianceCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改electricApplianceCollection")
    @ResponseBody
    public JsonResult<ElectricApplianceCollection> electricApplianceCollectionCreateUpdate(@ApiParam(name = "electricApplianceCollection", value = "ElectricApplianceCollection实体类") @RequestBody ElectricApplianceCollection electricApplianceCollection){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricApplianceCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/electricApplianceCollection/create_update");
                Session session = subject.getSession();
                Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
                electricApplianceCollection.setUserId(userId);
                electricApplianceCollection = electricApplianceCollectionService.myElectricApplianceCollectionCreateUpdate(electricApplianceCollection);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(electricApplianceCollection);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
