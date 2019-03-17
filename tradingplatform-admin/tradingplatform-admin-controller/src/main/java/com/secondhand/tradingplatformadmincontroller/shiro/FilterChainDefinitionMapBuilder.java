package com.secondhand.tradingplatformadmincontroller.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义shiro路由过滤器
 *
 * @author 81079
 */
public class FilterChainDefinitionMapBuilder {

    public static LinkedHashMap<String, String> addFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {

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

        //图书专库
        filterChainDefinitionMap.put("/front/bookLibrary/query", "anon");
        filterChainDefinitionMap.put("/front/bookLibrary/get_map_by_id/*", "anon");

        //运动专场
        filterChainDefinitionMap.put("/front/sportsSpecial/query", "anon");
        filterChainDefinitionMap.put("/front/sportsSpecial/get_map_by_id/*", "anon");

        //数码广场
        filterChainDefinitionMap.put("/front/digitalSquare/query", "anon");
        filterChainDefinitionMap.put("/front/digitalSquare/get_map_by_id/*", "anon");

        //租房专区
        filterChainDefinitionMap.put("/front/rentingHouse/query", "anon");
        filterChainDefinitionMap.put("/front/rentingHouse/get_map_by_id/*", "anon");

        //其他类别
        filterChainDefinitionMap.put("/front/otherCategories/query", "anon");
        filterChainDefinitionMap.put("/front/otherCategories/get_map_by_id/*", "anon");

        return (LinkedHashMap<String, String>) filterChainDefinitionMap;
    }
}
