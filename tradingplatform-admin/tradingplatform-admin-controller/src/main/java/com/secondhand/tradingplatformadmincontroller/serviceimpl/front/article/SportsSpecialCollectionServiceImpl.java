package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecialCollection;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecialCollectionMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecialCollectionService;
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
 *   @description : SportsSpecialCollection 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "sportsSpecialCollection")
public class SportsSpecialCollectionServiceImpl extends BaseServiceImpl<SportsSpecialCollectionMapper, SportsSpecialCollection> implements SportsSpecialCollectionService {

    @Autowired
    private SportsSpecialCollectionMapper sportsSpecialCollectionMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long sportsSpecialCollectionId) {
        SportsSpecialCollection sportsSpecialCollection = new SportsSpecialCollection();
        sportsSpecialCollection.setId(sportsSpecialCollectionId);
        sportsSpecialCollection.setDeleted(true);
        return sportsSpecialCollectionMapper.updateById(sportsSpecialCollection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> sportsSpecialCollectionIds) {
        sportsSpecialCollectionIds.forEach(sportsSpecialCollectionId->{
            myFakeDeleteById(sportsSpecialCollectionId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long sportsSpecialCollectionId) {
        return sportsSpecialCollectionMapper.selectMapById(sportsSpecialCollectionId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SportsSpecialCollection mySportsSpecialCollectionCreateUpdate(SportsSpecialCollection sportsSpecialCollection) {
        Long sportsSpecialCollectionId = sportsSpecialCollection.getId();
        if (sportsSpecialCollectionId == null){
            sportsSpecialCollection.setUuid(ToolUtil.getUUID());
            sportsSpecialCollectionMapper.insert(sportsSpecialCollection);
        } else {
            sportsSpecialCollectionMapper.updateById(sportsSpecialCollection);
        }
        return sportsSpecialCollection;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialCollection sportsSpecialCollection) {

        //判空
        sportsSpecialCollection.setDeleted(false);
        Wrapper<SportsSpecialCollection> wrapper = new EntityWrapper<>(sportsSpecialCollection);
        //遍历排序
        List<Sort> sorts = sportsSpecialCollection.getSorts();
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
    public List<SportsSpecialCollection> mySelectListWithMap(Map<String, Object> map) {
        return sportsSpecialCollectionMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<SportsSpecialCollection> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<SportsSpecialCollection> mySelectList(Wrapper<SportsSpecialCollection> wrapper) {
        return sportsSpecialCollectionMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SportsSpecialCollection sportsSpecialCollection) {
        sportsSpecialCollection.setUuid(ToolUtil.getUUID());
        return this.insert(sportsSpecialCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SportsSpecialCollection> sportsSpecialCollectionList) {
        sportsSpecialCollectionList.forEach(sportsSpecialCollection -> {
            sportsSpecialCollection.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(sportsSpecialCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SportsSpecialCollection sportsSpecialCollection) {
        //没有uuid的话要加上去
        if (sportsSpecialCollection.getUuid().equals(null)){
            sportsSpecialCollection.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(sportsSpecialCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SportsSpecialCollection> sportsSpecialCollectionList) {
        sportsSpecialCollectionList.forEach(sportsSpecialCollection -> {
            //没有uuid的话要加上去
            if (sportsSpecialCollection.getUuid().equals(null)){
                sportsSpecialCollection.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(sportsSpecialCollectionList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SportsSpecialCollection> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialCollectionIds) {
        return sportsSpecialCollectionMapper.selectBatchIds(sportsSpecialCollectionIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public SportsSpecialCollection mySelectById(Serializable sportsSpecialCollectionId) {
        return sportsSpecialCollectionMapper.selectById(sportsSpecialCollectionId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<SportsSpecialCollection> wrapper) {
        return sportsSpecialCollectionMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public SportsSpecialCollection mySelectOne(Wrapper<SportsSpecialCollection> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<SportsSpecialCollection> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SportsSpecialCollection sportsSpecialCollection, Wrapper<SportsSpecialCollection> wrapper) {
        return this.update(sportsSpecialCollection, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SportsSpecialCollection> sportsSpecialCollectionList) {
        return this.updateBatchById(sportsSpecialCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SportsSpecialCollection sportsSpecialCollection) {
        return this.updateById(sportsSpecialCollection);
    }
}
