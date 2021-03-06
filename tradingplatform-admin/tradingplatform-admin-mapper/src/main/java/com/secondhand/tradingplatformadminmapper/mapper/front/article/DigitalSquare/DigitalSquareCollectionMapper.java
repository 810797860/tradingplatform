package com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare;

import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareCollection;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : DigitalSquareCollectionMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface DigitalSquareCollectionMapper extends BaseDao<DigitalSquareCollection> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param digitalSquareCollectionId
     * @return
     */
    Map<String, Object> selectMapById(@Param("digitalSquareCollectionId") Long digitalSquareCollectionId);
}