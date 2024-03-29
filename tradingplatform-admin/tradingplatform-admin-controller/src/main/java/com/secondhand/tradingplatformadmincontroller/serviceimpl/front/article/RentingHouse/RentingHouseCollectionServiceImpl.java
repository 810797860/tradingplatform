package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouse;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseCollection;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse.RentingHouseCollectionMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse.RentingHouseMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseCollectionService;
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
 *   @description : RentingHouseCollection 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "rentingHouseCollection")
public class RentingHouseCollectionServiceImpl extends BaseServiceImpl<RentingHouseCollectionMapper, RentingHouseCollection> implements RentingHouseCollectionService {

    @Autowired
    private RentingHouseCollectionMapper rentingHouseCollectionMapper;

    @Autowired
    private RentingHouseMapper rentingHouseMapper;

    @Override
    @CacheEvict(cacheNames = {"rentingHouseCollection", "myCollection"}, allEntries = true)
    public Integer myFakeDeleteById(Long rentingHouseId, Long userId) {
        //这里传的是商品id，要把收藏id找出来
        RentingHouse rentingHouse = rentingHouseMapper.selectById(rentingHouseId);
        //找出那一条收藏记录
        RentingHouseCollection rentingHouseCollection = new RentingHouseCollection();
        rentingHouseCollection.setUserId(userId);
        rentingHouseCollection.setRentingId(rentingHouse.getId());
        rentingHouseCollection.setDeleted(false);
        rentingHouseCollection = rentingHouseCollectionMapper.selectOne(rentingHouseCollection);
        //假删除
        rentingHouseCollection.setDeleted(true);
        return rentingHouseCollectionMapper.updateById(rentingHouseCollection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> rentingHouseCollectionIds) {
        rentingHouseCollectionIds.forEach(rentingHouseCollectionId->{
            myFakeDeleteById(rentingHouseCollectionId, 48L);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long rentingHouseCollectionId) {
        return rentingHouseCollectionMapper.selectMapById(rentingHouseCollectionId);
    }

    @Override
    @CacheEvict(cacheNames = {"rentingHouseCollection", "myCollection"}, allEntries = true)
    public RentingHouseCollection myRentingHouseCollectionCreateUpdate(RentingHouseCollection rentingHouseCollection) {
        Long rentingHouseCollectionId = rentingHouseCollection.getId();
        if (rentingHouseCollectionId == null){
            rentingHouseCollection.setUuid(ToolUtil.getUUID());
            rentingHouseCollectionMapper.insert(rentingHouseCollection);
        } else {
            rentingHouseCollectionMapper.updateById(rentingHouseCollection);
        }
        return rentingHouseCollection;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseCollection rentingHouseCollection) {

        //判空
        rentingHouseCollection.setDeleted(false);
        Wrapper<RentingHouseCollection> wrapper = new EntityWrapper<>(rentingHouseCollection);
        //根据自动生成sql回显
        wrapper.setSqlSelect("c_business_renting_house_collection.id as id, c_business_renting_house_collection.created_by as created_by, c_business_renting_house_collection.deleted as deleted, c_business_renting_house_collection.description as description, c_business_renting_house_collection.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_renting_house_collection.user_id) ) AS user_id, c_business_renting_house_collection.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbrh.id, '\",\"title\":\"', cbrh.title, '\",\"cover\":\"', cbrh.cover, '\",\"price\":\"', cbrh.price, '\"}' ) FROM c_business_renting_house cbrh WHERE (cbrh.id = c_business_renting_house_collection.renting_id) ) AS renting_id, c_business_renting_house_collection.created_at as created_at");
        //遍历排序
        List<Sort> sorts = rentingHouseCollection.getSorts();
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
    public List<RentingHouseCollection> mySelectListWithMap(Map<String, Object> map) {
        return rentingHouseCollectionMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<RentingHouseCollection> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<RentingHouseCollection> mySelectList(Wrapper<RentingHouseCollection> wrapper) {
        return rentingHouseCollectionMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "'mySelectCollectionList : ' + #p0")
    public List<Object> mySelectCollectionList(Long userId) {
        Wrapper<RentingHouseCollection> wrapper = new EntityWrapper<>();
        //没被删除的
        wrapper.setSqlSelect("renting_id");
        wrapper.where("user_id = {0}", userId)
                .and("deleted = {0}", false);
        return rentingHouseCollectionMapper.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RentingHouseCollection rentingHouseCollection) {
        rentingHouseCollection.setUuid(ToolUtil.getUUID());
        return this.insert(rentingHouseCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RentingHouseCollection> rentingHouseCollectionList) {
        rentingHouseCollectionList.forEach(rentingHouseCollection -> {
            rentingHouseCollection.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(rentingHouseCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RentingHouseCollection rentingHouseCollection) {
        //没有uuid的话要加上去
        if (rentingHouseCollection.getUuid().equals(null)){
            rentingHouseCollection.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(rentingHouseCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RentingHouseCollection> rentingHouseCollectionList) {
        rentingHouseCollectionList.forEach(rentingHouseCollection -> {
            //没有uuid的话要加上去
            if (rentingHouseCollection.getUuid().equals(null)){
                rentingHouseCollection.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(rentingHouseCollectionList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RentingHouseCollection> mySelectBatchIds(Collection<? extends Serializable> rentingHouseCollectionIds) {
        return rentingHouseCollectionMapper.selectBatchIds(rentingHouseCollectionIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RentingHouseCollection mySelectById(Serializable rentingHouseCollectionId) {
        return rentingHouseCollectionMapper.selectById(rentingHouseCollectionId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<RentingHouseCollection> wrapper) {
        return rentingHouseCollectionMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public RentingHouseCollection mySelectOne(Wrapper<RentingHouseCollection> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<RentingHouseCollection> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RentingHouseCollection rentingHouseCollection, Wrapper<RentingHouseCollection> wrapper) {
        return this.update(rentingHouseCollection, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RentingHouseCollection> rentingHouseCollectionList) {
        return this.updateBatchById(rentingHouseCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RentingHouseCollection rentingHouseCollection) {
        return this.updateById(rentingHouseCollection);
    }
}
