package com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial;

import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecial;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangjk
 * @description : SportsSpecialMapper 接口
 * ---------------------------------
 * @since 2019-03-16
 */
@Repository
public interface SportsSpecialMapper extends BaseDao<SportsSpecial> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     *
     * @param sportsSpecialId
     * @return
     */
    Map<String, Object> selectMapById(@Param("sportsSpecialId") Long sportsSpecialId);
}