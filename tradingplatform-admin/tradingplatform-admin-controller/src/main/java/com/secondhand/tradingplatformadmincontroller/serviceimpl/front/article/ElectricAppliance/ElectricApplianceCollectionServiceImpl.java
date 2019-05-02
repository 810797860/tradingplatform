package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceCollection;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricAppliance.ElectricApplianceCollectionMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceCollectionService;
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
 *   @description : ElectricApplianceCollection 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "electricApplianceCollection")
public class ElectricApplianceCollectionServiceImpl extends BaseServiceImpl<ElectricApplianceCollectionMapper, ElectricApplianceCollection> implements ElectricApplianceCollectionService {

    @Autowired
    private ElectricApplianceCollectionMapper electricApplianceCollectionMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long electricApplianceCollectionId) {
        ElectricApplianceCollection electricApplianceCollection = new ElectricApplianceCollection();
        electricApplianceCollection.setId(electricApplianceCollectionId);
        electricApplianceCollection.setDeleted(true);
        return electricApplianceCollectionMapper.updateById(electricApplianceCollection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> electricApplianceCollectionIds) {
        electricApplianceCollectionIds.forEach(electricApplianceCollectionId->{
            myFakeDeleteById(electricApplianceCollectionId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long electricApplianceCollectionId) {
        return electricApplianceCollectionMapper.selectMapById(electricApplianceCollectionId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ElectricApplianceCollection myElectricApplianceCollectionCreateUpdate(ElectricApplianceCollection electricApplianceCollection) {
        Long electricApplianceCollectionId = electricApplianceCollection.getId();
        if (electricApplianceCollectionId == null){
            electricApplianceCollection.setUuid(ToolUtil.getUUID());
            electricApplianceCollectionMapper.insert(electricApplianceCollection);
        } else {
            electricApplianceCollectionMapper.updateById(electricApplianceCollection);
        }
        return electricApplianceCollection;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceCollection electricApplianceCollection) {

        //判空
        electricApplianceCollection.setDeleted(false);
        Wrapper<ElectricApplianceCollection> wrapper = new EntityWrapper<>(electricApplianceCollection);
        //自定义sql回显
        wrapper.setSqlSelect("c_business_electric_appliance_collection.id as id, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_electric_appliance_collection.user_id) ) AS user_id, c_business_electric_appliance_collection.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbea.id, '\",\"title\":\"', cbea.title, '\",\"cover\":\"', cbea.cover, '\",\"price\":\"', cbea.price, '\"}' ) FROM c_business_electric_appliance cbea WHERE (cbea.id = c_business_electric_appliance_collection.electric_id) ) AS electric_id, c_business_electric_appliance_collection.created_by as created_by, c_business_electric_appliance_collection.deleted as deleted, c_business_electric_appliance_collection.description as description, c_business_electric_appliance_collection.updated_at as updated_at, c_business_electric_appliance_collection.created_at as created_at");
        //遍历排序
        List<Sort> sorts = electricApplianceCollection.getSorts();
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
    public List<ElectricApplianceCollection> mySelectListWithMap(Map<String, Object> map) {
        return electricApplianceCollectionMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<ElectricApplianceCollection> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<ElectricApplianceCollection> mySelectList(Wrapper<ElectricApplianceCollection> wrapper) {
        return electricApplianceCollectionMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "'mySelectCollectionList : ' + #p0")
    public List<Object> mySelectCollectionList(Long userId) {
        Wrapper<ElectricApplianceCollection> wrapper = new EntityWrapper<>();
        //没被删除的
        wrapper.setSqlSelect("electric_id");
        wrapper.where("user_id = {0}", userId)
                .and("deleted = {0}", false);
        return electricApplianceCollectionMapper.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(ElectricApplianceCollection electricApplianceCollection) {
        electricApplianceCollection.setUuid(ToolUtil.getUUID());
        return this.insert(electricApplianceCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<ElectricApplianceCollection> electricApplianceCollectionList) {
        electricApplianceCollectionList.forEach(electricApplianceCollection -> {
            electricApplianceCollection.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(electricApplianceCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(ElectricApplianceCollection electricApplianceCollection) {
        //没有uuid的话要加上去
        if (electricApplianceCollection.getUuid().equals(null)){
            electricApplianceCollection.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(electricApplianceCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<ElectricApplianceCollection> electricApplianceCollectionList) {
        electricApplianceCollectionList.forEach(electricApplianceCollection -> {
            //没有uuid的话要加上去
            if (electricApplianceCollection.getUuid().equals(null)){
                electricApplianceCollection.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(electricApplianceCollectionList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<ElectricApplianceCollection> mySelectBatchIds(Collection<? extends Serializable> electricApplianceCollectionIds) {
        return electricApplianceCollectionMapper.selectBatchIds(electricApplianceCollectionIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public ElectricApplianceCollection mySelectById(Serializable electricApplianceCollectionId) {
        return electricApplianceCollectionMapper.selectById(electricApplianceCollectionId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<ElectricApplianceCollection> wrapper) {
        return electricApplianceCollectionMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public ElectricApplianceCollection mySelectOne(Wrapper<ElectricApplianceCollection> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<ElectricApplianceCollection> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(ElectricApplianceCollection electricApplianceCollection, Wrapper<ElectricApplianceCollection> wrapper) {
        return this.update(electricApplianceCollection, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<ElectricApplianceCollection> electricApplianceCollectionList) {
        return this.updateBatchById(electricApplianceCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(ElectricApplianceCollection electricApplianceCollection) {
        return this.updateById(electricApplianceCollection);
    }
}
