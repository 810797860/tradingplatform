package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceEvaluation;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricAppliance.ElectricApplianceEvaluationMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceEvaluationService;
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
 *   @description : ElectricApplianceEvaluation 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "electricApplianceEvaluation")
public class ElectricApplianceEvaluationServiceImpl extends BaseServiceImpl<ElectricApplianceEvaluationMapper, ElectricApplianceEvaluation> implements ElectricApplianceEvaluationService {

    @Autowired
    private ElectricApplianceEvaluationMapper electricApplianceEvaluationMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long electricApplianceEvaluationId) {
        ElectricApplianceEvaluation electricApplianceEvaluation = new ElectricApplianceEvaluation();
        electricApplianceEvaluation.setId(electricApplianceEvaluationId);
        electricApplianceEvaluation.setDeleted(true);
        return electricApplianceEvaluationMapper.updateById(electricApplianceEvaluation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> electricApplianceEvaluationIds) {
        electricApplianceEvaluationIds.forEach(electricApplianceEvaluationId->{
            myFakeDeleteById(electricApplianceEvaluationId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long electricApplianceEvaluationId) {
        return electricApplianceEvaluationMapper.selectMapById(electricApplianceEvaluationId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ElectricApplianceEvaluation myElectricApplianceEvaluationCreateUpdate(ElectricApplianceEvaluation electricApplianceEvaluation) {
        Long electricApplianceEvaluationId = electricApplianceEvaluation.getId();
        if (electricApplianceEvaluationId == null){
            electricApplianceEvaluation.setUuid(ToolUtil.getUUID());
            electricApplianceEvaluationMapper.insert(electricApplianceEvaluation);
        } else {
            electricApplianceEvaluationMapper.updateById(electricApplianceEvaluation);
        }
        return electricApplianceEvaluation;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceEvaluation electricApplianceEvaluation) {

        //判空
        electricApplianceEvaluation.setDeleted(false);
        Wrapper<ElectricApplianceEvaluation> wrapper = new EntityWrapper<>(electricApplianceEvaluation);
        //自定义sql回显
        wrapper.setSqlSelect("c_business_electric_appliance_evaluation.id as id, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_electric_appliance_evaluation.back_check_status)) AS back_check_status, c_business_electric_appliance_evaluation.description as description, c_business_electric_appliance_evaluation.updated_at as updated_at, c_business_electric_appliance_evaluation.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_electric_appliance_evaluation.user_id) ) AS user_id, c_business_electric_appliance_evaluation.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbds.id, '\",\"title\":\"', cbds.title, '\",\"cover\":\"', cbds.cover, '\",\"price\":\"', cbds.price, '\"}' ) FROM c_business_digital_square cbds WHERE (cbds.id = c_business_electric_appliance_evaluation.electric_id) ) AS electric_id, c_business_electric_appliance_evaluation.star as star, c_business_electric_appliance_evaluation.created_by as created_by, c_business_electric_appliance_evaluation.content as content, c_business_electric_appliance_evaluation.deleted as deleted, c_business_electric_appliance_evaluation.created_at as created_at");
        //遍历排序
        List<Sort> sorts = electricApplianceEvaluation.getSorts();
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
    public List<ElectricApplianceEvaluation> mySelectListWithMap(Map<String, Object> map) {
        return electricApplianceEvaluationMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<ElectricApplianceEvaluation> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<ElectricApplianceEvaluation> mySelectList(Wrapper<ElectricApplianceEvaluation> wrapper) {
        return electricApplianceEvaluationMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(ElectricApplianceEvaluation electricApplianceEvaluation) {
        electricApplianceEvaluation.setUuid(ToolUtil.getUUID());
        return this.insert(electricApplianceEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<ElectricApplianceEvaluation> electricApplianceEvaluationList) {
        electricApplianceEvaluationList.forEach(electricApplianceEvaluation -> {
            electricApplianceEvaluation.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(electricApplianceEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(ElectricApplianceEvaluation electricApplianceEvaluation) {
        //没有uuid的话要加上去
        if (electricApplianceEvaluation.getUuid().equals(null)){
            electricApplianceEvaluation.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(electricApplianceEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<ElectricApplianceEvaluation> electricApplianceEvaluationList) {
        electricApplianceEvaluationList.forEach(electricApplianceEvaluation -> {
            //没有uuid的话要加上去
            if (electricApplianceEvaluation.getUuid().equals(null)){
                electricApplianceEvaluation.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(electricApplianceEvaluationList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<ElectricApplianceEvaluation> mySelectBatchIds(Collection<? extends Serializable> electricApplianceEvaluationIds) {
        return electricApplianceEvaluationMapper.selectBatchIds(electricApplianceEvaluationIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public ElectricApplianceEvaluation mySelectById(Serializable electricApplianceEvaluationId) {
        return electricApplianceEvaluationMapper.selectById(electricApplianceEvaluationId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<ElectricApplianceEvaluation> wrapper) {
        return electricApplianceEvaluationMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public ElectricApplianceEvaluation mySelectOne(Wrapper<ElectricApplianceEvaluation> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<ElectricApplianceEvaluation> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(ElectricApplianceEvaluation electricApplianceEvaluation, Wrapper<ElectricApplianceEvaluation> wrapper) {
        return this.update(electricApplianceEvaluation, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<ElectricApplianceEvaluation> electricApplianceEvaluationList) {
        return this.updateBatchById(electricApplianceEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(ElectricApplianceEvaluation electricApplianceEvaluation) {
        return this.updateById(electricApplianceEvaluation);
    }
}
