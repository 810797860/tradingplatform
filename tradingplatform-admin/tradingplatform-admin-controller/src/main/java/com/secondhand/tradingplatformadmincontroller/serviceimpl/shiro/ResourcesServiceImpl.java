package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleResources;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.ResourcesMapper;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.RoleResourcesMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.ResourcesService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
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
 *   @description : Resources 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-12
 */

@Service
@CacheConfig(cacheNames = "resources")
public class ResourcesServiceImpl extends BaseServiceImpl<ResourcesMapper, Resources> implements ResourcesService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long resourcesId) {

        //关联的role_resources的权限也假删除掉
        roleResourcesMapper.deleteWithResourcesById(resourcesId);

        Resources resources = new Resources();
        resources.setId(resourcesId);
        resources.setDeleted(true);
        return resourcesMapper.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> resourcesIds) {
        for (Long resourcesId : resourcesIds){
            myFakeDeleteById(resourcesId);
        }
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long resourcesId) {
        return resourcesMapper.selectMapById(resourcesId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Resources myResourcesCreateUpdate(Resources resources) {
        Long resourcesId = resources.getId();
        if (resourcesId == null){
            resources.setUuid(ToolUtil.getUUID());
            resourcesMapper.insert(resources);

            //role_resources表默认给管理员也配上权限
            RoleResources roleResources = new RoleResources();
            roleResources.setUuid(ToolUtil.getUUID());
            roleResources.setRoleId(MagicalValue.ADMINISTRATOR_ID);
            //新增后resources就会有id了，我也不知道为什么
            roleResources.setResourcesId(resources.getId());
            roleResourcesMapper.insert(roleResources);
        } else {
            resourcesMapper.updateById(resources);
        }
        return resources;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p1")
    public Page<Resources> mySelectPageWithParam(Page<Resources> page, Resources resources) {
        Wrapper<Resources> wrapper = new EntityWrapper<>(resources);
        return this.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Resources> mySelectListWithMap(Map<String, Object> map) {
        return resourcesMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Resources> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Resources> mySelectList(Wrapper<Resources> wrapper) {
        return resourcesMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Resources resources) {
        resources.setUuid(ToolUtil.getUUID());
        return this.insert(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Resources> resourcesList) {
        for (Resources resources : resourcesList){
            resources.setUuid(ToolUtil.getUUID());
        }
        return this.insertBatch(resourcesList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Resources resources) {
        //没有uuid的话要加上去
        if (resources.getUuid().equals(null)){
            resources.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Resources> resourcesList) {
        for (Resources resources : resourcesList){
            //没有uuid的话要加上去
            if (resources.getUuid().equals(null)){
                resources.setUuid(ToolUtil.getUUID());
            }
        }
        return this.insertOrUpdateBatch(resourcesList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Resources> mySelectBatchIds(Collection<? extends Serializable> resourcesIds) {
        return resourcesMapper.selectBatchIds(resourcesIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public Resources mySelectById(Serializable resourcesId) {
        return resourcesMapper.selectById(resourcesId);
    }

    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Resources> wrapper) {
        return resourcesMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public Resources mySelectOne(Wrapper<Resources> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Resources resources, Wrapper<Resources> wrapper) {
        return this.update(resources, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Resources> resourcesList) {
        return this.updateBatchById(resourcesList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Resources resources) {
        return this.updateById(resources);
    }

    @Override
    @Cacheable(key = "'myQueryAll'")
    public List<Resources> myQueryAll() {
        Wrapper<Resources> wrapper = new EntityWrapper<>();
        wrapper.where("deleted = {0}", false);
        return this.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Resources> myLoadUserResources(Long userId) {
        return resourcesMapper.loadUserResources(userId);
    }
}
