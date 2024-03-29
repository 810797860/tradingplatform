package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseAdvisory;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse.RentingHouseAdvisoryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseAdvisoryService;
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
 *   @description : RentingHouseAdvisory 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "rentingHouseAdvisory")
public class RentingHouseAdvisoryServiceImpl extends BaseServiceImpl<RentingHouseAdvisoryMapper, RentingHouseAdvisory> implements RentingHouseAdvisoryService {

    @Autowired
    private RentingHouseAdvisoryMapper rentingHouseAdvisoryMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long rentingHouseAdvisoryId) {
        RentingHouseAdvisory rentingHouseAdvisory = new RentingHouseAdvisory();
        rentingHouseAdvisory.setId(rentingHouseAdvisoryId);
        rentingHouseAdvisory.setDeleted(true);
        return rentingHouseAdvisoryMapper.updateById(rentingHouseAdvisory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> rentingHouseAdvisoryIds) {
        rentingHouseAdvisoryIds.forEach(rentingHouseAdvisoryId->{
            myFakeDeleteById(rentingHouseAdvisoryId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long rentingHouseAdvisoryId) {
        return rentingHouseAdvisoryMapper.selectMapById(rentingHouseAdvisoryId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RentingHouseAdvisory myRentingHouseAdvisoryCreateUpdate(RentingHouseAdvisory rentingHouseAdvisory) {
        Long rentingHouseAdvisoryId = rentingHouseAdvisory.getId();
        if (rentingHouseAdvisoryId == null){
            rentingHouseAdvisory.setUuid(ToolUtil.getUUID());
            rentingHouseAdvisoryMapper.insert(rentingHouseAdvisory);
        } else {
            rentingHouseAdvisoryMapper.updateById(rentingHouseAdvisory);
        }
        return rentingHouseAdvisory;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseAdvisory rentingHouseAdvisory) {

        //判空
        rentingHouseAdvisory.setDeleted(false);
        Wrapper<RentingHouseAdvisory> wrapper = new EntityWrapper<>(rentingHouseAdvisory);
        //根据自动生成sql回显
        wrapper.setSqlSelect("c_business_renting_house_advisory.id as id, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_renting_house_advisory.reply_id) ) AS reply_id, c_business_renting_house_advisory.created_by as created_by, ( SELECT concat( '{\"id\":\"', cbrh.id, '\",\"title\":\"', cbrh.title, '\",\"cover\":\"', cbrh.cover, '\",\"price\":\"', cbrh.price, '\"}' ) FROM c_business_renting_house cbrh WHERE (cbrh.id = c_business_renting_house_advisory.renting_id) ) AS renting_id, c_business_renting_house_advisory.deleted as deleted, c_business_renting_house_advisory.content as content, c_business_renting_house_advisory.description as description, c_business_renting_house_advisory.updated_at as updated_at, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_renting_house_advisory.back_check_status)) AS back_check_status, c_business_renting_house_advisory.pid as pid, c_business_renting_house_advisory.updated_by as updated_by, c_business_renting_house_advisory.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_renting_house_advisory.user_id) ) AS user_id, c_business_renting_house_advisory.created_at as created_at");
        //遍历排序
        List<Sort> sorts = rentingHouseAdvisory.getSorts();
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
    public List<RentingHouseAdvisory> mySelectListWithMap(Map<String, Object> map) {
        return rentingHouseAdvisoryMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<RentingHouseAdvisory> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<RentingHouseAdvisory> mySelectList(Wrapper<RentingHouseAdvisory> wrapper) {
        return rentingHouseAdvisoryMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RentingHouseAdvisory rentingHouseAdvisory) {
        rentingHouseAdvisory.setUuid(ToolUtil.getUUID());
        return this.insert(rentingHouseAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RentingHouseAdvisory> rentingHouseAdvisoryList) {
        rentingHouseAdvisoryList.forEach(rentingHouseAdvisory -> {
            rentingHouseAdvisory.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(rentingHouseAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RentingHouseAdvisory rentingHouseAdvisory) {
        //没有uuid的话要加上去
        if (rentingHouseAdvisory.getUuid().equals(null)){
            rentingHouseAdvisory.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(rentingHouseAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RentingHouseAdvisory> rentingHouseAdvisoryList) {
        rentingHouseAdvisoryList.forEach(rentingHouseAdvisory -> {
            //没有uuid的话要加上去
            if (rentingHouseAdvisory.getUuid().equals(null)){
                rentingHouseAdvisory.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(rentingHouseAdvisoryList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RentingHouseAdvisory> mySelectBatchIds(Collection<? extends Serializable> rentingHouseAdvisoryIds) {
        return rentingHouseAdvisoryMapper.selectBatchIds(rentingHouseAdvisoryIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RentingHouseAdvisory mySelectById(Serializable rentingHouseAdvisoryId) {
        return rentingHouseAdvisoryMapper.selectById(rentingHouseAdvisoryId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<RentingHouseAdvisory> wrapper) {
        return rentingHouseAdvisoryMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public RentingHouseAdvisory mySelectOne(Wrapper<RentingHouseAdvisory> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<RentingHouseAdvisory> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RentingHouseAdvisory rentingHouseAdvisory, Wrapper<RentingHouseAdvisory> wrapper) {
        return this.update(rentingHouseAdvisory, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RentingHouseAdvisory> rentingHouseAdvisoryList) {
        return this.updateBatchById(rentingHouseAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RentingHouseAdvisory rentingHouseAdvisory) {
        return this.updateById(rentingHouseAdvisory);
    }
}
