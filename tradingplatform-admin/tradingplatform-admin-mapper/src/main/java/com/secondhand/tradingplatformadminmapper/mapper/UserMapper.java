package com.secondhand.tradingplatformadminmapper.mapper;

import com.secondhand.tradingplatformadminentity.entity.User;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

@Repository
public interface UserMapper extends BaseDao<User> {

    /**
     * 根据id进行假删除
     * @param userId
     * @return
     */
    boolean fakeDeleteById(@Param("userId") Long userId);

    /**
     * 批量假删除
     * @param userIds
     * @return
     */
    boolean fakeBatchDelete(@Param("userIds") List<Long> userIds);

    /**
     * 获取Map数据（Obj）
     * @param userId
     * @return
     */

    @Select("select sbu.id as id, sbu.avatar as avatar, sbu.account as account, sbu.password as password, sbu.salt as salt, sbu.name as name, sbu.birthday as birthday, sbu.sex as sex, sbu.email as email, sbu.phone as phone, sbu.roleId as roleId, sbu.deptId as deptId, sbu.status as status, sbu.createTime as createTime, sbu.version as version, sbu.deleted as deleted, sbu.created_at as created_at from s_base_user sbu where id = #{userId}")
    Map<String, Object> selectMapById(@Param("userId") Long userId);
}
