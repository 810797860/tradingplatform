package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecialOrder;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecialOrderMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecialOrderService;
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
 *   @description : SportsSpecialOrder 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "sportsSpecialOrder")
public class SportsSpecialOrderServiceImpl extends BaseServiceImpl<SportsSpecialOrderMapper, SportsSpecialOrder> implements SportsSpecialOrderService {

    @Autowired
    private SportsSpecialOrderMapper sportsSpecialOrderMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long sportsSpecialOrderId) {
        SportsSpecialOrder sportsSpecialOrder = new SportsSpecialOrder();
        sportsSpecialOrder.setId(sportsSpecialOrderId);
        sportsSpecialOrder.setDeleted(true);
        return sportsSpecialOrderMapper.updateById(sportsSpecialOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> sportsSpecialOrderIds) {
        sportsSpecialOrderIds.forEach(sportsSpecialOrderId->{
            myFakeDeleteById(sportsSpecialOrderId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long sportsSpecialOrderId) {
        return sportsSpecialOrderMapper.selectMapById(sportsSpecialOrderId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SportsSpecialOrder mySportsSpecialOrderCreateUpdate(SportsSpecialOrder sportsSpecialOrder) {
        Long sportsSpecialOrderId = sportsSpecialOrder.getId();
        if (sportsSpecialOrderId == null){
            sportsSpecialOrder.setUuid(ToolUtil.getUUID());
            sportsSpecialOrderMapper.insert(sportsSpecialOrder);
        } else {
            sportsSpecialOrderMapper.updateById(sportsSpecialOrder);
        }
        return sportsSpecialOrder;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialOrder sportsSpecialOrder) {

        //判空
        sportsSpecialOrder.setDeleted(false);
        Wrapper<SportsSpecialOrder> wrapper = new EntityWrapper<>(sportsSpecialOrder);
        //遍历排序
        List<Sort> sorts = sportsSpecialOrder.getSorts();
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
    public List<SportsSpecialOrder> mySelectListWithMap(Map<String, Object> map) {
        return sportsSpecialOrderMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<SportsSpecialOrder> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<SportsSpecialOrder> mySelectList(Wrapper<SportsSpecialOrder> wrapper) {
        return sportsSpecialOrderMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SportsSpecialOrder sportsSpecialOrder) {
        sportsSpecialOrder.setUuid(ToolUtil.getUUID());
        return this.insert(sportsSpecialOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SportsSpecialOrder> sportsSpecialOrderList) {
        sportsSpecialOrderList.forEach(sportsSpecialOrder -> {
            sportsSpecialOrder.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(sportsSpecialOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SportsSpecialOrder sportsSpecialOrder) {
        //没有uuid的话要加上去
        if (sportsSpecialOrder.getUuid().equals(null)){
            sportsSpecialOrder.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(sportsSpecialOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SportsSpecialOrder> sportsSpecialOrderList) {
        sportsSpecialOrderList.forEach(sportsSpecialOrder -> {
            //没有uuid的话要加上去
            if (sportsSpecialOrder.getUuid().equals(null)){
                sportsSpecialOrder.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(sportsSpecialOrderList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SportsSpecialOrder> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialOrderIds) {
        return sportsSpecialOrderMapper.selectBatchIds(sportsSpecialOrderIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public SportsSpecialOrder mySelectById(Serializable sportsSpecialOrderId) {
        return sportsSpecialOrderMapper.selectById(sportsSpecialOrderId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<SportsSpecialOrder> wrapper) {
        return sportsSpecialOrderMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public SportsSpecialOrder mySelectOne(Wrapper<SportsSpecialOrder> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<SportsSpecialOrder> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SportsSpecialOrder sportsSpecialOrder, Wrapper<SportsSpecialOrder> wrapper) {
        return this.update(sportsSpecialOrder, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SportsSpecialOrder> sportsSpecialOrderList) {
        return this.updateBatchById(sportsSpecialOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SportsSpecialOrder sportsSpecialOrder) {
        return this.updateById(sportsSpecialOrder);
    }
}
