package com.secondhand.tradingplatformadminmapper.mapper.admin.shiro;

import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Role;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : RoleMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-13
 */
@Repository
public interface RoleMapper extends BaseDao<Role> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param roleId
     * @return
     */
    Map<String, Object> selectMapById(@Param("roleId") Long roleId);
}