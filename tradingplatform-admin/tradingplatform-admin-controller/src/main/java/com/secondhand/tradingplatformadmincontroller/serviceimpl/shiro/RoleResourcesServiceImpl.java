package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleResources;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.RoleResourcesMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleResourcesService;
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
 *   @description : RoleResources 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-12
 */

@Service
@CacheConfig(cacheNames = "roleResources")
public class RoleResourcesServiceImpl extends BaseServiceImpl<RoleResourcesMapper, RoleResources> implements RoleResourcesService {

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteByRoleResources(RoleResources roleResources) {
        Wrapper<RoleResources> wrapper = new EntityWrapper<>();
        wrapper.where("role_id = {0}", roleResources.getRoleId());
        wrapper.where("resources_id = {0}", roleResources.getResourcesId());
        roleResources.setDeleted(true);
        return roleResourcesMapper.update(roleResources, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(Long roleId, List<Integer> resourcesIds) {
        RoleResources roleResources = new RoleResources();
        roleResources.setDeleted(true);
        resourcesIds.forEach(resourcesId -> {
            //这里就直接遍历假删除了，不去调用myFakeDelete
            Wrapper<RoleResources> wrapper = new EntityWrapper<>();
            wrapper.where("role_id = {0}", roleId);
            wrapper.where("resources_id = {0}", resourcesId.longValue());
            roleResourcesMapper.update(roleResources, wrapper);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long roleResourcesId) {
        return roleResourcesMapper.selectMapById(roleResourcesId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RoleResources myRoleResourcesCreateUpdate(RoleResources roleResources) {
        Long roleResourcesId = roleResources.getId();
        if (roleResourcesId == null){
            roleResources.setUuid(ToolUtil.getUUID());
            roleResourcesMapper.insert(roleResources);
        } else {
            roleResourcesMapper.updateById(roleResources);
        }
        return roleResources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myRoleResourcesBatchCreate(Long roleId, List<Integer> resourcesIds) {
        resourcesIds.forEach(resourcesId->{
            //这里就自己写了，为了快一点(因为都是新增)
            RoleResources roleResources = new RoleResources();
            roleResources.setUuid(ToolUtil.getUUID());
            roleResources.setRoleId(roleId);
            roleResources.setResourcesId(resourcesId.longValue());
            roleResourcesMapper.insert(roleResources);
        });
        return true;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p1")
    public Page<RoleResources> mySelectPageWithParam(Page<RoleResources> page, RoleResources roleResources) {
        Wrapper<RoleResources> wrapper = new EntityWrapper<>(roleResources);
        //没id，自己重写
        wrapper.setSqlSelect("uuid", "description", "deleted", "created_by", "created_at", "updated_by", "updated_at", "role_id", "resources_id");
        return this.selectPage(page, wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RoleResources> mySelectListWithMap(Map<String, Object> map) {
        return roleResourcesMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<RoleResources> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RoleResources> mySelectList(Wrapper<RoleResources> wrapper) {
        return roleResourcesMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RoleResources roleResources) {
        roleResources.setUuid(ToolUtil.getUUID());
        return this.insert(roleResources);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RoleResources> roleResourcesList) {
        for (RoleResources roleResources : roleResourcesList){
            roleResources.setUuid(ToolUtil.getUUID());
        }
        return this.insertBatch(roleResourcesList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RoleResources roleResources) {
        //没有uuid的话要加上去
        if (roleResources.getUuid().equals(null)){
            roleResources.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(roleResources);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RoleResources> roleResourcesList) {
        for (RoleResources roleResources : roleResourcesList){
        //没有uuid的话要加上去
            if (roleResources.getUuid().equals(null)){
                roleResources.setUuid(ToolUtil.getUUID());
            }
        }
        return this.insertOrUpdateBatch(roleResourcesList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RoleResources> mySelectBatchIds(Collection<? extends Serializable> roleResourcesIds) {
        return roleResourcesMapper.selectBatchIds(roleResourcesIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RoleResources mySelectById(Serializable roleResourcesId) {
        return roleResourcesMapper.selectById(roleResourcesId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<RoleResources> wrapper) {
        return roleResourcesMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RoleResources mySelectOne(Wrapper<RoleResources> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RoleResources roleResources, Wrapper<RoleResources> wrapper) {
        return this.update(roleResources, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RoleResources> roleResourcesList) {
        return this.updateBatchById(roleResourcesList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RoleResources roleResources) {
        return this.updateById(roleResources);
    }
}