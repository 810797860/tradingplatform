package com.secondhand.tradingplatformadminservice.service.admin.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : User 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-13
 */
public interface UserService extends BaseService<User> {

        /**
         * 根据id进行假删除
         * @param userId
         * @return
         */
        Integer myFakeDeleteById(Long userId);

        /**
         * 根据ids进行批量假删除
         * @param userIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> userIds);

        /**
         * 获取Map数据（Obj）
         * @param userId
         * @return
         */
        Map<String, Object> mySelectMapById(Long userId);

        /**
         * 新增或修改user
         * @param user
         * @return
         */
        User myUserCreateUpdate(User user, Long userType) throws CustomizeException;

        /**
         * 根据account查找user
         * @param account
         * @return
         */
        User selectByUserAccount(String account);

        /**
         * 分页获取User列表数据（实体类）
         * @param page
         * @param user
         * @return
         */
        Page<User> mySelectPageWithParam(Page<User> page, User user);

        /**
         * 获取User列表数据（Map）
         * @param map
         * @return
         */
        List<User> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<User> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<User> mySelectList(Wrapper<User> wrapper);

        /**
         * 插入User
         * @param user
         * @return
         */
        boolean myInsert(User user);

        /**
         * 批量插入List<User>
         * @param userList
         * @return
         */
        boolean myInsertBatch(List<User> userList);

        /**
         * 插入或更新user
         * @param user
         * @return
         */
        boolean myInsertOrUpdate(User user);

        /**
         * 批量插入或更新List<User>
         * @param userList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<User> userList);

        /**
         * 根据userIds获取List
         * @param userIds
         * @return
         */
        List<User> mySelectBatchIds(Collection<? extends Serializable> userIds);

        /**
         * 根据userId获取User
         * @param userId
         * @return
         */
        User mySelectById(Serializable userId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<User> wrapper);

        /**
         * 根据wrapper获取User
         * @param wrapper
         * @return
         */
        User mySelectOne(Wrapper<User> wrapper);

        /**
         * 根据user和wrapper更新user
         * @param user
         * @param wrapper
         * @return
         */
        boolean myUpdate(User user, Wrapper<User> wrapper);

        /**
         * 根据userList更新user
         * @param userList
         * @return
         */
        boolean myUpdateBatchById(List<User> userList);

        /**
         * 根据userId修改user
         * @param user
         * @return
         */
        boolean myUpdateById(User user);

}
