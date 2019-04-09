package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceAdvisory;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricAppliance.ElectricApplianceAdvisoryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceAdvisoryService;
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
 *   @description : ElectricApplianceAdvisory 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "electricApplianceAdvisory")
public class ElectricApplianceAdvisoryServiceImpl extends BaseServiceImpl<ElectricApplianceAdvisoryMapper, ElectricApplianceAdvisory> implements ElectricApplianceAdvisoryService {

    @Autowired
    private ElectricApplianceAdvisoryMapper electricApplianceAdvisoryMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long electricApplianceAdvisoryId) {
        ElectricApplianceAdvisory electricApplianceAdvisory = new ElectricApplianceAdvisory();
        electricApplianceAdvisory.setId(electricApplianceAdvisoryId);
        electricApplianceAdvisory.setDeleted(true);
        return electricApplianceAdvisoryMapper.updateById(electricApplianceAdvisory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> electricApplianceAdvisoryIds) {
        electricApplianceAdvisoryIds.forEach(electricApplianceAdvisoryId->{
            myFakeDeleteById(electricApplianceAdvisoryId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long electricApplianceAdvisoryId) {
        return electricApplianceAdvisoryMapper.selectMapById(electricApplianceAdvisoryId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ElectricApplianceAdvisory myElectricApplianceAdvisoryCreateUpdate(ElectricApplianceAdvisory electricApplianceAdvisory) {
        Long electricApplianceAdvisoryId = electricApplianceAdvisory.getId();
        if (electricApplianceAdvisoryId == null){
            electricApplianceAdvisory.setUuid(ToolUtil.getUUID());
            electricApplianceAdvisoryMapper.insert(electricApplianceAdvisory);
        } else {
            electricApplianceAdvisoryMapper.updateById(electricApplianceAdvisory);
        }
        return electricApplianceAdvisory;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceAdvisory electricApplianceAdvisory) {

        //判空
        electricApplianceAdvisory.setDeleted(false);
        Wrapper<ElectricApplianceAdvisory> wrapper = new EntityWrapper<>(electricApplianceAdvisory);
        //遍历排序
        List<Sort> sorts = electricApplianceAdvisory.getSorts();
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
    public List<ElectricApplianceAdvisory> mySelectListWithMap(Map<String, Object> map) {
        return electricApplianceAdvisoryMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<ElectricApplianceAdvisory> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<ElectricApplianceAdvisory> mySelectList(Wrapper<ElectricApplianceAdvisory> wrapper) {
        return electricApplianceAdvisoryMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(ElectricApplianceAdvisory electricApplianceAdvisory) {
        electricApplianceAdvisory.setUuid(ToolUtil.getUUID());
        return this.insert(electricApplianceAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<ElectricApplianceAdvisory> electricApplianceAdvisoryList) {
        electricApplianceAdvisoryList.forEach(electricApplianceAdvisory -> {
            electricApplianceAdvisory.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(electricApplianceAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(ElectricApplianceAdvisory electricApplianceAdvisory) {
        //没有uuid的话要加上去
        if (electricApplianceAdvisory.getUuid().equals(null)){
            electricApplianceAdvisory.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(electricApplianceAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<ElectricApplianceAdvisory> electricApplianceAdvisoryList) {
        electricApplianceAdvisoryList.forEach(electricApplianceAdvisory -> {
            //没有uuid的话要加上去
            if (electricApplianceAdvisory.getUuid().equals(null)){
                electricApplianceAdvisory.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(electricApplianceAdvisoryList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<ElectricApplianceAdvisory> mySelectBatchIds(Collection<? extends Serializable> electricApplianceAdvisoryIds) {
        return electricApplianceAdvisoryMapper.selectBatchIds(electricApplianceAdvisoryIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public ElectricApplianceAdvisory mySelectById(Serializable electricApplianceAdvisoryId) {
        return electricApplianceAdvisoryMapper.selectById(electricApplianceAdvisoryId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<ElectricApplianceAdvisory> wrapper) {
        return electricApplianceAdvisoryMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public ElectricApplianceAdvisory mySelectOne(Wrapper<ElectricApplianceAdvisory> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<ElectricApplianceAdvisory> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(ElectricApplianceAdvisory electricApplianceAdvisory, Wrapper<ElectricApplianceAdvisory> wrapper) {
        return this.update(electricApplianceAdvisory, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<ElectricApplianceAdvisory> electricApplianceAdvisoryList) {
        return this.updateBatchById(electricApplianceAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(ElectricApplianceAdvisory electricApplianceAdvisory) {
        return this.updateById(electricApplianceAdvisory);
    }
}
