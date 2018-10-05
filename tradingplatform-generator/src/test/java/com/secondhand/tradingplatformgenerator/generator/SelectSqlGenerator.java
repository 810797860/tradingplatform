package com.secondhand.tradingplatformgenerator.generator;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class SelectSqlGenerator {

    private DruidDataSource dataSource;

    {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/myshtp?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testSelectSqlGenerator(){
        String name = "张三";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<String, Object> resultMap = jdbcTemplate.queryForMap("select * from s_base_user where name = '" + name + "'");
        System.out.println(resultMap);
    }
}
