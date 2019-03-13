package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Resources;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.MenuButtonService;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.ResourcesService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;

import javax.servlet.http.HttpSession;

/**
 * @description : Resources 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-12
 */
@Controller
@Api(value="/admin/resources", description="Resources 控制器")
@RequestMapping("/admin/resources")
public class ResourcesController extends BaseController {

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到resources的列表页面")
    public String toResourcesList(@ApiParam(name = "model", value = "Model") Model model,
                                  @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                  @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/resources/tabulation";
    }

    /**
     * @description : 跳转到修改或新增resources的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{resourcesId}/update.html", "/create.html"})
    @ApiOperation(value = "/{resourcesId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyResources(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "resourcesId", value = "ResourcesId") @PathVariable(value = "resourcesId", required = false) Long resourcesId) {

        Map<String, Object> resources = new HashMap<>();
        //判空
        if (resourcesId != null) {
            //根据resourcesId查找记录回显的数据
            resources = resourcesService.mySelectMapById(resourcesId);
        }
        //静态注入
        model.addAttribute("resources", resources);
        return "system/resources/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Resources> getResourcesList(@ApiParam(name = "resources", value = "Resources 实体类") @RequestBody Resources resources){
        TableJson<Resources> resJson = new TableJson<>();
        Page resPage = resources.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Resources> resourcesPage = new Page<>(current, size);
        resourcesPage = resourcesService.mySelectPageWithParam(resourcesPage, resources);
        resJson.setRecordsTotal(resourcesPage.getTotal());
        resJson.setData(resourcesPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 获取配置子列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/query_by_role/{roleId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_by_role/{roleId}", notes="获取分页列表")
    @ResponseBody
    public JsonResult<List<Resources>> getResourcesListWithRoleId(@ApiParam(name = "resources", value = "Resources 实体类") @RequestBody Resources resources,
                                                 @ApiParam(name = "roleId", value = "菜单id") @PathVariable(name = "roleId", required = false) Long roleId) {
        JsonResult<List<Resources>> resJson = new JsonResult<>();
        List<Resources> resourcesList = resourcesService.mySelectListWithParam(resources, roleId);
        resJson.setData(resourcesList);
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
