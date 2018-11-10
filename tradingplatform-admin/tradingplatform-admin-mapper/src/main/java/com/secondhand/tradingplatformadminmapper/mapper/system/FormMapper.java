
package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : FormMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-08
 */
@Repository
public interface FormMapper extends BaseDao<Form> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param formId
     * @return
     */
    Map<String, Object> selectMapById(@Param("formId") Long formId);

    /**
     * 在数据库中创建表
     * @param form
     * @return
     */
    @Insert("<script>" +
            "create table if not exists ${form.collection}( " +
            "id  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键', " +
            "uuid  varchar(32) NULL COMMENT '全局id', " +
            "description varchar(1024) DEFAULT NULL COMMENT '备注', " +
            "deleted bit(1) DEFAULT 0 COMMENT '是否已删除', " +
            "created_by bigint(20) DEFAULT NULL COMMENT '创建人', " +
            "created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', " +
            "updated_by bigint(20) DEFAULT NULL COMMENT '更新人', " +
            "updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', " +
            "PRIMARY KEY (id) )ENGINE=InnoDB DEFAULT CHARSET=utf8 " +
            "<if test=\"null != form.description\">" +
            "COMMENT #{form.description}" +
            "</if>" +
            "</script>")
    int createTable(@Param("form") Form form);

    /**
     * 在数据库中修改数据库表名
     * @param oldCollection
     * @param newCollection
     * @return
     */
    @Update("alter table ${oldCollection} rename ${newCollection}")
    int updateTable(@Param("oldCollection") String oldCollection, @Param("newCollection") String newCollection);
}