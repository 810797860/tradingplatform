package com.secondhand.tradingplatformadmincontroller.controller.business;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.business.Annex;
import com.secondhand.tradingplatformadminservice.service.business.AnnexService;

/**
 * @description : Annex 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-14
 */
@RestController
@Api(value="/admin/annex", description="Annex 控制器")
@RequestMapping("/admin/annex")
public class AnnexController extends BaseController {

    @Autowired
    private AnnexService annexService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到annex的列表页面")
    public String toAnnexList(@ApiParam(name = "Model", value = "model") Model model) {
        return "annex/tabulation";
    }

    /**
     * @description : 跳转到修改annex的页面
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/{annexId}/update.html")
    @ApiOperation(value = "/{annexId}/update.html", notes = "跳转到修改页面")
    public String toUpdateAnnex(@ApiParam(name = "Model", value = "model") Model model, @PathVariable(value = "annexId") Long annexId) {
        //静态注入要回显的数据
        Map<String, Object> annex = annexService.mySelectMapById(annexId);
        model.addAttribute("annex", annex);
        return "annex/newAnnex";
    }

    /**
     * @description : 跳转到新增annex的页面
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateAnnex(@ApiParam(name = "Model", value = "model") Model model) {
        return "annex/newAnnex";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Annex> getAnnexList(@ApiParam(name = "Annex", value = "Annex 实体类") @RequestBody Annex annex) {
            TableJson<Annex> resJson = new TableJson<>();
            Page resPage = annex.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Annex> annexPage = new Page<Annex>(current, size);
            annexPage = annexService.mySelectPageWithParam(annexPage, annex);
            resJson.setRecordsTotal(annexPage.getTotal());
            resJson.setData(annexPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取annexMap
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/get_map_by_id/{annexId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{annexId}", notes = "根据id获取annexMap")
    public JsonResult<Map<String, Object>> getAnnexByIdForMap( @ApiParam(name = "id", value = "annexId") @PathVariable("annexId") Long annexId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> annex = annexService.mySelectMapById(annexId);
            resJson.setData(annex);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除annex
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除annex")
    public JsonResult<Annex> fakeDeleteById(@ApiParam(name = "id", value = "annexId") @RequestBody Long annexId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Annex> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/annex/delete");
                annexService.myFakeDeleteById(annexId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除annex
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除annex")
    public JsonResult<Annex> fakeBatchDelete(@ApiParam(name = "ids", value = "annexIds") @RequestBody List<Long> annexIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Annex> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/annex/batch_delete");
                resJson.setSuccess(annexService.myFakeBatchDelete(annexIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改annex
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改annex")
    public JsonResult<Annex> annexCreateUpdate(@ApiParam(name = "Annex", value = "Annex实体类") @RequestBody Annex annex){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Annex> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/annex/create_update");
                annex = annexService.myAnnexCreateUpdate(annex);
                resJson.setData(annex);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
