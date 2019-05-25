package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.SportsSpecial;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecial;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialOrder;
import com.secondhand.tradingplatformadminmapper.mapper.admin.shiro.UserMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial.SportsSpecialMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial.SportsSpecialOrderMapper;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialOrderService;
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

    @Autowired
    private SportsSpecialMapper sportsSpecialMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShortMessageService shortMessageService;

    @Override
    @CacheEvict(cacheNames = {"sportsSpecialOrder", "shoppingCart"}, allEntries = true)
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
    @CacheEvict(cacheNames = {"sportsSpecialOrder", "shoppingCart"}, allEntries = true)
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
        //自动生成sql回显
        wrapper.setSqlSelect("c_business_sports_special_order.id as id, c_business_sports_special_order.price as price, c_business_sports_special_order.created_by as created_by, c_business_sports_special_order.quantity as quantity, c_business_sports_special_order.deleted as deleted, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_sports_special_order.order_status)) AS order_status, c_business_sports_special_order.description as description, c_business_sports_special_order.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_sports_special_order.user_id) ) AS user_id, c_business_sports_special_order.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbss.id, '\",\"title\":\"', cbss.title, '\",\"cover\":\"', cbss.cover, '\",\"price\":\"', cbss.price, '\"}' ) FROM c_business_sports_special cbss WHERE (cbss.id = c_business_sports_special_order.sports_id) ) AS sports_id, c_business_sports_special_order.created_at as created_at");
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

    @Override
    @Cacheable(key = "'mySettlementByListId' + #p0 + #p1")
    @Transactional(rollbackFor = Exception.class)
    public Float mySettlementByListId(List<Long> sportsSpecialOrderLists, Float balance) {

        final Float[] tempBalance = {balance};

        //遍历结算
        sportsSpecialOrderLists.forEach(sportsSpecialOrderId -> {

            //先找出该订单
            SportsSpecialOrder sportsSpecialOrder = this.mySelectById(sportsSpecialOrderId);
            //扣掉余额
            tempBalance[0] = tempBalance[0] - sportsSpecialOrder.getPrice() * sportsSpecialOrder.getQuantity();
            if (tempBalance[0] >= 0) {
                //修改订单的状态
                sportsSpecialOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_PAID);
            } else {
                try {
                    throw new CustomizeException(CustomizeStatus.SPORTS_SPECIAL_INSUFFICIENT_BALANCE, this.getClass());
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
    public void myNotifyByListId(List<Long> sportsSpecialOrderLists) {

        //遍历发短信
        sportsSpecialOrderLists.forEach(sportsSpecialOrderId -> {

            //先找卖家的电话号码（商品id->userId->phone）
            //先找该条订单的信息
            SportsSpecialOrder sportsSpecialOrder = this.mySelectById(sportsSpecialOrderId);
            //找该条商品的信息
            SportsSpecial sportsSpecial = sportsSpecialMapper.selectById(sportsSpecialOrder.getSportsId());
            //找phone
            User user = userMapper.selectById(sportsSpecial.getUserId());
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
