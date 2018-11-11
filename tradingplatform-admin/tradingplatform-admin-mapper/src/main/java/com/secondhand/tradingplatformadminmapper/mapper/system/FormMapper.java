package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : FormMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-11
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
}