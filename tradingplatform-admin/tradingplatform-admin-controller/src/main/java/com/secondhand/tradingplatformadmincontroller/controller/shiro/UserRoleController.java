package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
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
import com.secondhand.tradingplatformadminentity.entity.shiro.UserRole;
import com.secondhand.tradingplatformadminservice.service.shiro.UserRoleService;

/**
 * @description : UserRole 控制器
 * @author : zhangjk
 * @since : Create in 2018-11-22
 */
@RestController
@Api(value="/admin/userRole", description="UserRole 控制器")
@RequestMapping("/admin/userRole")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到userRole的列表页面")
    public String toUserRoleList(Model model) {
        return "userRole/tabulation";
    }

    /**
     * @description : 跳转到修改userRole的页面
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @GetMapping(value = "/{userRoleId}/update.html")
    @ApiOperation(value = "/{userRoleId}/update.html", notes = "跳转到修改页面")
    public String toUpdateUserRole(Model model, @PathVariable(value = "userRoleId") Long userRoleId) {
        //静态注入要回显的数据
        Map<String, Object> userRole = userRoleService.mySelectMapById(userRoleId);
        model.addAttribute("userRole", userRole);
        return "userRole/newUserRole";
    }

    /**
     * @description : 跳转到新增userRole的页面
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateUserRole(Model model) {
        return "userRole/newUserRole";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PostMapping(value = "/query", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<UserRole> getUserRoleList(@ApiParam(name = "UserRole", value = "UserRole 实体类") @RequestBody UserRole userRole) {
            TableJson<UserRole> resJson = new TableJson<>();
            Page resPage = userRole.getPage();
            userRole.setDeleted(false);
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<UserRole> userRolePage = new Page<UserRole>(current, size);
            userRolePage = userRoleService.mySelectPageWithParam(userRolePage, userRole);
            resJson.setRecordsTotal(userRolePage.getTotal());
            resJson.setData(userRolePage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取userRoleMap
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @GetMapping(value = "/get_map_by_id/{userRoleId}", produces = {"application/json"})
    @ApiOperation(value = "/get_map_by_id/{userRoleId}", notes = "根据id获取userRoleMap")
    public JsonResult<Map<String, Object>> getUserRoleByIdForMap( @ApiParam(name = "id", value = "userRoleId") @PathVariable("userRoleId") Long userRoleId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> userRole = userRoleService.mySelectMapById(userRoleId);
            resJson.setData(userRole);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除userRole
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PutMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/delete", notes = "根据id假删除userRole")
    public JsonResult<UserRole> fakeDeleteById(@ApiParam(name = "id", value = "userRoleId") @RequestBody Long userRoleId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<UserRole> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/userRole/delete");
                userRoleService.myFakeDeleteById(userRoleId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除userRole
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PutMapping(value = "/batch_delete", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除userRole")
    public JsonResult<UserRole> fakeBatchDelete(@ApiParam(name = "ids", value = "userRoleIds") @RequestBody List<Long> userRoleIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<UserRole> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/userRole/batch_delete");
                resJson.setSuccess(userRoleService.myFakeBatchDelete(userRoleIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改userRole
     * @author : zhangjk
     * @since : Create in 2018-11-22
     */
    @PostMapping(value = "/create_update", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(value = "/create_update", notes = "新增或修改userRole")
    public JsonResult<UserRole> userRoleCreateUpdate(@ApiParam(name = "UserRole", value = "UserRole实体类") @RequestBody UserRole userRole){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<UserRole> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/userRole/create_update");
                userRole = userRoleService.myUserRoleCreateUpdate(userRole);
                resJson.setData(userRole);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
