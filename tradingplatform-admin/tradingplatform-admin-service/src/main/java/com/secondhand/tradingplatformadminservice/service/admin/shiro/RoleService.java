package com.secondhand.tradingplatformadminservice.service.admin.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Role;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : Role 服务接口
 * ---------------------------------
 * @since 2018-11-13
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 根据id进行假删除
     *
     * @param roleId
     * @return
     */
    Integer myFakeDeleteById(Long roleId);

    /**
     * 根据ids进行批量假删除
     *
     * @param roleIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> roleIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param roleId
     * @return
     */
    Map<String, Object> mySelectMapById(Long roleId);

    /**
     * 新增或修改role
     *
     * @param role
     * @return
     */
    Role myRoleCreateUpdate(Role role);

    /**
     * 分页获取Role列表数据（实体类）
     *
     * @param page
     * @param role
     * @return
     */
    Page<Role> mySelectPageWithParam(Page<Role> page, Role role);

    /**
     * 获取Role列表数据（Map）
     *
     * @param map
     * @return
     */
    List<Role> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<Role> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<Role> mySelectList(Wrapper<Role> wrapper);

    /**
     * 插入Role
     *
     * @param role
     * @return
     */
    boolean myInsert(Role role);

    /**
     * 批量插入List<Role>
     *
     * @param roleList
     * @return
     */
    boolean myInsertBatch(List<Role> roleList);

    /**
     * 插入或更新role
     *
     * @param role
     * @return
     */
    boolean myInsertOrUpdate(Role role);

    /**
     * 批量插入或更新List<Role>
     *
     * @param roleList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<Role> roleList);

    /**
     * 根据roleIds获取List
     *
     * @param roleIds
     * @return
     */
    List<Role> mySelectBatchIds(Collection<? extends Serializable> roleIds);

    /**
     * 根据roleId获取Role
     *
     * @param roleId
     * @return
     */
    Role mySelectById(Serializable roleId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<Role> wrapper);

    /**
     * 根据wrapper获取Role
     *
     * @param wrapper
     * @return
     */
    Role mySelectOne(Wrapper<Role> wrapper);

    /**
     * 根据role和wrapper更新role
     *
     * @param role
     * @param wrapper
     * @return
     */
    boolean myUpdate(Role role, Wrapper<Role> wrapper);

    /**
     * 根据roleList更新role
     *
     * @param roleList
     * @return
     */
    boolean myUpdateBatchById(List<Role> roleList);

    /**
     * 根据roleId修改role
     *
     * @param role
     * @return
     */
    boolean myUpdateById(Role role);

    /**
     * 查找所有的角色
     *
     * @return
     */
    List<Role> mySelectAllList();
}
