package com.secondhand.tradingplatformgenerator.select;

import com.secondhand.tradingplatformcommon.pojo.MagicalValue;

/**
 * @author 81079
 */

public class Select {

    private StringBuilder selectSql;
    private String tableName;
    private StringBuilder tableAlias;


    public Select(String tableName) {
        this.tableName = tableName;
        if (tableName != null){

//            //初始化，不然的话会空指针报错
//            tableAlias = new StringBuilder();
//
//            //类似植树，先把记录第一个，然后'_'后一个char下来
//            tableAlias.append(tableName.charAt(0));
//
//            //-1时表示没有'_'
//            for (int tableAliasIndex = tableName.indexOf(MagicalValue.UNDERLINE); tableAliasIndex > 0; tableAliasIndex = tableName.indexOf(MagicalValue.UNDERLINE, tableAliasIndex + 1)){
//                tableAlias.append(tableName.charAt(tableAliasIndex + 1));
//            }
            tableAlias = new StringBuilder(tableName);
        }
    }

    public StringBuilder getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(StringBuilder selectSql) {
        this.selectSql = selectSql;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public StringBuilder getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(StringBuilder tableAlias) {
        this.tableAlias = tableAlias;
    }
}
