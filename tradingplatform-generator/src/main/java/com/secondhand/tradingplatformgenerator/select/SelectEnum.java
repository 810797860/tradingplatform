package com.secondhand.tradingplatformgenerator.select;

/**
 * @author 81079
 */

public enum SelectEnum {

//    C_BUSINESS_MATURE_CASE为例子
//    C_BUSINESS_MATURE_CASE("case_id", "(select concat('{\"id\":\"', cbmc.id, '\",\"case_name\":\"', cbmc.title, '\"}') from c_business_mature_case cbmc where (cbmc.id = tableAlias.case_id)) AS case_id, ");

    S_BASE_SELECT_ITEM("item_ids", "(SELECT concat( '[', group_concat( concat( '{\"id\":\"', bsi.id, '\",\"pid\":\"', bsi.pid, '\",\"title\":\"', bsi.title, '\"}' ) SEPARATOR ',' ), ']' ) FROM s_base_select_item bsi WHERE find_in_set( bsi.id, sbsi.item_ids ) ) AS item_ids, ");

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
