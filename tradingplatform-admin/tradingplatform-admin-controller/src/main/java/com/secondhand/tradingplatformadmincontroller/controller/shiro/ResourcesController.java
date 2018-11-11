package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformadminservice.service.shiro.ResourcesService;

/**
 * @description : Resources 控制器
 * @author : zhangjk
 * @since : Create in 2018-10-21
 */
@RestController
@Api(value="/admin/resources", description="Resources 控制器")
@RequestMapping("/admin/resources")
public class ResourcesController extends BaseController {

    @Autowired
    private ResourcesService resourcesService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-20
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Resources> getResourcesList(@ApiParam(name = "Resources", value = "Resources 实体类") @RequestBody Resources resources) {
            TableJson<Resources> resJson = new TableJson<>();
            Page resPage = resources.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Resources> resourcesPage = new Page<Resources>(current, size);
            resourcesPage = resourcesService.selectPageWithParam(resourcesPage, resources);
            resJson.setRecordsTotal(resourcesPage.getTotal());
            resJson.setData(resourcesPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取resources
     * @author : zhangjk
     * @since : Create in 2018-10-20
     */
    @GetMapping(value = "/get_by_id/{resourcesId}", produces = {"application/json"})
    @ApiOperation(value = "/get_by_id/{resourcesId}", notes = "根据id获取resources")
    public JsonResult<Resources> getResourcesById( @ApiParam(name = "id",value = "resourcesId") @PathVariable("resourcesId") Long resourcesId) {
            JsonResult<Resources> resJson = new JsonResult<>();
            Resources resources = resourcesService.selectById(resourcesId);
            resJson.setData(resources);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取resourcesMap
     * @author : zhangjk
     * @since : Create in 2018-10-20
     */
    @GetMapping(value = "/get_map_by_id/{resourcesId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{resourcesId}", notes = "根据id获取resourcesMap")
    public JsonResult<Map<String, Object>> getResourcesByIdForMap( @ApiParam(name = "id", value = "resourcesId") @PathVariable("resourcesId") Long resourcesId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> resources = resourcesService.selectMapById(resourcesId);
            resJson.setData(resources);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除resources
     * @author : zhangjk
     * @since : Create in 2018-10-20
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除resources")
    public JsonResult<Resources> fakeDeleteById(@ApiParam(name = "id", value = "resourcesId") @RequestBody Long resourcesId){
            JsonResult<Resources> resJson = new JsonResult<>();
            resJson.setSuccess(resourcesService.fakeDeleteById(resourcesId));
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除resources
     * @author : zhangjk
     * @since : Create in 2018-10-20
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除resources")
    public JsonResult<Resources> fakeBatchDelete(@ApiParam(name = "ids", value = "resourcesIds") @RequestBody List<Long> resourcesIds){
            JsonResult<Resources> resJson = new JsonResult<>();
            resJson.setSuccess(resourcesService.fakeBatchDelete(resourcesIds));
            return resJson;
    }

    /**
     * @description : 新增或修改resources
     * @author : zhangjk
     * @since : Create in 2018-10-20
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改resources")
    public JsonResult<Resources> resourcesCreateUpdate(@ApiParam(name = "Resources", value = "Resources实体类") @RequestBody Resources resources){
            resources = resourcesService.resourcesCreateUpdate(resources);
            JsonResult<Resources> resJson = new JsonResult<>();
            resJson.setData(resources);
            resJson.setSuccess(true);
            return resJson;
    }
}
