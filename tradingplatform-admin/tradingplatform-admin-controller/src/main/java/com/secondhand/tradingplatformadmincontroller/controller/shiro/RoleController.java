package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleService;
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

/**
 * @description : Role 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-13
 */
@RestController
@Api(value="/admin/role", description="Role 控制器")
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到role的列表页面")
    public String toRoleList(@ApiParam(name = "Model", value = "model") Model model) {
        return "role/tabulation";
    }

    /**
     * @description : 跳转到修改role的页面
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/{roleId}/update.html")
    @ApiOperation(value = "/{roleId}/update.html", notes = "跳转到修改页面")
    public String toUpdateRole(@ApiParam(name = "Model", value = "model") Model model, @PathVariable(value = "roleId") Long roleId) {
        //静态注入要回显的数据
        Map<String, Object> role = roleService.mySelectMapById(roleId);
        model.addAttribute("role", role);
        return "role/newRole";
    }

    /**
     * @description : 跳转到新增role的页面
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateRole(@ApiParam(name = "Model", value = "model") Model model) {
        return "role/newRole";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Role> getRoleList(@ApiParam(name = "Role", value = "Role 实体类") @RequestBody Role role) {
            TableJson<Role> resJson = new TableJson<>();
            Page resPage = role.getPage();
            role.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Role> rolePage = new Page<Role>(current, size);
            rolePage = roleService.mySelectPageWithParam(rolePage, role);
            resJson.setRecordsTotal(rolePage.getTotal());
            resJson.setData(rolePage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取roleMap
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/get_map_by_id/{roleId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{roleId}", notes = "根据id获取roleMap")
    public JsonResult<Map<String, Object>> getRoleByIdForMap( @ApiParam(name = "id", value = "roleId") @PathVariable("roleId") Long roleId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> role = roleService.mySelectMapById(roleId);
            resJson.setData(role);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除role
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除role")
    public JsonResult<Role> fakeDeleteById(@ApiParam(name = "id", value = "roleId") @RequestBody Long roleId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Role> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/role/delete");
                roleService.myFakeDeleteById(roleId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除role
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除role")
    public JsonResult<Role> fakeBatchDelete(@ApiParam(name = "ids", value = "roleIds") @RequestBody List<Long> roleIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Role> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/role/batch_delete");
                resJson.setSuccess(roleService.myFakeBatchDelete(roleIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改role
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改role")
    public JsonResult<Role> roleCreateUpdate(@ApiParam(name = "Role", value = "Role实体类") @RequestBody Role role){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Role> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/role/create_update");
                role = roleService.myRoleCreateUpdate(role);
                resJson.setData(role);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
