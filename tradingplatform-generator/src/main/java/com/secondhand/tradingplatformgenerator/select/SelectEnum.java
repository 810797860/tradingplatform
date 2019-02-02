package com.secondhand.tradingplatformgenerator.select;

/**
 * @author 81079
 */

public enum SelectEnum {

    //C_BUSINESS_MATURE_CASE为例子
    C_BUSINESS_MATURE_CASE("case_id", "(select concat('{\"id\":\"', cbmc.id, '\",\"case_name\":\"', cbmc.title, '\"}') from c_business_mature_case cbmc where (cbmc.id = tableAlias.case_id)) AS case_id, "),

    //S_BASE_SELECT_ITEM为例子
    S_BASE_SELECT_ITEM("item_id", "(select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = tableAlias.item_id)) AS item_id, "),

    //S_BASE_SELECT_ITEM的GROUP_CONCAT为例子
    S_BASE_SELECT_ITEM_GROUP("item_ids", "(SELECT concat( '[', group_concat( concat( '{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}' ) SEPARATOR ',' ), ']' ) FROM s_base_select_item sbsi WHERE find_in_set( sbsi.id, tableAlias.item_ids ) ) AS item_ids, ");

    private String fieldName;
    private String selectSql;

    SelectEnum(String fieldName, String selectSql) {
        this.fieldName = fieldName;
        this.selectSql = selectSql;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }
}
