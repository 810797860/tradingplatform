package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformadminentity.entity.shiro.UserRole;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.UserRoleMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleService;
import com.secondhand.tradingplatformadminservice.service.shiro.UserRoleService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : UserRole 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-22
 */

@Service
@CacheConfig(cacheNames = "userRole")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteByUserRole(UserRole userRole) {

        Wrapper<UserRole> wrapper = new EntityWrapper<>();
        wrapper.where("user_id = {0}", userRole.getUserId());
        wrapper.where("role_id = {0}", userRole.getRoleId());
        userRole.setDeleted(true);
        return userRoleMapper.update(userRole, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(Long userId, List<Integer> roleIds) {
        UserRole userRole = new UserRole();
        userRole.setDeleted(true);
        roleIds.forEach(roleId -> {
            //这里就直接遍历假删除了，不去调用myFakeDelete
            Wrapper<UserRole> wrapper = new EntityWrapper<>();
            wrapper.where("user_id = {0}", userId);
            wrapper.where("role_id = {0}", roleId);
            userRoleMapper.update(userRole, wrapper);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long userRoleId) {
        return userRoleMapper.selectMapById(userRoleId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public UserRole myUserRoleCreateUpdate(UserRole userRole) {
        Long userRoleId = userRole.getId();
        if (userRoleId == null){
            userRole.setUuid(ToolUtil.getUUID());
            userRoleMapper.insert(userRole);
        } else {
            userRoleMapper.updateById(userRole);
        }
        return userRole;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'EnableCreate' + #p0 + '' + #p1 + #p1.sorts")
    public Page<Role> mySelectEnableCreatePage(Page<Role> page, UserRole userRole) {

        //先找出roleIds
        Wrapper<UserRole> wrapper = new EntityWrapper<>(userRole);
        wrapper.setSqlSelect("role_id");
        List<Object> roleIds = this.selectObjs(wrapper);
        //再根据id找rolePage
        Wrapper<Role> roleWrapper = new EntityWrapper<>();
        roleWrapper.notIn("id", roleIds);
        //判空
        roleWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = userRole.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            roleWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                roleWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return roleService.selectPage(page, roleWrapper);
    }

    //以下是继承BaseServiceImpl

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'MyRole' + #p0 + '' + #p1 + #p1.sorts")
    public Page<Role> mySelectPageWithParam(Page<Role> page, UserRole userRole) {

        //先找出roleIds
        Wrapper<UserRole> wrapper = new EntityWrapper<>(userRole);
        wrapper.setSqlSelect("role_id");
        List<Object> roleIds = this.selectObjs(wrapper);
        //如果roleIds为空，返回空的对象
        if (roleIds.size() == 0){
            return new Page<Role>();
        }
        //再根据id找rolePage
        Wrapper<Role> roleWrapper = new EntityWrapper<>();
        roleWrapper.in("id", roleIds);
        //判空
        roleWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = userRole.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            roleWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                roleWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return roleService.selectPage(page, roleWrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<UserRole> mySelectListWithMap(Map<String, Object> map) {
        return userRoleMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<UserRole> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<UserRole> mySelectList(Wrapper<UserRole> wrapper) {
        return userRoleMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(UserRole userRole) {
        userRole.setUuid(ToolUtil.getUUID());
        return this.insert(userRole);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<UserRole> userRoleList) {
        userRoleList.forEach(userRole -> {
            userRole.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(userRoleList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(UserRole userRole) {
        //没有uuid的话要加上去
        if (userRole.getUuid().equals(null)){
            userRole.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(userRole);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<UserRole> userRoleList) {
        userRoleList.forEach(userRole -> {
            //没有uuid的话要加上去
            if (userRole.getUuid().equals(null)){
                userRole.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(userRoleList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<UserRole> mySelectBatchIds(Collection<? extends Serializable> userRoleIds) {
        return userRoleMapper.selectBatchIds(userRoleIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public UserRole mySelectById(Serializable userRoleId) {
        return userRoleMapper.selectById(userRoleId);
    }

    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<UserRole> wrapper) {
        return userRoleMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public UserRole mySelectOne(Wrapper<UserRole> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(UserRole userRole, Wrapper<UserRole> wrapper) {
        return this.update(userRole, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<UserRole> userRoleList) {
        return this.updateBatchById(userRoleList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(UserRole userRole) {
        return this.updateById(userRole);
    }
}
