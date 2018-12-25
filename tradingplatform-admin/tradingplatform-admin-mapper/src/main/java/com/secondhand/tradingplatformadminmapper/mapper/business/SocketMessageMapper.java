package com.secondhand.tradingplatformadminmapper.mapper.business;

import com.secondhand.tradingplatformadminentity.entity.business.SocketMessage;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : SocketMessageMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-25
 */
@Repository
public interface SocketMessageMapper extends BaseDao<SocketMessage> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param socketMessageId
     * @return
     */
    Map<String, Object> selectMapById(@Param("socketMessageId") Long socketMessageId);
}