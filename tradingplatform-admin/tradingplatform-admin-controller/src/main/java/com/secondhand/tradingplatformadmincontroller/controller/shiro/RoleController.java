package com.secondhand.tradingplatformadmincontroller.controller.shiro;

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
import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleService;

/**
 * @description : Role 控制器
 * @author : zhangjk
 * @since : Create in 2018-10-21
 */
@RestController
@Api(value="/admin/role", description="Role 控制器")
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Role> getRoleList(@ApiParam(name = "Role", value = "Role 实体类") @RequestBody Role role) {
            TableJson<Role> resJson = new TableJson<>();
            Page resPage = role.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Role> rolePage = new Page<Role>(current, size);
            rolePage = roleService.selectPageWithParam(rolePage, role);
            resJson.setRecordsTotal(rolePage.getTotal());
            resJson.setData(rolePage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取role
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @GetMapping(value = "/get_by_id/{roleId}", produces = {"application/json"})
    @ApiOperation(value = "/get_by_id/{roleId}", notes = "根据id获取role")
    public JsonResult<Role> getRoleById( @ApiParam(name = "id",value = "roleId") @PathVariable("roleId") Long roleId) {
            JsonResult<Role> resJson = new JsonResult<>();
            Role role = roleService.selectOneByObj(roleId);
            resJson.setData(role);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取roleMap
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @GetMapping(value = "/get_map_by_id/{roleId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{roleId}", notes = "根据id获取roleMap")
    public JsonResult<Map<String, Object>> getRoleByIdForMap(@ApiParam(name = "id", value = "roleId") @PathVariable("roleId") Long roleId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> role = roleService.selectMapById(roleId);
            resJson.setData(role);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除role
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除role")
    public JsonResult<Role> fakeDeleteById(@ApiParam(name = "id", value = "roleId") @RequestBody Long roleId){
            JsonResult<Role> resJson = new JsonResult<>();
            roleService.fakeDeleteById(roleId);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除role
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除role")
    public JsonResult<Role> fakeBatchDelete(@ApiParam(name = "ids", value = "roleIds") @RequestBody List<Long> roleIds){
            JsonResult<Role> resJson = new JsonResult<>();
            resJson.setSuccess(roleService.fakeBatchDelete(roleIds));
            return resJson;
    }

    /**
     * @description : 新增或修改role
     * @author : zhangjk
     * @since : Create in 2018-10-21
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改role")
    public JsonResult<Role> roleCreateUpdate(@ApiParam(name = "Role", value = "Role实体类") @RequestBody Role role){
            role = roleService.roleCreateUpdate(role);
            JsonResult<Role> resJson = new JsonResult<>();
            resJson.setData(role);
            resJson.setSuccess(true);
            return resJson;
    }
}
