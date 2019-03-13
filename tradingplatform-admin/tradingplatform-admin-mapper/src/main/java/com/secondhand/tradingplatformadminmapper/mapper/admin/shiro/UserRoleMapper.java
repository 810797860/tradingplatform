package com.secondhand.tradingplatformadminmapper.mapper.admin.shiro;

import com.secondhand.tradingplatformadminentity.entity.admin.shiro.UserRole;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : UserRoleMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-22
 */
@Repository
public interface UserRoleMapper extends BaseDao<UserRole> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param userRoleId
     * @return
     */
    Map<String, Object> selectMapById(@Param("userRoleId") Long userRoleId);
}