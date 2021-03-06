package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.RentingHouse;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouse;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseOrder;
import com.secondhand.tradingplatformadminmapper.mapper.admin.shiro.UserMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse.RentingHouseMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse.RentingHouseOrderMapper;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseOrderService;
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

    @Autowired
    private RentingHouseMapper rentingHouseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShortMessageService shortMessageService;

    @Override
    @CacheEvict(cacheNames = {"rentingHouseOrder", "shoppingCart"}, allEntries = true)
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
    @CacheEvict(cacheNames = {"rentingHouseOrder", "shoppingCart"}, allEntries = true)
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
        //自动生成sql回显
        wrapper.setSqlSelect("c_business_renting_house_order.id as id, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_renting_house_order.user_id) ) AS user_id, c_business_renting_house_order.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbrh.id, '\",\"title\":\"', cbrh.title, '\",\"cover\":\"', cbrh.cover, '\",\"price\":\"', cbrh.price, '\"}' ) FROM c_business_renting_house cbrh WHERE (cbrh.id = c_business_renting_house_order.renting_id) ) AS renting_id, c_business_renting_house_order.price as price, c_business_renting_house_order.created_by as created_by, c_business_renting_house_order.quantity as quantity, c_business_renting_house_order.deleted as deleted, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_renting_house_order.order_status)) AS order_status, c_business_renting_house_order.description as description, c_business_renting_house_order.updated_at as updated_at, c_business_renting_house_order.created_at as created_at");
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

    @Override
    @Cacheable(key = "'mySettlementByListId' + #p0 + #p1")
    @Transactional(rollbackFor = Exception.class)
    public Float mySettlementByListId(List<Long> rentingHouseOrderLists, Float balance) {

        final Float[] tempBalance = {balance};

        //遍历结算
        rentingHouseOrderLists.forEach(rentingHouseOrderId -> {

            //先找出该订单
            RentingHouseOrder rentingHouseOrder = this.mySelectById(rentingHouseOrderId);
            //扣掉余额
            tempBalance[0] = tempBalance[0] - rentingHouseOrder.getPrice() * rentingHouseOrder.getQuantity();
            if (tempBalance[0] >= 0) {
                //修改订单的状态
                rentingHouseOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_PAID);
            } else {
                try {
                    throw new CustomizeException(CustomizeStatus.RENTING_HOUSE_INSUFFICIENT_BALANCE, this.getClass());
                } catch (CustomizeException e) {
                    e.printStackTrace();
                }
            }
        });
        return tempBalance[0];
    }

    @Override
    @Cacheable(key = "'myNotifyByListId' + #p0")
    @Transactional(rollbackFor = Exception.class)
    public void myNotifyByListId(List<Long> rentingHouseOrderLists) {

        //遍历发短信
        rentingHouseOrderLists.forEach(rentingHouseOrderId -> {

            //先找卖家的电话号码（商品id->userId->phone）
            //先找该条订单的信息
            RentingHouseOrder rentingHouseOrder = this.mySelectById(rentingHouseOrderId);
            //找该条商品的信息
            RentingHouse rentingHouse = rentingHouseMapper.selectById(rentingHouseOrder.getRentingId());
            //找phone
            User user = userMapper.selectById(rentingHouse.getUserId());
            String phone = user.getPhone();
            //如果该用户有验证手机号码
            if (!ToolUtil.strIsEmpty(phone)){
                try {
                    shortMessageService.notifyPurchaseSuccess(phone);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
