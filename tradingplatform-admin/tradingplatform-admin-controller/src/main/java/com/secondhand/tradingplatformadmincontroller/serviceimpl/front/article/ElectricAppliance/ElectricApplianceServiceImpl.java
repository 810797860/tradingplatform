package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.ElectricAppliance;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.enums.SqlLike;
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
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.BusinessSelectItem;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.CustomizeStatus;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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
 * @author zhangjk
 * @description : ElectricAppliance 服务实现类
 * ---------------------------------
 * @since 2019-03-15
 */

@Service
@CacheConfig(cacheNames = "electricAppliance")
public class ElectricApplianceServiceImpl extends BaseServiceImpl<ElectricApplianceMapper, ElectricAppliance> implements ElectricApplianceService {

    @Autowired
    private ElectricApplianceMapper electricApplianceMapper;

    @Autowired
    private ElectricApplianceOrderMapper electricApplianceOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShortMessageService shortMessageService;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long electricApplianceId) {
        ElectricAppliance electricAppliance = new ElectricAppliance();
        electricAppliance.setId(electricApplianceId);
        electricAppliance.setDeleted(true);
        return electricApplianceMapper.updateById(electricAppliance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> electricApplianceIds) {
        electricApplianceIds.forEach(electricApplianceId -> {
            myFakeDeleteById(electricApplianceId);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myExaminationBatchPass(List<Long> electricApplianceIds) {
        electricApplianceIds.forEach(electricApplianceId -> {
            ElectricAppliance electricAppliance = new ElectricAppliance();
            electricAppliance.setId(electricApplianceId);
            electricAppliance.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            electricAppliance.setNotPassReason("");
            myUpdateById(electricAppliance);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long electricApplianceId) {
        return electricApplianceMapper.selectMapById(electricApplianceId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ElectricAppliance myElectricApplianceCreateUpdate(ElectricAppliance electricAppliance) {
        Long electricApplianceId = electricAppliance.getId();
        if (electricApplianceId == null) {
            electricAppliance.setUuid(ToolUtil.getUUID());
            electricApplianceMapper.insert(electricAppliance);
        } else {
            electricApplianceMapper.updateById(electricAppliance);
        }
        return electricAppliance;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricAppliance electricAppliance) {

        //判空
        electricAppliance.setDeleted(false);
        Wrapper<ElectricAppliance> wrapper = new EntityWrapper<>(electricAppliance);
        //自定义sql回显
        wrapper.setSqlSelect("c_business_electric_appliance.id as id, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_electric_appliance.classification)) AS classification, c_business_electric_appliance.comment_num as comment_num, c_business_electric_appliance.description as description, c_business_electric_appliance.details as details, c_business_electric_appliance.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_electric_appliance.user_id) ) AS user_id, c_business_electric_appliance.title as title, c_business_electric_appliance.back_check_time as back_check_time, c_business_electric_appliance.updated_by as updated_by, c_business_electric_appliance.brand as brand, c_business_electric_appliance.cover as cover, c_business_electric_appliance.not_pass_reason as not_pass_reason, c_business_electric_appliance.model as model, c_business_electric_appliance.price as price, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_electric_appliance.back_check_status)) AS back_check_status, c_business_electric_appliance.created_by as created_by, c_business_electric_appliance.type as type, c_business_electric_appliance.star as star, c_business_electric_appliance.power as power, c_business_electric_appliance.deleted as deleted, c_business_electric_appliance.created_at as created_at")
                //字符串模糊匹配
                .like("title", electricAppliance.getTitle(), SqlLike.DEFAULT)
                .like("price", electricAppliance.getPrice() == null ? null : (electricAppliance.getPrice() % 1 == 0 ? new Integer(electricAppliance.getPrice().intValue()).toString() : electricAppliance.getPrice().toString()), SqlLike.DEFAULT);
        electricAppliance.setTitle(null);
        electricAppliance.setPrice(null);
        //遍历排序
        List<Sort> sorts = electricAppliance.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectMapsPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<ElectricAppliance> mySelectListWithMap(Map<String, Object> map) {
        return electricApplianceMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<ElectricAppliance> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<ElectricAppliance> mySelectList(Wrapper<ElectricAppliance> wrapper) {
        return electricApplianceMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(ElectricAppliance electricAppliance) {
        electricAppliance.setUuid(ToolUtil.getUUID());
        return this.insert(electricAppliance);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<ElectricAppliance> electricApplianceList) {
        electricApplianceList.forEach(electricAppliance -> {
            electricAppliance.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(electricApplianceList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(ElectricAppliance electricAppliance) {
        //没有uuid的话要加上去
        if (electricAppliance.getUuid().equals(null)) {
            electricAppliance.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(electricAppliance);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<ElectricAppliance> electricApplianceList) {
        electricApplianceList.forEach(electricAppliance -> {
            //没有uuid的话要加上去
            if (electricAppliance.getUuid().equals(null)) {
                electricAppliance.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(electricApplianceList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<ElectricAppliance> mySelectBatchIds(Collection<? extends Serializable> electricApplianceIds) {
        return electricApplianceMapper.selectBatchIds(electricApplianceIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public ElectricAppliance mySelectById(Serializable electricApplianceId) {
        return electricApplianceMapper.selectById(electricApplianceId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<ElectricAppliance> wrapper) {
        return electricApplianceMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public ElectricAppliance mySelectOne(Wrapper<ElectricAppliance> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<ElectricAppliance> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(ElectricAppliance electricAppliance, Wrapper<ElectricAppliance> wrapper) {
        return this.update(electricAppliance, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<ElectricAppliance> electricApplianceList) {
        return this.updateBatchById(electricApplianceList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(ElectricAppliance electricAppliance) {
        return this.updateById(electricAppliance);
    }

    @Override
    @CacheEvict(cacheNames = "electricApplianceOrder", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Float mySettlementById(Long electricApplianceId, Float balance, Long userId) throws CustomizeException, ClientException {

        //先增加一条订单到购物车
        //先找价格
        ElectricAppliance electricAppliance = electricApplianceMapper.selectById(electricApplianceId);
        Float price = electricAppliance.getPrice();
        ElectricApplianceOrder electricApplianceOrder = new ElectricApplianceOrder();
        electricApplianceOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_PAID);
        electricApplianceOrder.setUserId(userId);
        electricApplianceOrder.setElectricId(electricApplianceId);
        electricApplianceOrder.setPrice(price);
        electricApplianceOrder.setQuantity(1);
        electricApplianceOrder.setUuid(ToolUtil.getUUID());
        electricApplianceOrderMapper.insert(electricApplianceOrder);

        //相减
        balance = balance - price;
        if (balance < 0){
            throw new CustomizeException(CustomizeStatus.ELECTRIC_APPLIANCE_INSUFFICIENT_BALANCE, this.getClass());
        }

        //给卖家短信
        //找phone
        User user = userMapper.selectById(electricAppliance.getUserId());
        String phone = user.getPhone();
        //如果该用户有验证手机号码
        if (!ToolUtil.strIsEmpty(phone)){
            shortMessageService.notifyPurchaseSuccess(phone);
        }
        return balance;
    }
}
