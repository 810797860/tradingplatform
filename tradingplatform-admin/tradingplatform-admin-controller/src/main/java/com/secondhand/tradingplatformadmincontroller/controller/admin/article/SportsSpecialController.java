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
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecial;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialService;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @description : SportsSpecial 控制器
 * @author : zhangjk
 * @since : Create in 2019-05-02
 */
@Controller("adminSportsSpecialController")
@Api(value="/admin/sportsSpecial", description="SportsSpecial 控制器")
@RequestMapping("/admin/sportsSpecial")
public class SportsSpecialController extends BaseController {

    @Autowired
    private SportsSpecialService sportsSpecialService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到sportsSpecial的列表页面")
    public String toSportsSpecialList(@ApiParam(name = "model", value = "Model") Model model,
                                    @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                    @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/sportsSpecial/tabulation";
    }

    /**
     * @description : 跳转到修改或新增sportsSpecial的页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = {"/{sportsSpecialId}/update.html", "/create.html"})
    @ApiOperation(value = "/{sportsSpecialId}/update.html、/create.html", notes = "跳转到修改或新增sportsSpecial的页面")
    public String toModifySportsSpecial(@ApiParam(name = "model", value = "Model") Model model,
    @ApiParam(name = "sportsSpecialId", value = "SportsSpecialId") @PathVariable(value = "sportsSpecialId", required = false) Long sportsSpecialId) {
        Map<String, Object> sportsSpecial = new HashMap<>();
        //判空
        if (sportsSpecialId != null) {
            sportsSpecial = sportsSpecialService.mySelectMapById(sportsSpecialId);
        }
        //静态注入
        //静态注入根据sportsSpecialId查找记录回显的数据
        model.addAttribute("sportsSpecial", sportsSpecial);
        return "system/sportsSpecial/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getSportsSpecialList(@ApiParam(name = "sportsSpecial", value = "SportsSpecial 实体类") @RequestBody SportsSpecial sportsSpecial) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = sportsSpecial.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> sportsSpecialPage = new Page(current, size);
            sportsSpecialPage = sportsSpecialService.mySelectPageWithParam(sportsSpecialPage, sportsSpecial);
            resJson.setRecordsTotal(sportsSpecialPage.getTotal());
            resJson.setData(sportsSpecialPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取sportsSpecialMap
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/get_map_by_id/{sportsSpecialId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{sportsSpecialId}", notes = "根据id获取sportsSpecialMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getSportsSpecialByIdForMap( @ApiParam(name = "id", value = "sportsSpecialId") @PathVariable("sportsSpecialId") Long sportsSpecialId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> sportsSpecial = sportsSpecialService.mySelectMapById(sportsSpecialId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(sportsSpecial);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> fakeDeleteById(@ApiParam(name = "id", value = "sportsSpecialId") @RequestBody Long sportsSpecialId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecial> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/sportsSpecial/delete");
                sportsSpecialService.myFakeDeleteById(sportsSpecialId);
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
     * @description : 根据ids批量审核通过sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_pass", notes = "根据ids批量审核通过sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> examinationBatchPass(@ApiParam(name = "ids", value = "sportsSpecialIds") @RequestBody List<Long> sportsSpecialIds){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SportsSpecial> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/sportsSpecial/batch_pass");
            resJson.setSuccess(sportsSpecialService.myExaminationBatchPass(sportsSpecialIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 审核不通过sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/not_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/not_pass", notes = "审核不通过sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> examinationNotPass(@ApiParam(name = "sportsSpecial", value = "SportsSpecial实体类") @RequestBody SportsSpecial sportsSpecial){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SportsSpecial> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/sportsSpecial/not_pass");
            sportsSpecial.setBackCheckStatus(SystemSelectItem.BACK_STATUS_AUDIT_NOT_PASSED);
            resJson.setSuccess(sportsSpecialService.myUpdateById(sportsSpecial));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> fakeBatchDelete(@ApiParam(name = "ids", value = "sportsSpecialIds") @RequestBody List<Long> sportsSpecialIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecial> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/sportsSpecial/batch_delete");
                resJson.setSuccess(sportsSpecialService.myFakeBatchDelete(sportsSpecialIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改sportsSpecial
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改sportsSpecial")
    @ResponseBody
    public JsonResult<SportsSpecial> sportsSpecialCreateUpdate(@ApiParam(name = "sportsSpecial", value = "SportsSpecial实体类") @RequestBody SportsSpecial sportsSpecial){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<SportsSpecial> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/sportsSpecial/create_update");
                sportsSpecial = sportsSpecialService.mySportsSpecialCreateUpdate(sportsSpecial);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(sportsSpecial);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
