package com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial;

import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialOrder;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : SportsSpecialOrderMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface SportsSpecialOrderMapper extends BaseDao<SportsSpecialOrder> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param sportsSpecialOrderId
     * @return
     */
    Map<String, Object> selectMapById(@Param("sportsSpecialOrderId") Long sportsSpecialOrderId);
}