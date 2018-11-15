package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : UserMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-13
 */
@Repository
public interface UserMapper extends BaseDao<User> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param userId
     * @return
     */
    Map<String, Object> selectMapById(@Param("userId") Long userId);
}