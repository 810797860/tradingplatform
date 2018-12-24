package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.secondhand.tradingplatformadmincontroller.shiro.DesUserToken;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : Shiro 测试控制器
 * @author : zhangjk
 * @since : Create in 2018-12-04
 */
@Controller
public class TestShiroController {

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
     * @description : 用户登录
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "/login", notes = "用户登录")
    public String login(HttpServletRequest request, User user){
        if (ToolUtil.strIsEmpty(user.getUserName()) || ToolUtil.strIsEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        DesUserToken token = new DesUserToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
            return "redirect:usersPage";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    /**
     * @description : 跳转到用户列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @RequestMapping(value={"/usersPage",""})
    @ApiOperation(value = "/usersPage", notes = "跳转到登录页面")
    public String usersPage(){
        return "user/users";
    }

    /**
     * @description : 跳转到角色列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @RequestMapping("/rolesPage")
    @ApiOperation(value = "/rolesPage", notes = "跳转到角色列表")
    public String rolesPage(){
        return "role/roles";
    }

    /**
     * @description : 跳转到资源列表
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @RequestMapping("/resourcesPage")
    @ApiOperation(value = "/resourcesPage", notes = "跳转到资源列表")
    public String resourcesPage(){
        return "resources/resources";
    }

    /**
     * @description : 跳转到资源不可用页面
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @RequestMapping("/403")
    @ApiOperation(value = "/403", notes = "跳转到资源不可用页面")
    public String forbidden(){
        return "403";
    }
}
