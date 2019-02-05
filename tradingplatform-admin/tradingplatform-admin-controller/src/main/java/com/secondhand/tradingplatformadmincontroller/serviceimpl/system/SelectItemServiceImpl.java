package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminmapper.mapper.system.SelectItemMapper;
import com.secondhand.tradingplatformadminservice.service.system.SelectItemService;
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
 *   @description : SelectItem 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-02-05
 */

@Service
@CacheConfig(cacheNames = "selectItem")
public class SelectItemServiceImpl extends BaseServiceImpl<SelectItemMapper, SelectItem> implements SelectItemService {

    @Autowired
    private SelectItemMapper selectItemMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long selectItemId) {
        SelectItem selectItem = new SelectItem();
        selectItem.setId(selectItemId);
        selectItem.setDeleted(true);
        return selectItemMapper.updateById(selectItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> selectItemIds) {
        selectItemIds.forEach(selectItemId->{
            myFakeDeleteById(selectItemId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long selectItemId) {
        return selectItemMapper.selectMapById(selectItemId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SelectItem mySelectItemCreateUpdate(SelectItem selectItem) {
        Long selectItemId = selectItem.getId();
        if (selectItemId == null){
            selectItem.setUuid(ToolUtil.getUUID());
            selectItemMapper.insert(selectItem);
        } else {
            selectItemMapper.updateById(selectItem);
        }
        return selectItem;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, SelectItem selectItem) {

        //判空
        selectItem.setDeleted(false);
        Wrapper<SelectItem> wrapper = new EntityWrapper<>(selectItem);
        //遍历排序
        List<Sort> sorts = selectItem.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectMapsPage(page, wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SelectItem> mySelectListWithMap(Map<String, Object> map) {
        return selectItemMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<SelectItem> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SelectItem> mySelectList(Wrapper<SelectItem> wrapper) {
        return selectItemMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SelectItem selectItem) {
        selectItem.setUuid(ToolUtil.getUUID());
        return this.insert(selectItem);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SelectItem> selectItemList) {
        selectItemList.forEach(selectItem -> {
            selectItem.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(selectItemList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SelectItem selectItem) {
        //没有uuid的话要加上去
        if (selectItem.getUuid().equals(null)){
            selectItem.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(selectItem);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SelectItem> selectItemList) {
        selectItemList.forEach(selectItem -> {
            //没有uuid的话要加上去
            if (selectItem.getUuid().equals(null)){
                selectItem.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(selectItemList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SelectItem> mySelectBatchIds(Collection<? extends Serializable> selectItemIds) {
        return selectItemMapper.selectBatchIds(selectItemIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public SelectItem mySelectById(Serializable selectItemId) {
        return selectItemMapper.selectById(selectItemId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<SelectItem> wrapper) {
        return selectItemMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public SelectItem mySelectOne(Wrapper<SelectItem> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SelectItem selectItem, Wrapper<SelectItem> wrapper) {
        return this.update(selectItem, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SelectItem> selectItemList) {
        return this.updateBatchById(selectItemList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SelectItem selectItem) {
        return this.updateById(selectItem);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<SelectItem> myGetItemsByPid(Long pid) {
        return selectItemMapper.myGetItemsByPid(pid);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<SelectItem> myGetAllItemsByPid(Long pid) {
        return selectItemMapper.myGetAllItemsByPid(pid);
    }
}
