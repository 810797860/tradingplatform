package com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial;

import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialCollection;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : SportsSpecialCollectionMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface SportsSpecialCollectionMapper extends BaseDao<SportsSpecialCollection> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param sportsSpecialCollectionId
     * @return
     */
    Map<String, Object> selectMapById(@Param("sportsSpecialCollectionId") Long sportsSpecialCollectionId);
}