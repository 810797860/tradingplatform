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

        //Swagger2入口
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");

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
        //咨询
        filterChainDefinitionMap.put("/front/electricApplianceAdvisory/query", "anon");
        filterChainDefinitionMap.put("/front/electricApplianceAdvisory/get_map_by_id/*", "anon");
        //评价
        filterChainDefinitionMap.put("/front/electricApplianceEvaluation/query", "anon");
        filterChainDefinitionMap.put("/front/electricApplianceEvaluation/get_map_by_id/*", "anon");

        //图书专库
        filterChainDefinitionMap.put("/front/bookLibrary/query", "anon");
        filterChainDefinitionMap.put("/front/bookLibrary/get_map_by_id/*", "anon");
        //咨询
        filterChainDefinitionMap.put("/front/bookLibraryAdvisory/query", "anon");
        filterChainDefinitionMap.put("/front/bookLibraryAdvisory/get_map_by_id/*", "anon");
        //评价
        filterChainDefinitionMap.put("/front/bookLibraryEvaluation/query", "anon");
        filterChainDefinitionMap.put("/front/bookLibraryEvaluation/get_map_by_id/*", "anon");

        //运动专场
        filterChainDefinitionMap.put("/front/sportsSpecial/query", "anon");
        filterChainDefinitionMap.put("/front/sportsSpecial/get_map_by_id/*", "anon");
        //咨询
        filterChainDefinitionMap.put("/front/sportsSpecialAdvisory/query", "anon");
        filterChainDefinitionMap.put("/front/sportsSpecialAdvisory/get_map_by_id/*", "anon");
        //评价
        filterChainDefinitionMap.put("/front/sportsSpecialEvaluation/query", "anon");
        filterChainDefinitionMap.put("/front/sportsSpecialEvaluation/get_map_by_id/*", "anon");
        
        //数码广场
        filterChainDefinitionMap.put("/front/digitalSquare/query", "anon");
        filterChainDefinitionMap.put("/front/digitalSquare/get_map_by_id/*", "anon");
        //咨询
        filterChainDefinitionMap.put("/front/digitalSquareAdvisory/query", "anon");
        filterChainDefinitionMap.put("/front/digitalSquareAdvisory/get_map_by_id/*", "anon");
        //评价
        filterChainDefinitionMap.put("/front/digitalSquareEvaluation/query", "anon");
        filterChainDefinitionMap.put("/front/digitalSquareEvaluation/get_map_by_id/*", "anon");

        //租房专区
        filterChainDefinitionMap.put("/front/rentingHouse/query", "anon");
        filterChainDefinitionMap.put("/front/rentingHouse/get_map_by_id/*", "anon");
        //咨询
        filterChainDefinitionMap.put("/front/rentingHouseAdvisory/query", "anon");
        filterChainDefinitionMap.put("/front/rentingHouseAdvisory/get_map_by_id/*", "anon");
        //评价
        filterChainDefinitionMap.put("/front/rentingHouseEvaluation/query", "anon");
        filterChainDefinitionMap.put("/front/rentingHouseEvaluation/get_map_by_id/*", "anon");
        
        //其他类别
        filterChainDefinitionMap.put("/front/otherCategories/query", "anon");
        filterChainDefinitionMap.put("/front/otherCategories/get_map_by_id/*", "anon");

        return (LinkedHashMap<String, String>) filterChainDefinitionMap;
    }
}
