package com.secondhand.tradingplatformadmincontroller.controller.admin.article;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.MenuButtonService;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricAppliance;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceService;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @description : ElectricAppliance 控制器
 * @author : zhangjk
 * @since : Create in 2019-05-02
 */
@Controller("adminElectricApplianceController")
@Api(value="/admin/electricAppliance", description="ElectricAppliance 控制器")
@RequestMapping("/admin/electricAppliance")
public class ElectricApplianceController extends BaseController {

    @Autowired
    private ElectricApplianceService electricApplianceService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到electricAppliance的列表页面")
    public String toElectricApplianceList(@ApiParam(name = "model", value = "Model") Model model,
                                    @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                    @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/electricAppliance/tabulation";
    }

    /**
     * @description : 跳转到修改或新增electricAppliance的页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = {"/{electricApplianceId}/update.html", "/create.html"})
    @ApiOperation(value = "/{electricApplianceId}/update.html、/create.html", notes = "跳转到修改或新增electricAppliance的页面")
    public String toModifyElectricAppliance(@ApiParam(name = "model", value = "Model") Model model,
    @ApiParam(name = "electricApplianceId", value = "ElectricApplianceId") @PathVariable(value = "electricApplianceId", required = false) Long electricApplianceId) {
        Map<String, Object> electricAppliance = new HashMap<>();
        //判空
        if (electricApplianceId != null) {
            electricAppliance = electricApplianceService.mySelectMapById(electricApplianceId);
        }
        //静态注入
        //静态注入根据electricApplianceId查找记录回显的数据
        model.addAttribute("electricAppliance", electricAppliance);
        return "system/electricAppliance/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getElectricApplianceList(@ApiParam(name = "electricAppliance", value = "ElectricAppliance 实体类") @RequestBody ElectricAppliance electricAppliance) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = electricAppliance.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> electricAppliancePage = new Page(current, size);
            electricAppliancePage = electricApplianceService.mySelectPageWithParam(electricAppliancePage, electricAppliance);
            resJson.setRecordsTotal(electricAppliancePage.getTotal());
            resJson.setData(electricAppliancePage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取electricApplianceMap
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/get_map_by_id/{electricApplianceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{electricApplianceId}", notes = "根据id获取electricApplianceMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getElectricApplianceByIdForMap( @ApiParam(name = "id", value = "electricApplianceId") @PathVariable("electricApplianceId") Long electricApplianceId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> electricAppliance = electricApplianceService.mySelectMapById(electricApplianceId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(electricAppliance);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除electricAppliance
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除electricAppliance")
    @ResponseBody
    public JsonResult<ElectricAppliance> fakeDeleteById(@ApiParam(name = "id", value = "electricApplianceId") @RequestBody Long electricApplianceId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricAppliance> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/electricAppliance/delete");
                electricApplianceService.myFakeDeleteById(electricApplianceId);
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
     * @description : 根据ids批量假删除electricAppliance
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除electricAppliance")
    @ResponseBody
    public JsonResult<ElectricAppliance> fakeBatchDelete(@ApiParam(name = "ids", value = "electricApplianceIds") @RequestBody List<Long> electricApplianceIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricAppliance> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/electricAppliance/batch_delete");
                resJson.setSuccess(electricApplianceService.myFakeBatchDelete(electricApplianceIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量审核通过electricAppliance
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_pass", notes = "根据ids批量审核通过electricAppliance")
    @ResponseBody
    public JsonResult<ElectricAppliance> examinationBatchPass(@ApiParam(name = "ids", value = "electricApplianceIds") @RequestBody List<Long> electricApplianceIds){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<ElectricAppliance> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/electricAppliance/batch_pass");
            resJson.setSuccess(electricApplianceService.myExaminationBatchPass(electricApplianceIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 审核不通过electricAppliance
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/not_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/not_pass", notes = "审核不通过electricAppliance")
    @ResponseBody
    public JsonResult<ElectricAppliance> examinationNotPass(@ApiParam(name = "electricAppliance", value = "ElectricAppliance实体类") @RequestBody ElectricAppliance electricAppliance){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<ElectricAppliance> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/electricAppliance/not_pass");
            electricAppliance.setBackCheckStatus(SystemSelectItem.BACK_STATUS_AUDIT_NOT_PASSED);
            resJson.setSuccess(electricApplianceService.myUpdateById(electricAppliance));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改electricAppliance
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改electricAppliance")
    @ResponseBody
    public JsonResult<ElectricAppliance> electricApplianceCreateUpdate(@ApiParam(name = "electricAppliance", value = "ElectricAppliance实体类") @RequestBody ElectricAppliance electricAppliance){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<ElectricAppliance> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/electricAppliance/create_update");
                electricAppliance = electricApplianceService.myElectricApplianceCreateUpdate(electricAppliance);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(electricAppliance);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
