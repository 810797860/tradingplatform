package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;
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

/**
 * @description : User 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-13
 */
@RestController
@Api(value="/admin/user", description="User 控制器")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到user的列表页面")
    public String toUserList(@ApiParam(name = "model", value = "Model") Model model) {
        return "user/tabulation";
    }

    /**
     * @description : 跳转到修改user的页面
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/{userId}/update.html")
    @ApiOperation(value = "/{userId}/update.html", notes = "跳转到修改页面")
    public String toUpdateUser(@ApiParam(name = "model", value = "Model") Model model, @PathVariable(value = "userId") Long userId) {
        //静态注入要回显的数据
        Map<String, Object> user = userService.mySelectMapById(userId);
        model.addAttribute("user", user);
        return "user/newUser";
    }

    /**
     * @description : 跳转到新增user的页面
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateUser(@ApiParam(name = "model", value = "Model") Model model) {
        return "user/newUser";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<User> getUserList(@ApiParam(name = "User", value = "User 实体类") @RequestBody User user) {
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
            }
            return resJson;
    }
}
