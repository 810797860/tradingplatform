package com.secondhand.tradingplatformgenerator.select;

/**
 * @author 81079
 */

public enum SelectEnum {

    //C_BUSINESS_MATURE_CASE为例子
    C_BUSINESS_MATURE_CASE("case_id", "(select concat('{\"id\":\"', cbmc.id, '\",\"case_name\":\"', cbmc.title, '\"}') from c_business_mature_case cbmc where (cbmc.id = tableAlias.case_id)) AS case_id, "),

    //S_BASE_SELECT_ITEM为例子
    S_BASE_SELECT_ITEM("item_id", "(select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = tableAlias.item_id)) AS item_id, "),

    //C_BUSINESS_FRONT_SELECT_ITEM为例子
    C_BUSINESS_FRONT_SELECT_ITEM("item_id", "(select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = tableAlias.item_id)) AS item_id, "),

    //S_BASE_SELECT_ITEM的GROUP_CONCAT为例子
    S_BASE_SELECT_ITEM_GROUP("item_ids", "(SELECT concat( '[', group_concat( concat( '{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}' ) SEPARATOR ',' ), ']' ) FROM s_base_select_item sbsi WHERE find_in_set( sbsi.id, tableAlias.item_ids ) ) AS item_ids, "),

    //user_id
    S_BASE_USER("user_id", "( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = tableAlias.user_id) ) AS user_id, "),

    //book_id
    C_BUSINESS_BOOK_LIBRARY("book_id", "( SELECT concat( '{\"id\":\"', cbbl.id, '\",\"title\":\"', cbbl.title, '\",\"cover\":\"', cbbl.cover, '\",\"price\":\"', cbbl.price, '\"}' ) FROM c_business_book_library cbbl WHERE (cbbl.id = tableAlias.book_id) ) AS book_id, "),

    //digital_id
    C_BUSINESS_DIGITAL_SQUARE("digital_id", "( SELECT concat( '{\"id\":\"', cbds.id, '\",\"title\":\"', cbds.title, '\",\"cover\":\"', cbds.cover, '\",\"price\":\"', cbds.price, '\"}' ) FROM c_business_digital_square cbds WHERE (cbds.id = tableAlias.digital_id) ) AS digital_id, "),

    //electric_id
    C_BUSINESS_ELECTRIC_APPLIANCE("electric_id", "( SELECT concat( '{\"id\":\"', cbea.id, '\",\"title\":\"', cbea.title, '\",\"cover\":\"', cbea.cover, '\",\"price\":\"', cbea.price, '\"}' ) FROM c_business_electric_appliance cbea WHERE (cbea.id = tableAlias.electric_id) ) AS electric_id, "),

    //renting_id
    C_BUSINESS_RENTING_HOUSE("renting_id", "( SELECT concat( '{\"id\":\"', cbrh.id, '\",\"title\":\"', cbrh.title, '\",\"cover\":\"', cbrh.cover, '\",\"price\":\"', cbrh.price, '\"}' ) FROM c_business_renting_house cbrh WHERE (cbrh.id = tableAlias.renting_id) ) AS renting_id, "),

    //sports_id
    C_BUSINESS_SPORTS_SPECIAL("sports_id", "( SELECT concat( '{\"id\":\"', cbss.id, '\",\"title\":\"', cbss.title, '\",\"cover\":\"', cbss.cover, '\",\"price\":\"', cbss.price, '\"}' ) FROM c_business_sports_special cbss WHERE (cbss.id = tableAlias.sports_id) ) AS sports_id, ");

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
