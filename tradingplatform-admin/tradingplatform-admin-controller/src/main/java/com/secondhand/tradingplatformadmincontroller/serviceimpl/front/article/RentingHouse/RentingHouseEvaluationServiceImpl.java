package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseEvaluation;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse.RentingHouseEvaluationMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseEvaluationService;
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
 *   @description : RentingHouseEvaluation 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "rentingHouseEvaluation")
public class RentingHouseEvaluationServiceImpl extends BaseServiceImpl<RentingHouseEvaluationMapper, RentingHouseEvaluation> implements RentingHouseEvaluationService {

    @Autowired
    private RentingHouseEvaluationMapper rentingHouseEvaluationMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long rentingHouseEvaluationId) {
        RentingHouseEvaluation rentingHouseEvaluation = new RentingHouseEvaluation();
        rentingHouseEvaluation.setId(rentingHouseEvaluationId);
        rentingHouseEvaluation.setDeleted(true);
        return rentingHouseEvaluationMapper.updateById(rentingHouseEvaluation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> rentingHouseEvaluationIds) {
        rentingHouseEvaluationIds.forEach(rentingHouseEvaluationId->{
            myFakeDeleteById(rentingHouseEvaluationId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long rentingHouseEvaluationId) {
        return rentingHouseEvaluationMapper.selectMapById(rentingHouseEvaluationId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RentingHouseEvaluation myRentingHouseEvaluationCreateUpdate(RentingHouseEvaluation rentingHouseEvaluation) {
        Long rentingHouseEvaluationId = rentingHouseEvaluation.getId();
        if (rentingHouseEvaluationId == null){
            rentingHouseEvaluation.setUuid(ToolUtil.getUUID());
            rentingHouseEvaluationMapper.insert(rentingHouseEvaluation);
        } else {
            rentingHouseEvaluationMapper.updateById(rentingHouseEvaluation);
        }
        return rentingHouseEvaluation;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseEvaluation rentingHouseEvaluation) {

        //判空
        rentingHouseEvaluation.setDeleted(false);
        Wrapper<RentingHouseEvaluation> wrapper = new EntityWrapper<>(rentingHouseEvaluation);
        //自动生成sql回显
        wrapper.setSqlSelect("c_business_renting_house_evaluation.id as id, ( SELECT concat( '{\"id\":\"', cbrh.id, '\",\"title\":\"', cbrh.title, '\",\"cover\":\"', cbrh.cover, '\",\"price\":\"', cbrh.price, '\"}' ) FROM c_business_renting_house cbrh WHERE (cbrh.id = c_business_renting_house_evaluation.renting_id) ) AS renting_id, c_business_renting_house_evaluation.star as star, c_business_renting_house_evaluation.created_by as created_by, c_business_renting_house_evaluation.content as content, c_business_renting_house_evaluation.deleted as deleted, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_renting_house_evaluation.back_check_status)) AS back_check_status, c_business_renting_house_evaluation.description as description, c_business_renting_house_evaluation.updated_at as updated_at, c_business_renting_house_evaluation.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_renting_house_evaluation.user_id) ) AS user_id, c_business_renting_house_evaluation.updated_by as updated_by, c_business_renting_house_evaluation.created_at as created_at");
        //遍历排序
        List<Sort> sorts = rentingHouseEvaluation.getSorts();
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
    public List<RentingHouseEvaluation> mySelectListWithMap(Map<String, Object> map) {
        return rentingHouseEvaluationMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<RentingHouseEvaluation> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<RentingHouseEvaluation> mySelectList(Wrapper<RentingHouseEvaluation> wrapper) {
        return rentingHouseEvaluationMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RentingHouseEvaluation rentingHouseEvaluation) {
        rentingHouseEvaluation.setUuid(ToolUtil.getUUID());
        return this.insert(rentingHouseEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RentingHouseEvaluation> rentingHouseEvaluationList) {
        rentingHouseEvaluationList.forEach(rentingHouseEvaluation -> {
            rentingHouseEvaluation.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(rentingHouseEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RentingHouseEvaluation rentingHouseEvaluation) {
        //没有uuid的话要加上去
        if (rentingHouseEvaluation.getUuid().equals(null)){
            rentingHouseEvaluation.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(rentingHouseEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RentingHouseEvaluation> rentingHouseEvaluationList) {
        rentingHouseEvaluationList.forEach(rentingHouseEvaluation -> {
            //没有uuid的话要加上去
            if (rentingHouseEvaluation.getUuid().equals(null)){
                rentingHouseEvaluation.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(rentingHouseEvaluationList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RentingHouseEvaluation> mySelectBatchIds(Collection<? extends Serializable> rentingHouseEvaluationIds) {
        return rentingHouseEvaluationMapper.selectBatchIds(rentingHouseEvaluationIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RentingHouseEvaluation mySelectById(Serializable rentingHouseEvaluationId) {
        return rentingHouseEvaluationMapper.selectById(rentingHouseEvaluationId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<RentingHouseEvaluation> wrapper) {
        return rentingHouseEvaluationMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public RentingHouseEvaluation mySelectOne(Wrapper<RentingHouseEvaluation> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<RentingHouseEvaluation> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RentingHouseEvaluation rentingHouseEvaluation, Wrapper<RentingHouseEvaluation> wrapper) {
        return this.update(rentingHouseEvaluation, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RentingHouseEvaluation> rentingHouseEvaluationList) {
        return this.updateBatchById(rentingHouseEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RentingHouseEvaluation rentingHouseEvaluation) {
        return this.updateById(rentingHouseEvaluation);
    }
}
