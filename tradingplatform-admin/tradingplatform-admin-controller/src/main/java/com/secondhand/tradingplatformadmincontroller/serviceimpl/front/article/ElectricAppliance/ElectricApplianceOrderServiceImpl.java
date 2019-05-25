package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.ElectricAppliance;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricAppliance;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceOrder;
import com.secondhand.tradingplatformadminmapper.mapper.admin.shiro.UserMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricAppliance.ElectricApplianceMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricAppliance.ElectricApplianceOrderMapper;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceOrderService;
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

    @Autowired
    private ElectricApplianceMapper electricApplianceMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShortMessageService shortMessageService;

    @Override
    @CacheEvict(cacheNames = {"electricApplianceOrder", "shoppingCart"}, allEntries = true)
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
    @CacheEvict(cacheNames = {"electricApplianceOrder", "shoppingCart"}, allEntries = true)
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
        //自定义sql回显
        wrapper.setSqlSelect("c_business_electric_appliance_order.id as id, c_business_electric_appliance_order.quantity as quantity, c_business_electric_appliance_order.deleted as deleted, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_electric_appliance_order.order_status)) AS order_status, c_business_electric_appliance_order.description as description, c_business_electric_appliance_order.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_electric_appliance_order.user_id) ) AS user_id, c_business_electric_appliance_order.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbea.id, '\",\"title\":\"', cbea.title, '\",\"cover\":\"', cbea.cover, '\",\"price\":\"', cbea.price, '\"}' ) FROM c_business_electric_appliance cbea WHERE (cbea.id = c_business_electric_appliance_order.electric_id) ) AS electric_id, c_business_electric_appliance_order.price as price, c_business_electric_appliance_order.created_by as created_by, c_business_electric_appliance_order.created_at as created_at");
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

    @Override
    @Cacheable(key = "'mySettlementByListId:' + #p0 + #p1")
    @Transactional(rollbackFor = Exception.class)
    public Float mySettlementByListId(List<Long> electricApplianceOrderLists, Float balance) {

        final Float[] tempBalance = {balance};

        //遍历结算
        electricApplianceOrderLists.forEach(electricApplianceOrderId -> {

            //先找出该订单
            ElectricApplianceOrder electricApplianceOrder = this.mySelectById(electricApplianceOrderId);
            //扣掉余额
            tempBalance[0] = tempBalance[0] - electricApplianceOrder.getPrice() * electricApplianceOrder.getQuantity();
            if (tempBalance[0] >= 0) {
                //修改订单的状态
                electricApplianceOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_PAID);
            } else {
                try {
                    throw new CustomizeException(CustomizeStatus.ELECTRIC_APPLIANCE_INSUFFICIENT_BALANCE, this.getClass());
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
    public void myNotifyByListId(List<Long> electricApplianceOrderLists) {

        //遍历发短信
        electricApplianceOrderLists.forEach(electricApplianceOrderId -> {

            //先找卖家的电话号码（商品id->userId->phone）
            //先找该条订单的信息
            ElectricApplianceOrder electricApplianceOrder = this.mySelectById(electricApplianceOrderId);
            //找该条商品的信息
            ElectricAppliance electricAppliance = electricApplianceMapper.selectById(electricApplianceOrder.getElectricId());
            //找phone
            User user = userMapper.selectById(electricAppliance.getUserId());
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
