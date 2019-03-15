package com.secondhand.tradingplatformadmincontroller.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义shiro路由过滤器
 * @author 81079
 */
public class FilterChainDefinitionMapBuilder {

    public static LinkedHashMap<String, String> addFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap){

        //验证码
        filterChainDefinitionMap.put("/admin/kaptcha/default", "anon");

        //图片加载
        filterChainDefinitionMap.put("/admin/annex/image/*", "anon");

        //前端枚举
        filterChainDefinitionMap.put("/front/frontSelectItem/query", "anon");
        filterChainDefinitionMap.put("/front/frontSelectItem/get_map_by_id/*", "anon");
        //电器商城
        filterChainDefinitionMap.put("/front/electricAppliance/query", "anon");
        filterChainDefinitionMap.put("/front/electricAppliance/get_map_by_id/*", "anon");
        return (LinkedHashMap<String, String>) filterChainDefinitionMap;
    }
}
