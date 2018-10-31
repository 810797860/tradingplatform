package com.secondhand.tradingplatformadmincontroller.controller.system;

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
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminservice.service.system.SelectItemService;

/**
 * @description : SelectItem 控制器
 * @author : zhangjk
 * @since : Create in 2018-10-31
 */
@RestController
@Api(value="/admin/selectItem", description="SelectItem 控制器")
@RequestMapping("/admin/selectItem")
public class SelectItemController extends BaseController {

    @Autowired
    private SelectItemService selectItemService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-31
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<SelectItem> getSelectItemList(@ApiParam(name = "SelectItem", value = "SelectItem 实体类") @RequestBody SelectItem selectItem) {
            TableJson<SelectItem> resJson = new TableJson<>();
            Page resPage = selectItem.getPage();
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
     * @description : 通过id获取selectItem
     * @author : zhangjk
     * @since : Create in 2018-10-31
     */
    @GetMapping(value = "/get_by_id/{selectItemId}", produces = {"application/json"})
    @ApiOperation(value = "/get_by_id/{selectItemId}", notes = "根据id获取selectItem")
    public JsonResult<SelectItem> getSelectItemById( @ApiParam(name = "id",value = "selectItemId") @PathVariable("selectItemId") Long selectItemId) {
            JsonResult<SelectItem> resJson = new JsonResult<>();
            SelectItem selectItem = selectItemService.selectOneByObj(selectItemId);
            resJson.setData(selectItem);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取selectItemMap
     * @author : zhangjk
     * @since : Create in 2018-10-31
     */
    @GetMapping(value = "/get_map_by_id/{selectItemId}", produces = {"application/json"})
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
     * @since : Create in 2018-10-31
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除selectItem")
    public JsonResult<SelectItem> fakeDeleteById(@ApiParam(name = "id", value = "selectItemId") @RequestBody Long selectItemId){
            JsonResult<SelectItem> resJson = new JsonResult<>();
            resJson.setSuccess(selectItemService.fakeDeleteById(selectItemId));
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除selectItem
     * @author : zhangjk
     * @since : Create in 2018-10-31
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除selectItem")
    public JsonResult<SelectItem> fakeBatchDelete(@ApiParam(name = "ids", value = "selectItemIds") @RequestBody List<Long> selectItemIds){
            JsonResult<SelectItem> resJson = new JsonResult<>();
            resJson.setSuccess(selectItemService.fakeBatchDelete(selectItemIds));
            return resJson;
    }

    /**
     * @description : 新增或修改selectItem
     * @author : zhangjk
     * @since : Create in 2018-10-31
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改selectItem")
    public JsonResult<SelectItem> selectItemCreateUpdate(@ApiParam(name = "SelectItem", value = "SelectItem实体类") @RequestBody SelectItem selectItem){
            selectItem = selectItemService.selectItemCreateUpdate(selectItem);
            JsonResult<SelectItem> resJson = new JsonResult<>();
            resJson.setData(selectItem);
            resJson.setSuccess(true);
            return resJson;
    }
}
