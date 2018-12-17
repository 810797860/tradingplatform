package com.secondhand.tradingplatformadmincontroller.controller.system;

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
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminservice.service.system.SelectItemService;

/**
 * @description : SelectItem 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-09
 */
@RestController
@Api(value="/admin/selectItem", description="SelectItem 控制器")
@RequestMapping("/admin/selectItem")
public class SelectItemController extends BaseController {

    @Autowired
    private SelectItemService selectItemService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到selectItem的列表页面")
    public String toSelectItemList(@ApiParam(name = "Model", value = "model") Model model) {
        return "selectItem/tabulation";
    }

    /**
     * @description : 跳转到修改selectItem的页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/{selectItemId}/update.html")
    @ApiOperation(value = "/{selectItemId}/update.html", notes = "跳转到修改页面")
    public String toUpdateSelectItem(@ApiParam(name = "Model", value = "model") Model model, @PathVariable(value = "selectItemId") Long selectItemId) {
        //静态注入要回显的数据
        Map<String, Object> selectItem = selectItemService.selectMapById(selectItemId);
        model.addAttribute("selectItem", selectItem);
        return "selectItem/newSelectItem";
    }

    /**
     * @description : 跳转到新增selectItem的页面
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateSelectItem(@ApiParam(name = "Model", value = "model") Model model) {
        return "selectItem/newSelectItem";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<SelectItem> getSelectItemList(@ApiParam(name = "SelectItem", value = "SelectItem 实体类") @RequestBody SelectItem selectItem) {
            TableJson<SelectItem> resJson = new TableJson<>();
            Page resPage = selectItem.getPage();
            selectItem.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<SelectItem> selectItemPage = new Page<SelectItem>(current, size);
            selectItemPage = selectItemService.selectPageWithParam(selectItemPage, selectItem);
            resJson.setRecordsTotal(selectItemPage.getTotal());
            resJson.setData(selectItemPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取selectItemMap
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @GetMapping(value = "/get_map_by_id/{selectItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{selectItemId}", notes = "根据id获取selectItemMap")
    public JsonResult<Map<String, Object>> getSelectItemByIdForMap( @ApiParam(name = "id", value = "selectItemId") @PathVariable("selectItemId") Long selectItemId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> selectItem = selectItemService.selectMapById(selectItemId);
            resJson.setData(selectItem);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除selectItem
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除selectItem")
    public JsonResult<SelectItem> fakeDeleteById(@ApiParam(name = "id", value = "selectItemId") @RequestBody Long selectItemId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SelectItem> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/selectItem/delete");
                selectItemService.fakeDeleteById(selectItemId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除selectItem
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除selectItem")
    public JsonResult<SelectItem> fakeBatchDelete(@ApiParam(name = "ids", value = "selectItemIds") @RequestBody List<Long> selectItemIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SelectItem> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/selectItem/batch_delete");
                resJson.setSuccess(selectItemService.fakeBatchDelete(selectItemIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改selectItem
     * @author : zhangjk
     * @since : Create in 2018-11-09
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改selectItem")
    public JsonResult<SelectItem> selectItemCreateUpdate(@ApiParam(name = "SelectItem", value = "SelectItem实体类") @RequestBody SelectItem selectItem){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SelectItem> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/selectItem/create_update");
                selectItem = selectItemService.selectItemCreateUpdate(selectItem);
                resJson.setData(selectItem);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
