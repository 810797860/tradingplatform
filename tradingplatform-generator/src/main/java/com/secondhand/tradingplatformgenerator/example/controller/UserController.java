package com.secondhand.tradingplatformgenerator.example.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformgenerator.example.entity.User;
import com.secondhand.tradingplatformgenerator.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

@RestController
@Api(value = "/admin/user", description = "User控制器")
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    public UserService userService;

    /**
     * 获取分页列表
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    public TableJson<User> getUserList(@ApiParam(name = "User", value = "User实体类") @RequestBody User user) {
        TableJson<User> resJson = new TableJson<>();
        Page resPage = user.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<User> userPage = new Page<>(current, size);
        userPage = userService.selectPageWithParam(userPage, user);
        resJson.setRecordsTotal(userPage.getTotal());
        resJson.setData(userPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * 根据id获取user
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/get_by_id/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_by_id/{userId}", notes = "根据id获取user")
    public JsonResult<User> getUserById(@ApiParam(name = "id", value = "userId") @PathVariable("userId") Long userId) {
        JsonResult<User> resJson = new JsonResult<>();
        User user = userService.selectById(userId);
        resJson.setData(user);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * 根据id获取userMap
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/get_map_by_id/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{userId}", notes = "根据id获取userMap")
    public JsonResult<Map<String, Object>> getUserByIdForMap(@ApiParam(name = "id", value = "userId") @PathVariable("userId") Long userId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> user = userService.selectMapById(userId);
        resJson.setData(user);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * 根据id假删除user
     *
     * @param userId
     * @return
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除user")
    public JsonResult<User> fakeDeleteById(@ApiParam(name = "id", value = "userId") @RequestBody Long userId) {
        JsonResult<User> resJson = new JsonResult<>();
        resJson.setSuccess(userService.fakeDeleteById(userId));
        return resJson;
    }

    /**
     * 根据ids批量假删除user
     *
     * @param userIds
     * @return
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除user")
    public JsonResult<User> fakeBatchDelete(@ApiParam(name = "ids", value = "userIds") @RequestBody List<Long> userIds) {
        JsonResult<User> resJson = new JsonResult<>();
        resJson.setSuccess(userService.fakeBatchDelete(userIds));
        return resJson;
    }

    /**
     * 新增或修改user
     *
     * @param user
     * @return
     */
    @PutMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改user")
    public JsonResult<User> userCreateUpdate(@ApiParam(name = "User", value = "User实体类") @RequestBody User user) {
        JsonResult<User> resJson = new JsonResult<>();
        resJson.setData(user);
        resJson.setSuccess(true);
        return resJson;
    }
}
