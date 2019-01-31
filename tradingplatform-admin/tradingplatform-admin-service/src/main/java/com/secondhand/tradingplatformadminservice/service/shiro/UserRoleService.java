package com.secondhand.tradingplatformadminservice.service.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformadminentity.entity.shiro.UserRole;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : UserRole 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-22
 */
public interface UserRoleService extends BaseService<UserRole> {

        /**
         * 根据id进行假删除
         * @param userRole
         * @return
         */
        Integer myFakeDeleteByUserRole(UserRole userRole);

        /**
         * 根据ids进行批量假删除
         * @param userId
         * @param roleIds
         * @return
         */
        boolean myFakeBatchDelete(Long userId, List<Integer> roleIds);

        /**
         * 获取Map数据（Obj）
         * @param userRoleId
         * @return
         */
        Map<String, Object> mySelectMapById(Long userRoleId);

        /**
         * 新增或修改userRole
         * @param userRole
         * @return
         */
        UserRole myUserRoleCreateUpdate(UserRole userRole);

        /**
         * 分页获取能够增加Role的列表（实体类）
         * @param page
         * @param userRole
         * @return
         */
        Page<Role> mySelectEnableCreatePage(Page<Role> page, UserRole userRole);

        /**
         * 根据userId查找roleId
         * @param userId
         * @return
         */
        Long getRoleIdByUserId(Long userId);

        /**
         * 分页获取UserRole列表数据（实体类）
         * @param page
         * @param userRole
         * @return
         */
        Page<Role> mySelectPageWithParam(Page<Role> page, UserRole userRole);

        /**
         * 获取UserRole列表数据（Map）
         * @param map
         * @return
         */
        List<UserRole> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<UserRole> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<UserRole> mySelectList(Wrapper<UserRole> wrapper);

        /**
         * 插入UserRole
         * @param userRole
         * @return
         */
        boolean myInsert(UserRole userRole);

        /**
         * 批量插入List<UserRole>
         * @param userRoleList
         * @return
         */
        boolean myInsertBatch(List<UserRole> userRoleList);

        /**
         * 插入或更新userRole
         * @param userRole
         * @return
         */
        boolean myInsertOrUpdate(UserRole userRole);

        /**
         * 批量插入或更新List<UserRole>
         * @param userRoleList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<UserRole> userRoleList);

        /**
         * 根据userRoleIds获取List
         * @param userRoleIds
         * @return
         */
        List<UserRole> mySelectBatchIds(Collection<? extends Serializable> userRoleIds);

        /**
         * 根据userRoleId获取UserRole
         * @param userRoleId
         * @return
         */
        UserRole mySelectById(Serializable userRoleId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<UserRole> wrapper);

        /**
         * 根据wrapper获取UserRole
         * @param wrapper
         * @return
         */
        UserRole mySelectOne(Wrapper<UserRole> wrapper);

        /**
         * 根据userRole和wrapper更新userRole
         * @param userRole
         * @param wrapper
         * @return
         */
        boolean myUpdate(UserRole userRole, Wrapper<UserRole> wrapper);

        /**
         * 根据userRoleList更新userRole
         * @param userRoleList
         * @return
         */
        boolean myUpdateBatchById(List<UserRole> userRoleList);

        /**
         * 根据userRoleId修改userRole
         * @param userRole
         * @return
         */
        boolean myUpdateById(UserRole userRole);

}