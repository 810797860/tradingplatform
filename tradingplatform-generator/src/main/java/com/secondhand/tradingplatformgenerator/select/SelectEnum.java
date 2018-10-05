package com.secondhand.tradingplatformgenerator.select;

/**
 * @author 81079
 */

public enum SelectEnum {

    C_BUSINESS_MATURE_CASE("case_id", "(select concat('{\"id\":\"', cbmc.id, '\",\"case_name\":\"', cbmc.title, '\"}') from c_business_mature_case cbmc where (cbmc.id = tableAlias.case_id)) AS case_id, ");

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
