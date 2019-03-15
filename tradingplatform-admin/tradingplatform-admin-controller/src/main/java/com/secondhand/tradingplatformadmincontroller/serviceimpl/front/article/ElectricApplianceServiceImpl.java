package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricApplianceMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricApplianceService;
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
 *   @description : ElectricAppliance 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-15
 */

@Service
@CacheConfig(cacheNames = "electricAppliance")
public class ElectricApplianceServiceImpl extends BaseServiceImpl<ElectricApplianceMapper, ElectricAppliance> implements ElectricApplianceService {

    @Autowired
    private ElectricApplianceMapper electricApplianceMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long electricApplianceId) {
        ElectricAppliance electricAppliance = new ElectricAppliance();
        electricAppliance.setId(electricApplianceId);
        electricAppliance.setDeleted(true);
        return electricApplianceMapper.updateById(electricAppliance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> electricApplianceIds) {
        electricApplianceIds.forEach(electricApplianceId->{
            myFakeDeleteById(electricApplianceId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long electricApplianceId) {
        return electricApplianceMapper.selectMapById(electricApplianceId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ElectricAppliance myElectricApplianceCreateUpdate(ElectricAppliance electricAppliance) {
        Long electricApplianceId = electricAppliance.getId();
        if (electricApplianceId == null){
            electricAppliance.setUuid(ToolUtil.getUUID());
            electricApplianceMapper.insert(electricAppliance);
        } else {
            electricApplianceMapper.updateById(electricAppliance);
        }
        return electricAppliance;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricAppliance electricAppliance) {

        //判空
        electricAppliance.setDeleted(false);
        Wrapper<ElectricAppliance> wrapper = new EntityWrapper<>(electricAppliance);
        //遍历排序
        List<Sort> sorts = electricAppliance.getSorts();
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
    public List<ElectricAppliance> mySelectListWithMap(Map<String, Object> map) {
        return electricApplianceMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<ElectricAppliance> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<ElectricAppliance> mySelectList(Wrapper<ElectricAppliance> wrapper) {
        return electricApplianceMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(ElectricAppliance electricAppliance) {
        electricAppliance.setUuid(ToolUtil.getUUID());
        return this.insert(electricAppliance);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<ElectricAppliance> electricApplianceList) {
        electricApplianceList.forEach(electricAppliance -> {
            electricAppliance.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(electricApplianceList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(ElectricAppliance electricAppliance) {
        //没有uuid的话要加上去
        if (electricAppliance.getUuid().equals(null)){
            electricAppliance.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(electricAppliance);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<ElectricAppliance> electricApplianceList) {
        electricApplianceList.forEach(electricAppliance -> {
            //没有uuid的话要加上去
            if (electricAppliance.getUuid().equals(null)){
                electricAppliance.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(electricApplianceList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<ElectricAppliance> mySelectBatchIds(Collection<? extends Serializable> electricApplianceIds) {
        return electricApplianceMapper.selectBatchIds(electricApplianceIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public ElectricAppliance mySelectById(Serializable electricApplianceId) {
        return electricApplianceMapper.selectById(electricApplianceId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<ElectricAppliance> wrapper) {
        return electricApplianceMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public ElectricAppliance mySelectOne(Wrapper<ElectricAppliance> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<ElectricAppliance> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(ElectricAppliance electricAppliance, Wrapper<ElectricAppliance> wrapper) {
        return this.update(electricAppliance, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<ElectricAppliance> electricApplianceList) {
        return this.updateBatchById(electricApplianceList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(ElectricAppliance electricAppliance) {
        return this.updateById(electricAppliance);
    }
}
