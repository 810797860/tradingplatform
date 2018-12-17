package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
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
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuService;

/**
 * @description : Menu 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-29
 */
@RestController
@Api(value="/admin/menu", description="Menu 控制器")
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到menu的列表页面")
    public String toMenuList(@ApiParam(name = "Model", value = "model") Model model) {
        return "menu/tabulation";
    }

    /**
     * @description : 跳转到修改menu的页面
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @GetMapping(value = "/{menuId}/update.html")
    @ApiOperation(value = "/{menuId}/update.html", notes = "跳转到修改页面")
    public String toUpdateMenu(@ApiParam(name = "Model", value = "model") Model model, @PathVariable(value = "menuId") Long menuId) {
        //静态注入要回显的数据
        Map<String, Object> menu = menuService.mySelectMapById(menuId);
        model.addAttribute("menu", menu);
        return "menu/newMenu";
    }

    /**
     * @description : 跳转到新增menu的页面
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateMenu(@ApiParam(name = "Model", value = "model") Model model) {
        return "menu/newMenu";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Menu> getMenuList(@ApiParam(name = "Menu", value = "Menu 实体类") @RequestBody Menu menu) {
            TableJson<Menu> resJson = new TableJson<>();
            Page resPage = menu.getPage();
            resPage.setOrderByField("id");
            resPage.setAsc(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Menu> menuPage = new Page<Menu>(current, size);
            menuPage = menuService.mySelectPageWithParam(menuPage, menu);
            resJson.setRecordsTotal(menuPage.getTotal());
            resJson.setData(menuPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取menuMap
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @GetMapping(value = "/get_map_by_id/{menuId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{menuId}", notes = "根据id获取menuMap")
    public JsonResult<Map<String, Object>> getMenuByIdForMap( @ApiParam(name = "id", value = "menuId") @PathVariable("menuId") Long menuId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> menu = menuService.mySelectMapById(menuId);
            resJson.setData(menu);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除menu
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除menu")
    public JsonResult<Menu> fakeDeleteById(@ApiParam(name = "id", value = "menuId") @RequestBody Long menuId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Menu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/menu/delete");
                menuService.myFakeDeleteById(menuId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除menu
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除menu")
    public JsonResult<Menu> fakeBatchDelete(@ApiParam(name = "ids", value = "menuIds") @RequestBody List<Long> menuIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Menu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/menu/batch_delete");
                resJson.setSuccess(menuService.myFakeBatchDelete(menuIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改menu
     * @author : zhangjk
     * @since : Create in 2018-11-29
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改menu")
    public JsonResult<Menu> menuCreateUpdate(@ApiParam(name = "Menu", value = "Menu实体类") @RequestBody Menu menu){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Menu> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/menu/create_update");
                menu = menuService.myMenuCreateUpdate(menu);
                resJson.setData(menu);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
