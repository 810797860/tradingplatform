package com.secondhand.tradingplatformgenerator.example.mapper;

import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import com.secondhand.tradingplatformgenerator.example.entity.User;
import org.apache.ibatis.annotations.Param;
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
     *
     * @param userId
     * @return
     */
    boolean fakeDeleteById(@Param("userId") Long userId);

    /**
     * 批量假删除
     *
     * @param userIds
     * @return
     */
    boolean fakeBatchDelete(@Param("userIds") List<Long> userIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param userId
     * @return
     */
    Map<String, Object> selectMapById(@Param("userId") Long userId);
}
