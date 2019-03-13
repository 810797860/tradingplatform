package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.MenuButtonService;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.UserService;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;

import javax.servlet.http.HttpSession;

/**
 * @description : User 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-13
 */
@Controller("adminUserController")
@Api(value="/admin/user", description="User 控制器")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private MenuButtonService menuButtonService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到user的列表页面")
    public String toUserList(@ApiParam(name = "model", value = "Model") Model model,
                             @ApiParam(name = "menuId", value = "菜单id") Long menuId,
                             @ApiParam(name = "session", value = "客户端会话") HttpSession session) {

        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        //根据菜单id找按钮
        List<Button> buttons = menuButtonService.mySelectListWithMenuId(menuId, roleId);
        //注入该表单的按钮
        model.addAttribute("buttons", buttons);
        return "system/user/tabulation";
    }

    /**
     * @description : 跳转到修改或新增user的页面
     * @author : zhangjk
     * @since : Create in 2018-11-11
     */
    @GetMapping(value = {"/{userId}/update.html", "/create.html"})
    @ApiOperation(value = "/{userId}/update.html、/create.html", notes = "跳转到修改或新增页面")
    public String toModifyUser(@ApiParam(name = "model", value = "Model") Model model,
                               @ApiParam(name = "userId", value = "UserId") @PathVariable(value = "userId", required = false) Long userId) {

        Map<String, Object> user = new HashMap<>();
        //判空
        if (userId != null) {
            //根据userId查找记录回显的数据
            user = userService.mySelectMapById(userId);
        }
        //静态注入
        model.addAttribute("user", user);
        return "system/user/modify";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<User> getUserList(@ApiParam(name = "User", value = "User 实体类") @RequestBody User user) {

            //查后台用户，加上type
            user.setType(SystemSelectItem.USER_TYPE_BACK_DESK);
            TableJson<User> resJson = new TableJson<>();
            Page resPage = user.getPage();
            user.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<User> userPage = new Page<User>(current, size);
            userPage = userService.mySelectPageWithParam(userPage, user);
            resJson.setRecordsTotal(userPage.getTotal());
            resJson.setData(userPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取userMap
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/get_map_by_id/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{userId}", notes = "根据id获取userMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getUserByIdForMap( @ApiParam(name = "id", value = "userId") @PathVariable("userId") Long userId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> user = userService.mySelectMapById(userId);
            resJson.setData(user);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除user
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除user")
    @ResponseBody
    public JsonResult<User> fakeDeleteById(@ApiParam(name = "id", value = "userId") @RequestBody Long userId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<User> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/user/delete");
                userService.myFakeDeleteById(userId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除user
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除user")
    @ResponseBody
    public JsonResult<User> fakeBatchDelete(@ApiParam(name = "ids", value = "userIds") @RequestBody List<Long> userIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<User> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/user/batch_delete");
                resJson.setSuccess(userService.myFakeBatchDelete(userIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改user
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改user")
    @ResponseBody
    public JsonResult<User> userCreateUpdate(@ApiParam(name = "User", value = "User实体类") @RequestBody User user){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<User> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/user/create_update");
                user = userService.myUserCreateUpdate(user);
                resJson.setData(user);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            } catch (CustomizeException e) {
                e.printStackTrace();
            }
        return resJson;
    }
}
