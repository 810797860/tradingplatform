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
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialCollection;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialCollectionService;

import java.util.List;
import java.util.Map;

/**
 * @description : SportsSpecialCollection 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontSportsSpecialCollectionController")
@Api(value="/front/sportsSpecialCollection", description="SportsSpecialCollection 控制器")
@RequestMapping("/front/sportsSpecialCollection")
public class SportsSpecialCollectionController extends BaseController {

    @Autowired
    private SportsSpecialCollectionService sportsSpecialCollectionService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSportsSpecialCollectionList(@ApiParam(name = "sportsSpecialCollection", value = "SportsSpecialCollection 实体类") @RequestBody SportsSpecialCollection sportsSpecialCollection) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = sportsSpecialCollection.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> sportsSpecialCollectionPage = new Page(current, size);
            sportsSpecialCollectionPage = sportsSpecialCollectionService.mySelectPageWithParam(sportsSpecialCollectionPage, sportsSpecialCollection);
            resJson.setRecordsTotal(sportsSpecialCollectionPage.getTotal());
            resJson.setData(sportsSpecialCollectionPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取sportsSpecialCollectionMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{sportsSpecialCollectionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{sportsSpecialCollectionId}", notes = "根据id获取sportsSpecialCollectionMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSportsSpecialCollectionByIdForMap( @ApiParam(name = "id", value = "sportsSpecialCollectionId") @PathVariable("sportsSpecialCollectionId") Long sportsSpecialCollectionId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> sportsSpecialCollection = sportsSpecialCollectionService.mySelectMapById(sportsSpecialCollectionId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(sportsSpecialCollection);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除sportsSpecialCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除sportsSpecialCollection")
    @ResponseBody
    public JsonResult<SportsSpecialCollection> fakeDeleteById(@ApiParam(name = "id", value = "sportsSpecialCollectionId") @RequestBody Long sportsSpecialCollectionId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialCollection/delete");
                sportsSpecialCollectionService.myFakeDeleteById(sportsSpecialCollectionId);
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
     * @description : 根据ids批量假删除sportsSpecialCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除sportsSpecialCollection")
    @ResponseBody
    public JsonResult<SportsSpecialCollection> fakeBatchDelete(@ApiParam(name = "ids", value = "sportsSpecialCollectionIds") @RequestBody List<Long> sportsSpecialCollectionIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialCollection/batch_delete");
                resJson.setSuccess(sportsSpecialCollectionService.myFakeBatchDelete(sportsSpecialCollectionIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改sportsSpecialCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改sportsSpecialCollection")
    @ResponseBody
    public JsonResult<SportsSpecialCollection> sportsSpecialCollectionCreateUpdate(@ApiParam(name = "sportsSpecialCollection", value = "SportsSpecialCollection实体类") @RequestBody SportsSpecialCollection sportsSpecialCollection){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecialCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/sportsSpecialCollection/create_update");
                sportsSpecialCollection = sportsSpecialCollectionService.mySportsSpecialCollectionCreateUpdate(sportsSpecialCollection);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(sportsSpecialCollection);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
