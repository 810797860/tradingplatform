package com.secondhand.tradingplatformcommon.util;

import com.secondhand.tradingplatformcommon.pojo.MagicalValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.IOException;
import java.util.Map;

/**
 * 高频方法集合类
 *
 * @author 81079
 */

public class ToolUtil {

    /**
     * String为空
     *
     * @param str
     * @return
     */
    public static boolean strIsEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * Object为空
     *
     * @param obj
     * @return
     */
    public static boolean objIsEmpty(Object obj) {

        if (obj == null) {
            return true;
        } else if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else {
            return obj instanceof Map ? ((Map) obj).isEmpty() : false;
        }
    }

    /**
     * 获取异常的具体信息
     *
     * @param e
     * @return
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

    /**
     * 实体对象转成Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map
     * @param clazz
     * @return
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 获取uuid->给新增数据用
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取当天时分秒
     *
     * @return
     */
    public static String getMinutesAndSeconds() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取字符串形式的年月日时分秒
     */
    public static String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 爬虫返回值/空串
     */
    public static String getClawer(String str) {
        return str == null ? "" : str;
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param frontCaptcha
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request, String frontCaptcha) {

        //获取生成的验证码
        HttpSession session = request.getSession();
        String captcha = session.getAttribute(MagicalValue.STRING_OF_KAPTCHA).toString();
        //判断
        if (strIsEmpty(frontCaptcha) || !captcha.equals(frontCaptcha)) {
            return false;
        }
        return true;
    }

    /**
     * Map某参数判空
     *
     * @param parameter
     * @param parameterName
     * @return
     */
    public static boolean mapJudgeParameterEmpty(Map<String, Object> parameter, String parameterName) {

        if (parameter.containsKey(parameterName) && !objIsEmpty(parameter.get(parameterName))) {
            return true;
        }
        return false;
    }

}
