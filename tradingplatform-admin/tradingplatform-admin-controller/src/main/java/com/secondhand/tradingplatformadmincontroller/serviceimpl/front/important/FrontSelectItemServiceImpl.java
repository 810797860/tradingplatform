package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.important;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.important.FrontSelectItem;
import com.secondhand.tradingplatformadminmapper.mapper.front.important.FrontSelectItemMapper;
import com.secondhand.tradingplatformadminservice.service.front.important.FrontSelectItemService;
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
 * @author zhangjk
 * @description : FrontSelectItem 服务实现类
 * ---------------------------------
 * @since 2019-03-15
 */

@Service
@CacheConfig(cacheNames = "frontSelectItem")
public class FrontSelectItemServiceImpl extends BaseServiceImpl<FrontSelectItemMapper, FrontSelectItem> implements FrontSelectItemService {

    @Autowired
    private FrontSelectItemMapper frontSelectItemMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long frontSelectItemId) {
        FrontSelectItem frontSelectItem = new FrontSelectItem();
        frontSelectItem.setId(frontSelectItemId);
        frontSelectItem.setDeleted(true);
        return frontSelectItemMapper.updateById(frontSelectItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> frontSelectItemIds) {
        frontSelectItemIds.forEach(frontSelectItemId -> {
            myFakeDeleteById(frontSelectItemId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long frontSelectItemId) {
        return frontSelectItemMapper.selectMapById(frontSelectItemId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public FrontSelectItem myFrontSelectItemCreateUpdate(FrontSelectItem frontSelectItem) {
        Long frontSelectItemId = frontSelectItem.getId();
        if (frontSelectItemId == null) {
            frontSelectItem.setUuid(ToolUtil.getUUID());
            frontSelectItemMapper.insert(frontSelectItem);
        } else {
            frontSelectItemMapper.updateById(frontSelectItem);
        }
        return frontSelectItem;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, FrontSelectItem frontSelectItem) {

        //判空
        frontSelectItem.setDeleted(false);
        Wrapper<FrontSelectItem> wrapper = new EntityWrapper<>(frontSelectItem);
        //遍历排序
        List<Sort> sorts = frontSelectItem.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectMapsPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<FrontSelectItem> mySelectListWithMap(Map<String, Object> map) {
        return frontSelectItemMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<FrontSelectItem> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<FrontSelectItem> mySelectList(Wrapper<FrontSelectItem> wrapper) {
        return frontSelectItemMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(FrontSelectItem frontSelectItem) {
        frontSelectItem.setUuid(ToolUtil.getUUID());
        return this.insert(frontSelectItem);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<FrontSelectItem> frontSelectItemList) {
        frontSelectItemList.forEach(frontSelectItem -> {
            frontSelectItem.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(frontSelectItemList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(FrontSelectItem frontSelectItem) {
        //没有uuid的话要加上去
        if (frontSelectItem.getUuid().equals(null)) {
            frontSelectItem.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(frontSelectItem);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<FrontSelectItem> frontSelectItemList) {
        frontSelectItemList.forEach(frontSelectItem -> {
            //没有uuid的话要加上去
            if (frontSelectItem.getUuid().equals(null)) {
                frontSelectItem.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(frontSelectItemList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<FrontSelectItem> mySelectBatchIds(Collection<? extends Serializable> frontSelectItemIds) {
        return frontSelectItemMapper.selectBatchIds(frontSelectItemIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public FrontSelectItem mySelectById(Serializable frontSelectItemId) {
        return frontSelectItemMapper.selectById(frontSelectItemId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<FrontSelectItem> wrapper) {
        return frontSelectItemMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public FrontSelectItem mySelectOne(Wrapper<FrontSelectItem> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<FrontSelectItem> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(FrontSelectItem frontSelectItem, Wrapper<FrontSelectItem> wrapper) {
        return this.update(frontSelectItem, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<FrontSelectItem> frontSelectItemList) {
        return this.updateBatchById(frontSelectItemList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(FrontSelectItem frontSelectItem) {
        return this.updateById(frontSelectItem);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<FrontSelectItem> myGetItemsByPid(Long pid) {
        return frontSelectItemMapper.myGetItemsByPid(pid);
    }

    @Override
    @Cacheable(key = "'myGetAllItemsByPid' + #p0")
    public List<FrontSelectItem> myGetAllItemsByPid(Long pid) {
        return frontSelectItemMapper.myGetAllItemsByPid(pid);
    }
}
