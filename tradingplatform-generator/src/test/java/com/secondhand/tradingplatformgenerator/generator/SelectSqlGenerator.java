package com.secondhand.tradingplatformgenerator.generator;

import com.alibaba.druid.pool.DruidDataSource;
import com.secondhand.tradingplatformgenerator.select.Select;
import com.secondhand.tradingplatformgenerator.select.SelectEnum;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class SelectSqlGenerator {

    /**
     * 初始化DruidDataSource
     * 由于多模块扫描的问题，暂时先用这种方法初始化
     */
    private DruidDataSource dataSource;

    {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/myshtp?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testSelectSqlGenerator(){

        Select select = new Select("s_base_select_item");

        //准备承载selectSql
        StringBuilder selectSql = new StringBuilder();

        //获取tableName
        String tableName = select.getTableName();

        //获取表的别名
        StringBuilder tableAlias = select.getTableAlias();

        //select的头部
        selectSql.append("select ");

        /**
         * 使用字典表查询该表字段
         */
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList("SELECT COLUMN_NAME AS COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'myshtp' AND TABLE_NAME = '" + tableName + "' ORDER BY IS_NULLABLE ASC");

        /**
         * 拼接简单形式的sql（参数部分）
         * 即sbu.deleted as deleted,  ...
         * 要拼接成一行，为了放到Mybatis-Plus时的Dao层的注释美观
         * 剔除公共的不常用字段
         */
        String tempParameter = null;
        for (Map<String, Object> tempMap : resultList){

            //分别获取字段名
            tempParameter = (String) tempMap.get("COLUMN_NAME");

            //把公共字段剔除
            if (tempParameter.equals("uuid")){
                break;
            }


//====================================================================================================================
            //特殊关联字段的拼接
            if (tempParameter.equals("item_value")){
                select.setSelectSql(selectSql);
                selectSql = concatSql(select, tempParameter, SelectEnum.S_BASE_SELECT_ITEM);
                continue;
            }
//====================================================================================================================

            //样式： tableAlias.parameter as parameter，

            //加别名
            selectSql.append(tableAlias);
            selectSql.append(".");
            //加字段
            selectSql.append(tempParameter);
            selectSql.append(" as ");
            selectSql.append(tempParameter);
            selectSql.append(", ");
        }

        //再把常用的deleted和created_at放进去，趁机把最后那个逗号人为弄走
        //tableAlias.parameter as parameter， tableAlias.parameter as parameter，
        selectSql.append(tableAlias);
        selectSql.append(".deleted as deleted, ");
        selectSql.append(tableAlias);
        selectSql.append(".created_at as created_at ");

        //select的尾部
        selectSql.append("from ");
        selectSql.append(tableName);
        selectSql.append(" ");
        selectSql.append(tableAlias);

        System.out.println(selectSql.toString());
        System.out.println("-----------------------------------------------");
    }

    public StringBuilder concatSql(Select select, String parameter, SelectEnum selectEnum){

        //获取模板的字段名
        String fieldName = selectEnum.getFieldName();

        //获取模板的sql
        String enumSql = selectEnum.getSelectSql();

        //获取selectSql继续拼接
        StringBuilder selectSql = select.getSelectSql();

        //获取表的别名
        StringBuilder tableAlias = select.getTableAlias();

        //原字段与默认字段相同时，直接替换别名
        if (fieldName.equals(parameter)){
            selectSql.append(enumSql.replaceAll("tableAlias", tableAlias.toString()));
            //原字段与默认字段不相同时，把倒数两个字段名也要替换掉
        } else {
            //先替换别名
            enumSql = enumSql.replaceAll("tableAlias", tableAlias.toString());
            //再替换参数名
            enumSql = enumSql.replaceAll(fieldName, parameter);

            //怼到selectSql里面
            selectSql.append(enumSql);
        }

        return selectSql;
    }
}