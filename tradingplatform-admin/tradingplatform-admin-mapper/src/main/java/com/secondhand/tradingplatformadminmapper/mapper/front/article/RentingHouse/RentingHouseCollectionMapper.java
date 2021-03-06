package com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse;

import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseCollection;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : RentingHouseCollectionMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface RentingHouseCollectionMapper extends BaseDao<RentingHouseCollection> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param rentingHouseCollectionId
     * @return
     */
    Map<String, Object> selectMapById(@Param("rentingHouseCollectionId") Long rentingHouseCollectionId);
}