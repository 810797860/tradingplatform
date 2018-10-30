package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
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
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;

/**
 * @description : User 控制器
 * @author : zhangjk
 * @since : Create in 2018-10-21
 */
@RestController
@Api(value="/admin/user", description="User 控制器")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<User> getUserList(@ApiParam(name = "User", value = "User 实体类") @RequestBody User user) {
            TableJson<User> resJson = new TableJson<>();
            Page resPage = user.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<User> userPage = new Page<User>(current, size);
            userPage = userService.selectPageWithParam(userPage, user);
            resJson.setRecordsTotal(userPage.getTotal());
            resJson.setData(userPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取user
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @GetMapping(value = "/get_by_id/{userId}", produces = {"application/json"})
    @ApiOperation(value = "/get_by_id/{userId}", notes = "根据id获取user")
    public JsonResult<User> getUserById( @ApiParam(name = "id",value = "userId") @PathVariable("userId") Long userId) {
            JsonResult<User> resJson = new JsonResult<>();
            User user = userService.selectOneByObj(userId);
            resJson.setData(user);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取userMap
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @GetMapping(value = "/get_map_by_id/{userId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{userId}", notes = "根据id获取userMap")
    public JsonResult<Map<String, Object>> getUserByIdForMap( @ApiParam(name = "id", value = "userId") @PathVariable("userId") Long userId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> user = userService.selectMapById(userId);
            resJson.setData(user);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除user
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除user")
    public JsonResult<User> fakeDeleteById(@ApiParam(name = "id", value = "userId") @RequestBody Long userId){
            JsonResult<User> resJson = new JsonResult<>();
            resJson.setSuccess(userService.fakeDeleteById(userId));
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除user
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除user")
    public JsonResult<User> fakeBatchDelete(@ApiParam(name = "ids", value = "userIds") @RequestBody List<Long> userIds){
            JsonResult<User> resJson = new JsonResult<>();
            resJson.setSuccess(userService.fakeBatchDelete(userIds));
            return resJson;
    }

    /**
     * @description : 新增或修改user
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改user")
    public JsonResult<User> userCreateUpdate(@ApiParam(name = "User", value = "User实体类") @RequestBody User user){
            user = userService.userCreateUpdate(user);
            JsonResult<User> resJson = new JsonResult<>();
            resJson.setData(user);
            resJson.setSuccess(true);
            return resJson;
    }
}
