package com.secondhand.tradingplatformadminmapper.mapper.front.article;

import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : ElectricApplianceMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-15
 */
@Repository
public interface ElectricApplianceMapper extends BaseDao<ElectricAppliance> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param electricApplianceId
     * @return
     */
    Map<String, Object> selectMapById(@Param("electricApplianceId") Long electricApplianceId);
}