
package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : UserMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-21
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
     * 自定化MapSql到MysqlGenerator生成
     * @param userId
     * @return
     */
    @Select("select * from s_base_user where id = #{userId}")
    Map<String, Object> selectMapById(@Param("userId") Long userId);
}