package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.SportsSpecial;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialEvaluation;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial.SportsSpecialEvaluationMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialEvaluationService;
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
 *   @description : SportsSpecialEvaluation 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "sportsSpecialEvaluation")
public class SportsSpecialEvaluationServiceImpl extends BaseServiceImpl<SportsSpecialEvaluationMapper, SportsSpecialEvaluation> implements SportsSpecialEvaluationService {

    @Autowired
    private SportsSpecialEvaluationMapper sportsSpecialEvaluationMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long sportsSpecialEvaluationId) {
        SportsSpecialEvaluation sportsSpecialEvaluation = new SportsSpecialEvaluation();
        sportsSpecialEvaluation.setId(sportsSpecialEvaluationId);
        sportsSpecialEvaluation.setDeleted(true);
        return sportsSpecialEvaluationMapper.updateById(sportsSpecialEvaluation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> sportsSpecialEvaluationIds) {
        sportsSpecialEvaluationIds.forEach(sportsSpecialEvaluationId->{
            myFakeDeleteById(sportsSpecialEvaluationId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long sportsSpecialEvaluationId) {
        return sportsSpecialEvaluationMapper.selectMapById(sportsSpecialEvaluationId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SportsSpecialEvaluation mySportsSpecialEvaluationCreateUpdate(SportsSpecialEvaluation sportsSpecialEvaluation) {
        Long sportsSpecialEvaluationId = sportsSpecialEvaluation.getId();
        if (sportsSpecialEvaluationId == null){
            sportsSpecialEvaluation.setUuid(ToolUtil.getUUID());
            sportsSpecialEvaluationMapper.insert(sportsSpecialEvaluation);
        } else {
            sportsSpecialEvaluationMapper.updateById(sportsSpecialEvaluation);
        }
        return sportsSpecialEvaluation;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialEvaluation sportsSpecialEvaluation) {

        //判空
        sportsSpecialEvaluation.setDeleted(false);
        Wrapper<SportsSpecialEvaluation> wrapper = new EntityWrapper<>(sportsSpecialEvaluation);
        //自动生成sql回显
        wrapper.setSqlSelect("c_business_sports_special_evaluation.id as id, ( SELECT concat( '{\"id\":\"', cbss.id, '\",\"title\":\"', cbss.title, '\",\"cover\":\"', cbss.cover, '\",\"price\":\"', cbss.price, '\"}' ) FROM c_business_sports_special cbss WHERE (cbss.id = c_business_sports_special_evaluation.sports_id) ) AS sports_id, c_business_sports_special_evaluation.star as star, c_business_sports_special_evaluation.created_by as created_by, c_business_sports_special_evaluation.content as content, c_business_sports_special_evaluation.deleted as deleted, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_sports_special_evaluation.back_check_status)) AS back_check_status, c_business_sports_special_evaluation.description as description, c_business_sports_special_evaluation.updated_at as updated_at, c_business_sports_special_evaluation.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_sports_special_evaluation.user_id) ) AS user_id, c_business_sports_special_evaluation.updated_by as updated_by, c_business_sports_special_evaluation.created_at as created_at");
        //遍历排序
        List<Sort> sorts = sportsSpecialEvaluation.getSorts();
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
    public List<SportsSpecialEvaluation> mySelectListWithMap(Map<String, Object> map) {
        return sportsSpecialEvaluationMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<SportsSpecialEvaluation> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<SportsSpecialEvaluation> mySelectList(Wrapper<SportsSpecialEvaluation> wrapper) {
        return sportsSpecialEvaluationMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SportsSpecialEvaluation sportsSpecialEvaluation) {
        sportsSpecialEvaluation.setUuid(ToolUtil.getUUID());
        return this.insert(sportsSpecialEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SportsSpecialEvaluation> sportsSpecialEvaluationList) {
        sportsSpecialEvaluationList.forEach(sportsSpecialEvaluation -> {
            sportsSpecialEvaluation.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(sportsSpecialEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SportsSpecialEvaluation sportsSpecialEvaluation) {
        //没有uuid的话要加上去
        if (sportsSpecialEvaluation.getUuid().equals(null)){
            sportsSpecialEvaluation.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(sportsSpecialEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SportsSpecialEvaluation> sportsSpecialEvaluationList) {
        sportsSpecialEvaluationList.forEach(sportsSpecialEvaluation -> {
            //没有uuid的话要加上去
            if (sportsSpecialEvaluation.getUuid().equals(null)){
                sportsSpecialEvaluation.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(sportsSpecialEvaluationList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<SportsSpecialEvaluation> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialEvaluationIds) {
        return sportsSpecialEvaluationMapper.selectBatchIds(sportsSpecialEvaluationIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public SportsSpecialEvaluation mySelectById(Serializable sportsSpecialEvaluationId) {
        return sportsSpecialEvaluationMapper.selectById(sportsSpecialEvaluationId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<SportsSpecialEvaluation> wrapper) {
        return sportsSpecialEvaluationMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public SportsSpecialEvaluation mySelectOne(Wrapper<SportsSpecialEvaluation> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<SportsSpecialEvaluation> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SportsSpecialEvaluation sportsSpecialEvaluation, Wrapper<SportsSpecialEvaluation> wrapper) {
        return this.update(sportsSpecialEvaluation, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SportsSpecialEvaluation> sportsSpecialEvaluationList) {
        return this.updateBatchById(sportsSpecialEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SportsSpecialEvaluation sportsSpecialEvaluation) {
        return this.updateById(sportsSpecialEvaluation);
    }
}
