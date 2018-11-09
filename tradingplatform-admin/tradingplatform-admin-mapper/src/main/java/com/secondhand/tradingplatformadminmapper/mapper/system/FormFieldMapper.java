package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
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
}