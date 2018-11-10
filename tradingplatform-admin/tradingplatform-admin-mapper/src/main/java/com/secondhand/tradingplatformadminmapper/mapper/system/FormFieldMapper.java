package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : FormFieldMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */
@Repository
public interface FormFieldMapper extends BaseDao<FormField> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param formFieldId
     * @return
     */
    Map<String, Object> selectMapById(@Param("formFieldId") Long formFieldId);

    /**
     * 在数据库表中创建字段
     * @param tableName
     * @param columnType
     * @param formField
     * @return
     */
    @Update("<script>" +
            "ALTER TABLE ${tableName} " +
            "ADD COLUMN ${formField.fieldName}" +
            "<if test=\"null != columnType\">     ${columnType} </if>" +
            "<if test=\"null != formField.required and true == formField.required\">" +
            "NOT NULL" +
            "</if>" +
            "<if test=\"null != formField.defaultValue\">" +
            "DEFAULT ${formField.defaultValue}" +
            "</if>" +
            "<if test=\"null != formField.description\">" +
            "COMMENT #{formField.description}" +
            "</if>" +
            "after uuid" +
            "</script>")
    int createField(@Param("tableName") String tableName, @Param("columnType") String columnType, @Param("formField") FormField formField);

    /**
     * 在数据库表中修改字段
     * @param tableName
     * @param oldFieldName
     * @param columnType
     * @param formField
     * @return
     */
    @Update("<script>" +
            "ALTER TABLE ${tableName} " +
            "CHANGE COLUMN ${oldFieldName} ${formField.fieldName}" +
            "<if test=\"null != columnType\">     ${columnType} </if>" +
            "<if test=\"null != formField.required and true == formField.required\">" +
            "NOT NULL" +
            "</if>" +
            "<if test=\"null != formField.defaultValue\">" +
            "DEFAULT ${formField.defaultValue}" +
            "</if>" +
            "<if test=\"null != formField.description\">" +
            "COMMENT #{formField.description}" +
            "</if>" +
            "after uuid" +
            "</script>")
    int changeField(@Param("tableName") String tableName, @Param("oldFieldName") String oldFieldName, @Param("columnType") String columnType, @Param("formField") FormField formField);
}