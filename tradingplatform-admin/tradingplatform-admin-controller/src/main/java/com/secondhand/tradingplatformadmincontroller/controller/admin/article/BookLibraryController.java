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
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibrary;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description : BookLibrary 控制器
 * @author : zhangjk
 * @since : Create in 2019-05-02
 */
@Controller("adminBookLibraryController")
@Api(value="/admin/bookLibrary", description="BookLibrary 控制器")
@RequestMapping("/admin/bookLibrary")
public class BookLibraryController extends BaseController {

    @Autowired
    private BookLibraryService bookLibraryService;

    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到bookLibrary的列表页面")
    public String toBookLibraryList(@ApiParam(name = "model", value = "Model") Model model,
                                    @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                                    @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/bookLibrary/tabulation";
    }

    /**
     * @description : 跳转到修改或新增bookLibrary的页面
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = {"/{bookLibraryId}/update.html", "/create.html"})
    @ApiOperation(value = "/{bookLibraryId}/update.html、/create.html", notes = "跳转到修改或新增bookLibrary的页面")
    public String toModifyBookLibrary(@ApiParam(name = "model", value = "Model") Model model,
    @ApiParam(name = "bookLibraryId", value = "BookLibraryId") @PathVariable(value = "bookLibraryId", required = false) Long bookLibraryId) {
        Map<String, Object> bookLibrary = new HashMap<>();
        //判空
        if (bookLibraryId != null) {
            bookLibrary = bookLibraryService.mySelectMapById(bookLibraryId);
        }
        //静态注入
        //静态注入根据bookLibraryId查找记录回显的数据
        model.addAttribute("bookLibrary", bookLibrary);
        return "system/bookLibrary/modify";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getBookLibraryList(@ApiParam(name = "bookLibrary", value = "BookLibrary 实体类") @RequestBody BookLibrary bookLibrary) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = bookLibrary.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> bookLibraryPage = new Page(current, size);
            bookLibraryPage = bookLibraryService.mySelectPageWithParam(bookLibraryPage, bookLibrary);
            resJson.setRecordsTotal(bookLibraryPage.getTotal());
            resJson.setData(bookLibraryPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取bookLibraryMap
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @GetMapping(value = "/get_map_by_id/{bookLibraryId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{bookLibraryId}", notes = "根据id获取bookLibraryMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getBookLibraryByIdForMap( @ApiParam(name = "id", value = "bookLibraryId") @PathVariable("bookLibraryId") Long bookLibraryId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> bookLibrary = bookLibraryService.mySelectMapById(bookLibraryId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(bookLibrary);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> fakeDeleteById(@ApiParam(name = "id", value = "bookLibraryId") @RequestBody Long bookLibraryId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibrary> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/bookLibrary/delete");
                bookLibraryService.myFakeDeleteById(bookLibraryId);
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
     * @description : 根据ids批量假删除bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> fakeBatchDelete(@ApiParam(name = "ids", value = "bookLibraryIds") @RequestBody List<Long> bookLibraryIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibrary> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/bookLibrary/batch_delete");
                resJson.setSuccess(bookLibraryService.myFakeBatchDelete(bookLibraryIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量审核通过bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/batch_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_pass", notes = "根据ids批量审核通过bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> examinationBatchPass(@ApiParam(name = "ids", value = "bookLibraryIds") @RequestBody List<Long> bookLibraryIds){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<BookLibrary> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/bookLibrary/batch_pass");
            resJson.setSuccess(bookLibraryService.myExaminationBatchPass(bookLibraryIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 审核不通过bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PutMapping(value = "/not_pass", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/not_pass", notes = "审核不通过bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> examinationNotPass(@ApiParam(name = "bookLibrary", value = "BookLibrary实体类") @RequestBody BookLibrary bookLibrary){
        Subject subject = SecurityUtils.getSubject();
        JsonResult<BookLibrary> resJson = new JsonResult<>();
        try{
            //检查是否具有权限
            subject.checkPermission("/admin/bookLibrary/not_pass");
            bookLibrary.setBackCheckStatus(SystemSelectItem.BACK_STATUS_AUDIT_NOT_PASSED);
            resJson.setSuccess(bookLibraryService.myUpdateById(bookLibrary));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        }catch(UnauthorizedException e){
            resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改bookLibrary
     * @author : zhangjk
     * @since : Create in 2019-05-02
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改bookLibrary")
    @ResponseBody
    public JsonResult<BookLibrary> bookLibraryCreateUpdate(@ApiParam(name = "bookLibrary", value = "BookLibrary实体类") @RequestBody BookLibrary bookLibrary){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibrary> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/bookLibrary/create_update");
                bookLibrary = bookLibraryService.myBookLibraryCreateUpdate(bookLibrary);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(bookLibrary);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
