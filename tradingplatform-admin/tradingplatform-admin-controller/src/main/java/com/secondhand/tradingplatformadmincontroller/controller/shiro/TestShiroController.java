package com.secondhand.tradingplatformadmincontroller.controller.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadmincontroller.shiro.DesUserToken;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description : Shiro 测试控制器
 * @author : zhangjk
 * @since : Create in 2018-12-04
 */
@Controller
public class TestShiroController {

    @Autowired
    private UserService userService;

    /**
     * @description : 跳转到登录页面
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @GetMapping(value = "/login")
    @ApiOperation(value = "/login", notes = "跳转到登录页面")
    public String login(Model model) {
        return "index";
    }

    /**
     * @description : 用户登录
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "/login", notes = "用户登录")
    public String login(HttpServletRequest request, User user, HttpSession session){
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
        } finally {
            //进行判断，是否为后台用户
            Long type = Long.valueOf(session.getAttribute(MagicalValue.USER_TYPE).toString());
            if (!type.equals(SystemSelectItem.USER_TYPE_BACK_DESK)){
                //跳回登录页面
                //前端调用/logout退出
                return "login";
            }
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

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-11-13
     */
    @GetMapping(value = "/wu")
    @ApiOperation(value = "/wu", notes="获取分页列表")
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
