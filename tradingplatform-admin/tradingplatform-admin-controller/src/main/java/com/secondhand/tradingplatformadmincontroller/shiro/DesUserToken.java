package com.secondhand.tradingplatformadmincontroller.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 81079
 */

public class DesUserToken extends UsernamePasswordToken {

    private Object principal;
    private Object credentials;

    public DesUserToken() {
    }

    public DesUserToken(String userName, String password) {
        this.principal = userName;
        this.credentials = password;
        this.setUsername(userName);
        this.setPassword(password.toCharArray());
    }

    @Override
    public Object getPrincipal() {
        //账号信息
        //返回用户名
        return principal;
    }

    @Override
    public Object getCredentials() {
        //校验的信息,其实一般就是指密码
        return DesEncryptionTool.encrypt(credentials.toString());
    }
}
