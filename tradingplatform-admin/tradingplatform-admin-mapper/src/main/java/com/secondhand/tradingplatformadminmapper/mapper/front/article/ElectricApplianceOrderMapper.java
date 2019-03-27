package com.secondhand.tradingplatformadminmapper.mapper.front.article;

import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricApplianceOrder;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : ElectricApplianceOrderMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface ElectricApplianceOrderMapper extends BaseDao<ElectricApplianceOrder> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param electricApplianceOrderId
     * @return
     */
    Map<String, Object> selectMapById(@Param("electricApplianceOrderId") Long electricApplianceOrderId);
}