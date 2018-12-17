package com.secondhand.tradingplatformadminmapper.mapper.business;

import com.secondhand.tradingplatformadminentity.entity.business.Annex;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : AnnexMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-14
 */
@Repository
public interface AnnexMapper extends BaseDao<Annex> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param annexId
     * @return
     */
    Map<String, Object> selectMapById(@Param("annexId") Long annexId);
}