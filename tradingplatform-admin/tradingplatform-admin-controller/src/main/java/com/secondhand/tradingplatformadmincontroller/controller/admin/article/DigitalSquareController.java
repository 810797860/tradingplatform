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
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquare;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareService;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @description : DigitalSquare 控制器
 * @author : zhangjk
 * @since : Create in 2019-05-02
 */
@Controller("adminDigitalSquareController")
@Api(value="/admin/digitalSquare", description="DigitalSquare 控制器")
@RequestMapping("/admin/digitalSquare")
public class DigitalSquareController extends BaseController {

    @Autowired
    private DigitalSquareService digitalSquareService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到digitalSquare的列表页面")
    public String toDigitalSquareList(@ApiParam(name = "model", value = "Model") Model model,
                                    @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                    @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/digitalSquare/tabulation";
    }

    /**
     * @description : 跳转到修改或新增digitalSquare的页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = {"/{digitalSquareId}/update.html", "/create.html"})
    @ApiOperation(value = "/{digitalSquareId}/update.html、/create.html", notes = "跳转到修改或新增digitalSquare的页面")
    public String toModifyDigitalSquare(@ApiParam(name = "model", value = "Model") Model model,
    @ApiParam(name = "digitalSquareId", value = "DigitalSquareId") @PathVariable(value = "digitalSquareId", required = false) Long digitalSquareId) {
        Map<String, Object> digitalSquare = new HashMap<>();
        //判空
        if (digitalSquareId != null) {
            digitalSquare = digitalSquareService.mySelectMapById(digitalSquareId);
        }
        //静态注入
        //静态注入根据digitalSquareId查找记录回显的数据
        model.addAttribute("digitalSquare", digitalSquare);
        return "system/digitalSquare/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getDigitalSquareList(@ApiParam(name = "digitalSquare", value = "DigitalSquare 实体类") @RequestBody DigitalSquare digitalSquare) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = digitalSquare.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> digitalSquarePage = new Page(current, size);
            digitalSquarePage = digitalSquareService.mySelectPageWithParam(digitalSquarePage, digitalSquare);
            resJson.setRecordsTotal(digitalSquarePage.getTotal());
            resJson.setData(digitalSquarePage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取digitalSquareMap
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/get_map_by_id/{digitalSquareId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{digitalSquareId}", notes = "根据id获取digitalSquareMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getDigitalSquareByIdForMap( @ApiParam(name = "id", value = "digitalSquareId") @PathVariable("digitalSquareId") Long digitalSquareId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> digitalSquare = digitalSquareService.mySelectMapById(digitalSquareId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(digitalSquare);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> fakeDeleteById(@ApiParam(name = "id", value = "digitalSquareId") @RequestBody Long digitalSquareId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquare> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/digitalSquare/delete");
                digitalSquareService.myFakeDeleteById(digitalSquareId);
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
     * @description : 根据ids批量假删除digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> fakeBatchDelete(@ApiParam(name = "ids", value = "digitalSquareIds") @RequestBody List<Long> digitalSquareIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquare> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/digitalSquare/batch_delete");
                resJson.setSuccess(digitalSquareService.myFakeBatchDelete(digitalSquareIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量审核通过digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_pass", notes = "根据ids批量审核通过digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> examinationBatchPass(@ApiParam(name = "ids", value = "digitalSquareIds") @RequestBody List<Long> digitalSquareIds){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<DigitalSquare> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/digitalSquare/batch_pass");
            resJson.setSuccess(digitalSquareService.myExaminationBatchPass(digitalSquareIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 审核不通过digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/not_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/not_pass", notes = "审核不通过digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> examinationNotPass(@ApiParam(name = "digitalSquare", value = "DigitalSquare实体类") @RequestBody DigitalSquare digitalSquare){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<DigitalSquare> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/digitalSquare/not_pass");
            digitalSquare.setBackCheckStatus(SystemSelectItem.BACK_STATUS_AUDIT_NOT_PASSED);
            resJson.setSuccess(digitalSquareService.myUpdateById(digitalSquare));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改digitalSquare
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改digitalSquare")
    @ResponseBody
    public JsonResult<DigitalSquare> digitalSquareCreateUpdate(@ApiParam(name = "digitalSquare", value = "DigitalSquare实体类") @RequestBody DigitalSquare digitalSquare){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<DigitalSquare> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/digitalSquare/create_update");
                digitalSquare = digitalSquareService.myDigitalSquareCreateUpdate(digitalSquare);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(digitalSquare);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
