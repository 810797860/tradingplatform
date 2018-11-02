
package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import com.secondhand.tradingplatformcommon.redisCache.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : RoleMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-21
 */

@Repository
@CacheNamespace(implementation = RedisCache.class)
public interface RoleMapper extends BaseDao<Role> {

    /**
     * 根据id进行假删除
     * @param roleId
     * @return
     */
    boolean fakeDeleteById(@Param("roleId") Long roleId);

    /**
     * 批量假删除
     * @param roleIds
     * @return
     */
    boolean fakeBatchDelete(@Param("roleIds") List<Long> roleIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param roleId
     * @return
     */
    @Select("select * from s_base_role where id = #{roleId}")
    Map<String, Object> selectMapById(@Param("roleId") Long roleId);
}