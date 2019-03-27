package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouseOrder;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouseOrderMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouseOrderService;
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
 *   @description : RentingHouseOrder 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "rentingHouseOrder")
public class RentingHouseOrderServiceImpl extends BaseServiceImpl<RentingHouseOrderMapper, RentingHouseOrder> implements RentingHouseOrderService {

    @Autowired
    private RentingHouseOrderMapper rentingHouseOrderMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long rentingHouseOrderId) {
        RentingHouseOrder rentingHouseOrder = new RentingHouseOrder();
        rentingHouseOrder.setId(rentingHouseOrderId);
        rentingHouseOrder.setDeleted(true);
        return rentingHouseOrderMapper.updateById(rentingHouseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> rentingHouseOrderIds) {
        rentingHouseOrderIds.forEach(rentingHouseOrderId->{
            myFakeDeleteById(rentingHouseOrderId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long rentingHouseOrderId) {
        return rentingHouseOrderMapper.selectMapById(rentingHouseOrderId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RentingHouseOrder myRentingHouseOrderCreateUpdate(RentingHouseOrder rentingHouseOrder) {
        Long rentingHouseOrderId = rentingHouseOrder.getId();
        if (rentingHouseOrderId == null){
            rentingHouseOrder.setUuid(ToolUtil.getUUID());
            rentingHouseOrderMapper.insert(rentingHouseOrder);
        } else {
            rentingHouseOrderMapper.updateById(rentingHouseOrder);
        }
        return rentingHouseOrder;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseOrder rentingHouseOrder) {

        //判空
        rentingHouseOrder.setDeleted(false);
        Wrapper<RentingHouseOrder> wrapper = new EntityWrapper<>(rentingHouseOrder);
        //遍历排序
        List<Sort> sorts = rentingHouseOrder.getSorts();
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
    public List<RentingHouseOrder> mySelectListWithMap(Map<String, Object> map) {
        return rentingHouseOrderMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<RentingHouseOrder> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<RentingHouseOrder> mySelectList(Wrapper<RentingHouseOrder> wrapper) {
        return rentingHouseOrderMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RentingHouseOrder rentingHouseOrder) {
        rentingHouseOrder.setUuid(ToolUtil.getUUID());
        return this.insert(rentingHouseOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RentingHouseOrder> rentingHouseOrderList) {
        rentingHouseOrderList.forEach(rentingHouseOrder -> {
            rentingHouseOrder.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(rentingHouseOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RentingHouseOrder rentingHouseOrder) {
        //没有uuid的话要加上去
        if (rentingHouseOrder.getUuid().equals(null)){
            rentingHouseOrder.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(rentingHouseOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RentingHouseOrder> rentingHouseOrderList) {
        rentingHouseOrderList.forEach(rentingHouseOrder -> {
            //没有uuid的话要加上去
            if (rentingHouseOrder.getUuid().equals(null)){
                rentingHouseOrder.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(rentingHouseOrderList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RentingHouseOrder> mySelectBatchIds(Collection<? extends Serializable> rentingHouseOrderIds) {
        return rentingHouseOrderMapper.selectBatchIds(rentingHouseOrderIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RentingHouseOrder mySelectById(Serializable rentingHouseOrderId) {
        return rentingHouseOrderMapper.selectById(rentingHouseOrderId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<RentingHouseOrder> wrapper) {
        return rentingHouseOrderMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public RentingHouseOrder mySelectOne(Wrapper<RentingHouseOrder> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<RentingHouseOrder> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RentingHouseOrder rentingHouseOrder, Wrapper<RentingHouseOrder> wrapper) {
        return this.update(rentingHouseOrder, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RentingHouseOrder> rentingHouseOrderList) {
        return this.updateBatchById(rentingHouseOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RentingHouseOrder rentingHouseOrder) {
        return this.updateById(rentingHouseOrder);
    }
}
