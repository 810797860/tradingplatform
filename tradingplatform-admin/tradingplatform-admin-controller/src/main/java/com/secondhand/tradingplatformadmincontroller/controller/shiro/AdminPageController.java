package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleMenuService;
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : 后台重要页面入口控制器
 * @author : zhangjk
 * @since : Create in 2018-12-04
 */
@Controller
@Api(value = "/admin", description = "后台重要页面入口控制器")
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * @description : 跳转到登录页面
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/login")
    @ApiOperation(value = "/login", notes = "跳转到登录页面")
    public String login(Model model) {
        return "login";
    }

    /**
     * @description : 跳转到后台首页
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/index")
    @ApiOperation(value = "/index", notes = "跳转到后台首页")
    public String index(@ApiParam(name = "session", value = "客户端会话") HttpSession session,
                        @ApiParam(name = "model", value = "Model") Model model) {
        User user = (User) session.getAttribute(MagicalValue.USER_SESSION);
        //查找该用户的角色菜单列表
        //先找改用户的角色id
        Long roleId = Long.valueOf(session.getAttribute(MagicalValue.ROLE_SESSION_ID).toString());
        List<Menu> roleMenus = roleMenuService.mySelectListWithRoleId(roleId);
        //前端没判空后台来判咯
        if (roleMenus == null){
            roleMenus = new ArrayList<>();
        }
        //注入后台用户信息
        model.addAttribute("user", user);
        //注入该用户的菜单
        model.addAttribute("roleMenus", roleMenus);
        return "index";
    }

    /**
     * @description : 这是吴丑银测试接口
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/wu")
    @ApiOperation(value = "/wu", notes="这是吴丑银测试接口")
    @ResponseBody
    public TableJson<User> getUserList() {
        User user = new User();
        TableJson<User> resJson = new TableJson<>();
        user.setDeleted(false);
        Integer current = 1;
        Integer size = 10;
        Page<User> userPage = new Page<User>(current, size);
        userPage = userService.mySelectPageWithParam(userPage, user);
        resJson.setRecordsTotal(userPage.getTotal());
        resJson.setData(userPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }
}
