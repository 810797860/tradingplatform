package com.secondhand.tradingplatformgenerator.example.service;

import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;
import com.secondhand.tradingplatformgenerator.example.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

public interface UserService extends BaseService<User> {

    /**
     * 根据id进行假删除
     *
     * @param userId
     * @return
     */
    boolean fakeDeleteById(Long userId);

    /**
     * 根据ids进行批量假删除
     *
     * @param userIds
     * @return
     */
    boolean fakeBatchDelete(List<Long> userIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param userId
     * @return
     */
    Map<String, Object> selectMapById(Long userId);

    /**
     * 新增或修改user
     *
     * @param user
     * @return
     */
    User userCreateUpdate(User user);
}
