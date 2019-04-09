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
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareCollection;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareCollectionService;

import java.util.List;
import java.util.Map;

/**
 * @description : DigitalSquareCollection 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontDigitalSquareCollectionController")
@Api(value="/front/digitalSquareCollection", description="DigitalSquareCollection 控制器")
@RequestMapping("/front/digitalSquareCollection")
public class DigitalSquareCollectionController extends BaseController {

    @Autowired
    private DigitalSquareCollectionService digitalSquareCollectionService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getDigitalSquareCollectionList(@ApiParam(name = "digitalSquareCollection", value = "DigitalSquareCollection 实体类") @RequestBody DigitalSquareCollection digitalSquareCollection) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = digitalSquareCollection.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> digitalSquareCollectionPage = new Page(current, size);
            digitalSquareCollectionPage = digitalSquareCollectionService.mySelectPageWithParam(digitalSquareCollectionPage, digitalSquareCollection);
            resJson.setRecordsTotal(digitalSquareCollectionPage.getTotal());
            resJson.setData(digitalSquareCollectionPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取digitalSquareCollectionMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{digitalSquareCollectionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{digitalSquareCollectionId}", notes = "根据id获取digitalSquareCollectionMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getDigitalSquareCollectionByIdForMap( @ApiParam(name = "id", value = "digitalSquareCollectionId") @PathVariable("digitalSquareCollectionId") Long digitalSquareCollectionId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> digitalSquareCollection = digitalSquareCollectionService.mySelectMapById(digitalSquareCollectionId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(digitalSquareCollection);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除digitalSquareCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除digitalSquareCollection")
    @ResponseBody
    public JsonResult<DigitalSquareCollection> fakeDeleteById(@ApiParam(name = "id", value = "digitalSquareCollectionId") @RequestBody Long digitalSquareCollectionId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareCollection/delete");
                digitalSquareCollectionService.myFakeDeleteById(digitalSquareCollectionId);
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
     * @description : 根据ids批量假删除digitalSquareCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除digitalSquareCollection")
    @ResponseBody
    public JsonResult<DigitalSquareCollection> fakeBatchDelete(@ApiParam(name = "ids", value = "digitalSquareCollectionIds") @RequestBody List<Long> digitalSquareCollectionIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareCollection/batch_delete");
                resJson.setSuccess(digitalSquareCollectionService.myFakeBatchDelete(digitalSquareCollectionIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改digitalSquareCollection
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改digitalSquareCollection")
    @ResponseBody
    public JsonResult<DigitalSquareCollection> digitalSquareCollectionCreateUpdate(@ApiParam(name = "digitalSquareCollection", value = "DigitalSquareCollection实体类") @RequestBody DigitalSquareCollection digitalSquareCollection){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquareCollection> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/digitalSquareCollection/create_update");
                digitalSquareCollection = digitalSquareCollectionService.myDigitalSquareCollectionCreateUpdate(digitalSquareCollection);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(digitalSquareCollection);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
