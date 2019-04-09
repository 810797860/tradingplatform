package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.SportsSpecial;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialAdvisory;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial.SportsSpecialAdvisoryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialAdvisoryService;
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
 *   @description : SportsSpecialAdvisory 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "sportsSpecialAdvisory")
public class SportsSpecialAdvisoryServiceImpl extends BaseServiceImpl<SportsSpecialAdvisoryMapper, SportsSpecialAdvisory> implements SportsSpecialAdvisoryService {

    @Autowired
    private SportsSpecialAdvisoryMapper sportsSpecialAdvisoryMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long sportsSpecialAdvisoryId) {
        SportsSpecialAdvisory sportsSpecialAdvisory = new SportsSpecialAdvisory();
        sportsSpecialAdvisory.setId(sportsSpecialAdvisoryId);
        sportsSpecialAdvisory.setDeleted(true);
        return sportsSpecialAdvisoryMapper.updateById(sportsSpecialAdvisory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> sportsSpecialAdvisoryIds) {
        sportsSpecialAdvisoryIds.forEach(sportsSpecialAdvisoryId->{
            myFakeDeleteById(sportsSpecialAdvisoryId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long sportsSpecialAdvisoryId) {
        return sportsSpecialAdvisoryMapper.selectMapById(sportsSpecialAdvisoryId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SportsSpecialAdvisory mySportsSpecialAdvisoryCreateUpdate(SportsSpecialAdvisory sportsSpecialAdvisory) {
        Long sportsSpecialAdvisoryId = sportsSpecialAdvisory.getId();
        if (sportsSpecialAdvisoryId == null){
            sportsSpecialAdvisory.setUuid(ToolUtil.getUUID());
            sportsSpecialAdvisoryMapper.insert(sportsSpecialAdvisory);
        } else {
            sportsSpecialAdvisoryMapper.updateById(sportsSpecialAdvisory);
        }
        return sportsSpecialAdvisory;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialAdvisory sportsSpecialAdvisory) {

        //判空
        sportsSpecialAdvisory.setDeleted(false);
        Wrapper<SportsSpecialAdvisory> wrapper = new EntityWrapper<>(sportsSpecialAdvisory);
        //遍历排序
        List<Sort> sorts = sportsSpecialAdvisory.getSorts();
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
    public List<SportsSpecialAdvisory> mySelectListWithMap(Map<String, Object> map) {
        return sportsSpecialAdvisoryMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<SportsSpecialAdvisory> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<SportsSpecialAdvisory> mySelectList(Wrapper<SportsSpecialAdvisory> wrapper) {
        return sportsSpecialAdvisoryMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SportsSpecialAdvisory sportsSpecialAdvisory) {
        sportsSpecialAdvisory.setUuid(ToolUtil.getUUID());
        return this.insert(sportsSpecialAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SportsSpecialAdvisory> sportsSpecialAdvisoryList) {
        sportsSpecialAdvisoryList.forEach(sportsSpecialAdvisory -> {
            sportsSpecialAdvisory.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(sportsSpecialAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SportsSpecialAdvisory sportsSpecialAdvisory) {
        //没有uuid的话要加上去
        if (sportsSpecialAdvisory.getUuid().equals(null)){
            sportsSpecialAdvisory.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(sportsSpecialAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SportsSpecialAdvisory> sportsSpecialAdvisoryList) {
        sportsSpecialAdvisoryList.forEach(sportsSpecialAdvisory -> {
            //没有uuid的话要加上去
            if (sportsSpecialAdvisory.getUuid().equals(null)){
                sportsSpecialAdvisory.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(sportsSpecialAdvisoryList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SportsSpecialAdvisory> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialAdvisoryIds) {
        return sportsSpecialAdvisoryMapper.selectBatchIds(sportsSpecialAdvisoryIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public SportsSpecialAdvisory mySelectById(Serializable sportsSpecialAdvisoryId) {
        return sportsSpecialAdvisoryMapper.selectById(sportsSpecialAdvisoryId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<SportsSpecialAdvisory> wrapper) {
        return sportsSpecialAdvisoryMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public SportsSpecialAdvisory mySelectOne(Wrapper<SportsSpecialAdvisory> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<SportsSpecialAdvisory> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SportsSpecialAdvisory sportsSpecialAdvisory, Wrapper<SportsSpecialAdvisory> wrapper) {
        return this.update(sportsSpecialAdvisory, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SportsSpecialAdvisory> sportsSpecialAdvisoryList) {
        return this.updateBatchById(sportsSpecialAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SportsSpecialAdvisory sportsSpecialAdvisory) {
        return this.updateById(sportsSpecialAdvisory);
    }
}
