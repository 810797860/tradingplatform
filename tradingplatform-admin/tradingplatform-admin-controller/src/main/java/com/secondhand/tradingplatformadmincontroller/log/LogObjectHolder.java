package com.secondhand.tradingplatformadmincontroller.log;

import com.secondhand.tradingplatformcommon.util.ApplicationContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * 被修改的bean临时存放的地方
 *
 * @author 81079
 */

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class LogObjectHolder implements Serializable {

    private Object object = null;

    public void set(Object obj) {
        this.object = obj;
    }

    public Object get() {
        return object;
    }

    public static LogObjectHolder me() {
        LogObjectHolder bean = ApplicationContextHolder.getBean(LogObjectHolder.class);
        return bean;
    }
}
