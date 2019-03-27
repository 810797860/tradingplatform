package com.secondhand.tradingplatformadminmapper.mapper.front.article;

import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouseAdvisory;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : RentingHouseAdvisoryMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface RentingHouseAdvisoryMapper extends BaseDao<RentingHouseAdvisory> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param rentingHouseAdvisoryId
     * @return
     */
    Map<String, Object> selectMapById(@Param("rentingHouseAdvisoryId") Long rentingHouseAdvisoryId);
}