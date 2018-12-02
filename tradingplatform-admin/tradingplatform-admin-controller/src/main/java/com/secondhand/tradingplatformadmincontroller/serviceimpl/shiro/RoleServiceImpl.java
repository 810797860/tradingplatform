package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.RoleMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleService;
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
 *   @description : Role 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-13
 */

@Service
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long roleId) {
        Role role = new Role();
        role.setId(roleId);
        role.setDeleted(true);
        return roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> roleIds) {
        roleIds.forEach(roleId->{
            myFakeDeleteById(roleId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long roleId) {
        return roleMapper.selectMapById(roleId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Role myRoleCreateUpdate(Role role) {
        Long roleId = role.getId();
        if (roleId == null){
            role.setUuid(ToolUtil.getUUID());
            roleMapper.insert(role);
        } else {
            roleMapper.updateById(role);
        }
        return role;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + '' + #p1+ #p1.sorts")
    public Page<Role> mySelectPageWithParam(Page<Role> page, Role role) {
        Wrapper<Role> wrapper = new EntityWrapper<>(role);
        //遍历排序
        List<Sort> sorts = role.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectPage(page, wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Role> mySelectListWithMap(Map<String, Object> map) {
        return roleMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Role> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Role> mySelectList(Wrapper<Role> wrapper) {
        return roleMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Role role) {
        role.setUuid(ToolUtil.getUUID());
        return this.insert(role);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Role> roleList) {
        roleList.forEach(role -> {
            role.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(roleList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Role role) {
        //没有uuid的话要加上去
        if (role.getUuid().equals(null)){
            role.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(role);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Role> roleList) {
        roleList.forEach(role -> {
            //没有uuid的话要加上去
            if (role.getUuid().equals(null)){
                role.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(roleList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Role> mySelectBatchIds(Collection<? extends Serializable> roleIds) {
        return roleMapper.selectBatchIds(roleIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Role mySelectById(Serializable roleId) {
        return roleMapper.selectById(roleId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Role> wrapper) {
        return roleMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Role mySelectOne(Wrapper<Role> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Role role, Wrapper<Role> wrapper) {
        return this.update(role, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Role> roleList) {
        return this.updateBatchById(roleList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Role role) {
        return this.updateById(role);
    }
}