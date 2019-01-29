package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformadminservice.service.shiro.ResourcesService;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;

/**
 * @description : Resources 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-12
 */
@RestController
@Api(value="/admin/resources", description="Resources 控制器")
@RequestMapping("/admin/resources")
public class ResourcesController extends BaseController {

    @Autowired
    private ResourcesService resourcesService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到resources的列表页面")
    public String toResourcesList(@ApiParam(name = "model", value = "Model") Model model) {
        return "resources/tabulation";
    }

    /**
     * @description : 跳转到修改resources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/{resourcesId}/update.html")
    @ApiOperation(value = "/{resourcesId}/update.html", notes = "跳转到修改页面")
    public String toUpdateResources(@ApiParam(name = "model", value = "Model") Model model, @PathVariable(value = "resourcesId") Long resourcesId) {
        //静态注入要回显的数据
        Map<String, Object> resources = resourcesService.mySelectMapById(resourcesId);
        model.addAttribute("resources", resources);
        return "resources/newResources";
    }

    /**
     * @description : 跳转到新增resources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateResources(@ApiParam(name = "model", value = "Model") Model model) {
        return "resources/newResources";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Resources> getResourcesList(@ApiParam(name = "Resources", value = "Resources 实体类") @RequestBody Resources resources) {
            TableJson<Resources> resJson = new TableJson<>();
            Page resPage = resources.getPage();
            resources.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Resources> resourcesPage = new Page<Resources>(current, size);
            resourcesPage = resourcesService.mySelectPageWithParam(resourcesPage, resources);
            resJson.setRecordsTotal(resourcesPage.getTotal());
            resJson.setData(resourcesPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取resourcesMap
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @GetMapping(value = "/get_map_by_id/{resourcesId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{resourcesId}", notes = "根据id获取resourcesMap")
    public JsonResult<Map<String, Object>> getResourcesByIdForMap( @ApiParam(name = "id", value = "resourcesId") @PathVariable("resourcesId") Long resourcesId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> resources = resourcesService.mySelectMapById(resourcesId);
            resJson.setData(resources);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除resources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除resources")
    public JsonResult<Resources> fakeDeleteById(@ApiParam(name = "id", value = "resourcesId") @RequestBody Long resourcesId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Resources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/resources/delete");
                resourcesService.myFakeDeleteById(resourcesId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除resources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除resources")
    public JsonResult<Resources> fakeBatchDelete(@ApiParam(name = "ids", value = "resourcesIds") @RequestBody List<Long> resourcesIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Resources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/resources/batch_delete");
                resJson.setSuccess(resourcesService.myFakeBatchDelete(resourcesIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改resources
     * @author : zhangjk
     * @since : Create in 2018-11-12
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改resources")
    public JsonResult<Resources> resourcesCreateUpdate(@ApiParam(name = "Resources", value = "Resources实体类") @RequestBody Resources resources){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Resources> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/resources/create_update");
                resources = resourcesService.myResourcesCreateUpdate(resources);
                resJson.setData(resources);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
