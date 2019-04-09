package com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse;

import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseOrder;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : RentingHouseOrderMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface RentingHouseOrderMapper extends BaseDao<RentingHouseOrder> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param rentingHouseOrderId
     * @return
     */
    Map<String, Object> selectMapById(@Param("rentingHouseOrderId") Long rentingHouseOrderId);
}