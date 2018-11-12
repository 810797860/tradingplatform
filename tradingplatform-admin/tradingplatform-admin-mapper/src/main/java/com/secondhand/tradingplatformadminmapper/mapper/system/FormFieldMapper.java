package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * 在数据库表中删除字段
     * @param tableName
     * @param fieldName
     * @return
     */
    @Update("ALTER TABLE ${tableName} DROP ${fieldName}")
    int deleteField(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

    /**
     * 假删除掉该表的字段
     * @param formId
     * @return
     */
    @Update("update s_base_form_field set deleted = true where form_id = #{formId}")
    int fakeDeleteByFormId(@Param("formId") Long formId);

    /**
     * 查询字典表，获取该表物理字段
     * @return
     */
    List<Map<String, Object>> selectByInformationSchema();
}