package com.secondhand.tradingplatformadmincontroller.controller.admin.shiro;

import com.secondhand.tradingplatformadmincontroller.shiro.DesUserToken;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.CustomizeStatus;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : 登录控制器
 * @since : Create in 2018-12-04
 */
@RestController("adminLogInController")
@Api(value = "/admin", description = "登录控制器")
@RequestMapping("/admin")
public class LogInController {

    /**
     * @description : 用户登录
     * @author : zhangjk
     * @since : Create in 2018-12-04
     */
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/login", notes = "用户登录")
    public JsonResult login(@ApiParam(name = "request", value = "服务器请求") HttpServletRequest request,
                            @ApiParam(name = "parameter", value = "用户实体类") @RequestBody Map<String, Object> parameter,
                            @ApiParam(name = "session", value = "客户端会话") HttpSession session) throws CustomizeException {

        //获取账号、密码、验证码
        String userName = parameter.get("userName").toString();
        String password = parameter.get("password").toString();
        String captcha = parameter.get("captcha").toString();
        //判断验证码
        if (!ToolUtil.checkVerifyCode(request, captcha)) {
            throw new CustomizeException(CustomizeStatus.LOGIN_VERIFICATION_CODE_ERROR, this.getClass());
        }
        Subject subject = SecurityUtils.getSubject();
        DesUserToken token = new DesUserToken(userName, password);
        try {
            subject.login(token);
            //返回登录成功的结果
            JsonResult successJsonResult = new JsonResult();
            successJsonResult.setCode(MagicalValue.CODE_OF_SUCCESS);
            successJsonResult.setMessage("登录成功");
            successJsonResult.setSuccess(true);
            return successJsonResult;
        } catch (LockedAccountException lae) {
            token.clear();
            throw new CustomizeException(CustomizeStatus.LOGIN_USER_IS_LOCKED, this.getClass());
        } catch (AuthenticationException e) {
            token.clear();
            throw new CustomizeException(CustomizeStatus.LOGIN_WRONG_PASSWORD, this.getClass());
        } finally {
            Long type = null;
            //进行判断，是否为后台用户
            if (session.getAttribute(MagicalValue.USER_TYPE) != null){
                type = Long.valueOf(session.getAttribute(MagicalValue.USER_TYPE).toString());
            }
            if (!type.equals(SystemSelectItem.USER_TYPE_BACK_DESK)) {
                //跳回登录页面
                //前端调用/logout退出
                throw new CustomizeException(CustomizeStatus.LOGIN_LOG_IN_ERROR, this.getClass());
            }
        }
    }
}
