package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.DigitalSquare;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareOrder;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare.DigitalSquareOrderMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareOrderService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.BusinessSelectItem;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.CustomizeStatus;
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
        //自定义sql回显
        wrapper.setSqlSelect("c_business_digital_square_order.id as id, c_business_digital_square_order.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbds.id, '\",\"title\":\"', cbds.title, '\",\"cover\":\"', cbds.cover, '\",\"price\":\"', cbds.price, '\"}' ) FROM c_business_digital_square cbds WHERE (cbds.id = c_business_digital_square_order.digital_id) ) AS digital_id, c_business_digital_square_order.price as price, c_business_digital_square_order.created_by as created_by, c_business_digital_square_order.quantity as quantity, c_business_digital_square_order.deleted as deleted, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_digital_square_order.order_status)) AS order_status, c_business_digital_square_order.description as description, c_business_digital_square_order.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_digital_square_order.user_id) ) AS user_id, c_business_digital_square_order.created_at as created_at");
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

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Float mySettlementByListId(List<Long> digitalSquareOrderLists, Float balance) {

        final Float[] tempBalance = {balance};

        //遍历结算
        digitalSquareOrderLists.forEach(digitalSquareOrderId -> {

            //先找出该订单
            DigitalSquareOrder digitalSquareOrder = this.mySelectById(digitalSquareOrderId);
            //扣掉余额
            tempBalance[0] = tempBalance[0] - digitalSquareOrder.getPrice() * digitalSquareOrder.getQuantity();
            if (tempBalance[0] >= 0) {
                //修改订单的状态
                digitalSquareOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_PAID);
            } else {
                try {
                    throw new CustomizeException(CustomizeStatus.DIGITAL_SQUARE_INSUFFICIENT_BALANCE, this.getClass());
                } catch (CustomizeException e) {
                    e.printStackTrace();
                }
            }
        });
        return tempBalance[0];
    }
}
