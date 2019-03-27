package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquareOrder;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquareOrderMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquareOrderService;
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
 *   @description : DigitalSquareOrder 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "digitalSquareOrder")
public class DigitalSquareOrderServiceImpl extends BaseServiceImpl<DigitalSquareOrderMapper, DigitalSquareOrder> implements DigitalSquareOrderService {

    @Autowired
    private DigitalSquareOrderMapper digitalSquareOrderMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long digitalSquareOrderId) {
        DigitalSquareOrder digitalSquareOrder = new DigitalSquareOrder();
        digitalSquareOrder.setId(digitalSquareOrderId);
        digitalSquareOrder.setDeleted(true);
        return digitalSquareOrderMapper.updateById(digitalSquareOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> digitalSquareOrderIds) {
        digitalSquareOrderIds.forEach(digitalSquareOrderId->{
            myFakeDeleteById(digitalSquareOrderId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long digitalSquareOrderId) {
        return digitalSquareOrderMapper.selectMapById(digitalSquareOrderId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public DigitalSquareOrder myDigitalSquareOrderCreateUpdate(DigitalSquareOrder digitalSquareOrder) {
        Long digitalSquareOrderId = digitalSquareOrder.getId();
        if (digitalSquareOrderId == null){
            digitalSquareOrder.setUuid(ToolUtil.getUUID());
            digitalSquareOrderMapper.insert(digitalSquareOrder);
        } else {
            digitalSquareOrderMapper.updateById(digitalSquareOrder);
        }
        return digitalSquareOrder;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareOrder digitalSquareOrder) {

        //判空
        digitalSquareOrder.setDeleted(false);
        Wrapper<DigitalSquareOrder> wrapper = new EntityWrapper<>(digitalSquareOrder);
        //遍历排序
        List<Sort> sorts = digitalSquareOrder.getSorts();
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
    public List<DigitalSquareOrder> mySelectListWithMap(Map<String, Object> map) {
        return digitalSquareOrderMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<DigitalSquareOrder> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<DigitalSquareOrder> mySelectList(Wrapper<DigitalSquareOrder> wrapper) {
        return digitalSquareOrderMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(DigitalSquareOrder digitalSquareOrder) {
        digitalSquareOrder.setUuid(ToolUtil.getUUID());
        return this.insert(digitalSquareOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<DigitalSquareOrder> digitalSquareOrderList) {
        digitalSquareOrderList.forEach(digitalSquareOrder -> {
            digitalSquareOrder.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(digitalSquareOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(DigitalSquareOrder digitalSquareOrder) {
        //没有uuid的话要加上去
        if (digitalSquareOrder.getUuid().equals(null)){
            digitalSquareOrder.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(digitalSquareOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<DigitalSquareOrder> digitalSquareOrderList) {
        digitalSquareOrderList.forEach(digitalSquareOrder -> {
            //没有uuid的话要加上去
            if (digitalSquareOrder.getUuid().equals(null)){
                digitalSquareOrder.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(digitalSquareOrderList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<DigitalSquareOrder> mySelectBatchIds(Collection<? extends Serializable> digitalSquareOrderIds) {
        return digitalSquareOrderMapper.selectBatchIds(digitalSquareOrderIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public DigitalSquareOrder mySelectById(Serializable digitalSquareOrderId) {
        return digitalSquareOrderMapper.selectById(digitalSquareOrderId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<DigitalSquareOrder> wrapper) {
        return digitalSquareOrderMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public DigitalSquareOrder mySelectOne(Wrapper<DigitalSquareOrder> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<DigitalSquareOrder> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(DigitalSquareOrder digitalSquareOrder, Wrapper<DigitalSquareOrder> wrapper) {
        return this.update(digitalSquareOrder, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<DigitalSquareOrder> digitalSquareOrderList) {
        return this.updateBatchById(digitalSquareOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(DigitalSquareOrder digitalSquareOrder) {
        return this.updateById(digitalSquareOrder);
    }
}
