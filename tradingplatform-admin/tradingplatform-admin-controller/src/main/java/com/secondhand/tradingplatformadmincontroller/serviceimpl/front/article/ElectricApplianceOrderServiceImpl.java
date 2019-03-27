package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricApplianceOrder;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricApplianceOrderMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricApplianceOrderService;
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
 *   @description : ElectricApplianceOrder 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "electricApplianceOrder")
public class ElectricApplianceOrderServiceImpl extends BaseServiceImpl<ElectricApplianceOrderMapper, ElectricApplianceOrder> implements ElectricApplianceOrderService {

    @Autowired
    private ElectricApplianceOrderMapper electricApplianceOrderMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long electricApplianceOrderId) {
        ElectricApplianceOrder electricApplianceOrder = new ElectricApplianceOrder();
        electricApplianceOrder.setId(electricApplianceOrderId);
        electricApplianceOrder.setDeleted(true);
        return electricApplianceOrderMapper.updateById(electricApplianceOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> electricApplianceOrderIds) {
        electricApplianceOrderIds.forEach(electricApplianceOrderId->{
            myFakeDeleteById(electricApplianceOrderId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long electricApplianceOrderId) {
        return electricApplianceOrderMapper.selectMapById(electricApplianceOrderId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ElectricApplianceOrder myElectricApplianceOrderCreateUpdate(ElectricApplianceOrder electricApplianceOrder) {
        Long electricApplianceOrderId = electricApplianceOrder.getId();
        if (electricApplianceOrderId == null){
            electricApplianceOrder.setUuid(ToolUtil.getUUID());
            electricApplianceOrderMapper.insert(electricApplianceOrder);
        } else {
            electricApplianceOrderMapper.updateById(electricApplianceOrder);
        }
        return electricApplianceOrder;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceOrder electricApplianceOrder) {

        //判空
        electricApplianceOrder.setDeleted(false);
        Wrapper<ElectricApplianceOrder> wrapper = new EntityWrapper<>(electricApplianceOrder);
        //遍历排序
        List<Sort> sorts = electricApplianceOrder.getSorts();
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
    public List<ElectricApplianceOrder> mySelectListWithMap(Map<String, Object> map) {
        return electricApplianceOrderMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<ElectricApplianceOrder> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<ElectricApplianceOrder> mySelectList(Wrapper<ElectricApplianceOrder> wrapper) {
        return electricApplianceOrderMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(ElectricApplianceOrder electricApplianceOrder) {
        electricApplianceOrder.setUuid(ToolUtil.getUUID());
        return this.insert(electricApplianceOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<ElectricApplianceOrder> electricApplianceOrderList) {
        electricApplianceOrderList.forEach(electricApplianceOrder -> {
            electricApplianceOrder.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(electricApplianceOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(ElectricApplianceOrder electricApplianceOrder) {
        //没有uuid的话要加上去
        if (electricApplianceOrder.getUuid().equals(null)){
            electricApplianceOrder.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(electricApplianceOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<ElectricApplianceOrder> electricApplianceOrderList) {
        electricApplianceOrderList.forEach(electricApplianceOrder -> {
            //没有uuid的话要加上去
            if (electricApplianceOrder.getUuid().equals(null)){
                electricApplianceOrder.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(electricApplianceOrderList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<ElectricApplianceOrder> mySelectBatchIds(Collection<? extends Serializable> electricApplianceOrderIds) {
        return electricApplianceOrderMapper.selectBatchIds(electricApplianceOrderIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public ElectricApplianceOrder mySelectById(Serializable electricApplianceOrderId) {
        return electricApplianceOrderMapper.selectById(electricApplianceOrderId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<ElectricApplianceOrder> wrapper) {
        return electricApplianceOrderMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public ElectricApplianceOrder mySelectOne(Wrapper<ElectricApplianceOrder> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<ElectricApplianceOrder> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(ElectricApplianceOrder electricApplianceOrder, Wrapper<ElectricApplianceOrder> wrapper) {
        return this.update(electricApplianceOrder, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<ElectricApplianceOrder> electricApplianceOrderList) {
        return this.updateBatchById(electricApplianceOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(ElectricApplianceOrder electricApplianceOrder) {
        return this.updateById(electricApplianceOrder);
    }
}
