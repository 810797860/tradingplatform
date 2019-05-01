package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.DigitalSquare;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareEvaluation;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare.DigitalSquareEvaluationMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareEvaluationService;
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
 *   @description : DigitalSquareEvaluation 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "digitalSquareEvaluation")
public class DigitalSquareEvaluationServiceImpl extends BaseServiceImpl<DigitalSquareEvaluationMapper, DigitalSquareEvaluation> implements DigitalSquareEvaluationService {

    @Autowired
    private DigitalSquareEvaluationMapper digitalSquareEvaluationMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long digitalSquareEvaluationId) {
        DigitalSquareEvaluation digitalSquareEvaluation = new DigitalSquareEvaluation();
        digitalSquareEvaluation.setId(digitalSquareEvaluationId);
        digitalSquareEvaluation.setDeleted(true);
        return digitalSquareEvaluationMapper.updateById(digitalSquareEvaluation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> digitalSquareEvaluationIds) {
        digitalSquareEvaluationIds.forEach(digitalSquareEvaluationId->{
            myFakeDeleteById(digitalSquareEvaluationId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long digitalSquareEvaluationId) {
        return digitalSquareEvaluationMapper.selectMapById(digitalSquareEvaluationId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public DigitalSquareEvaluation myDigitalSquareEvaluationCreateUpdate(DigitalSquareEvaluation digitalSquareEvaluation) {
        Long digitalSquareEvaluationId = digitalSquareEvaluation.getId();
        if (digitalSquareEvaluationId == null){
            digitalSquareEvaluation.setUuid(ToolUtil.getUUID());
            digitalSquareEvaluationMapper.insert(digitalSquareEvaluation);
        } else {
            digitalSquareEvaluationMapper.updateById(digitalSquareEvaluation);
        }
        return digitalSquareEvaluation;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareEvaluation digitalSquareEvaluation) {

        //判空
        digitalSquareEvaluation.setDeleted(false);
        Wrapper<DigitalSquareEvaluation> wrapper = new EntityWrapper<>(digitalSquareEvaluation);
        //自定义sql回显
        wrapper.setSqlSelect("c_business_digital_square_evaluation.id as id, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_digital_square_evaluation.back_check_status)) AS back_check_status, c_business_digital_square_evaluation.description as description, c_business_digital_square_evaluation.updated_at as updated_at, c_business_digital_square_evaluation.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_digital_square_evaluation.user_id) ) AS user_id, c_business_digital_square_evaluation.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbds.id, '\",\"title\":\"', cbds.title, '\",\"cover\":\"', cbds.cover, '\",\"price\":\"', cbds.price, '\"}' ) FROM c_business_digital_square cbds WHERE (cbds.id = c_business_digital_square_evaluation.digital_id) ) AS digital_id, c_business_digital_square_evaluation.star as star, c_business_digital_square_evaluation.created_by as created_by, c_business_digital_square_evaluation.content as content, c_business_digital_square_evaluation.deleted as deleted, c_business_digital_square_evaluation.created_at as created_at");
        //遍历排序
        List<Sort> sorts = digitalSquareEvaluation.getSorts();
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
    public List<DigitalSquareEvaluation> mySelectListWithMap(Map<String, Object> map) {
        return digitalSquareEvaluationMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<DigitalSquareEvaluation> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<DigitalSquareEvaluation> mySelectList(Wrapper<DigitalSquareEvaluation> wrapper) {
        return digitalSquareEvaluationMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(DigitalSquareEvaluation digitalSquareEvaluation) {
        digitalSquareEvaluation.setUuid(ToolUtil.getUUID());
        return this.insert(digitalSquareEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<DigitalSquareEvaluation> digitalSquareEvaluationList) {
        digitalSquareEvaluationList.forEach(digitalSquareEvaluation -> {
            digitalSquareEvaluation.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(digitalSquareEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(DigitalSquareEvaluation digitalSquareEvaluation) {
        //没有uuid的话要加上去
        if (digitalSquareEvaluation.getUuid().equals(null)){
            digitalSquareEvaluation.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(digitalSquareEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<DigitalSquareEvaluation> digitalSquareEvaluationList) {
        digitalSquareEvaluationList.forEach(digitalSquareEvaluation -> {
            //没有uuid的话要加上去
            if (digitalSquareEvaluation.getUuid().equals(null)){
                digitalSquareEvaluation.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(digitalSquareEvaluationList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<DigitalSquareEvaluation> mySelectBatchIds(Collection<? extends Serializable> digitalSquareEvaluationIds) {
        return digitalSquareEvaluationMapper.selectBatchIds(digitalSquareEvaluationIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public DigitalSquareEvaluation mySelectById(Serializable digitalSquareEvaluationId) {
        return digitalSquareEvaluationMapper.selectById(digitalSquareEvaluationId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<DigitalSquareEvaluation> wrapper) {
        return digitalSquareEvaluationMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public DigitalSquareEvaluation mySelectOne(Wrapper<DigitalSquareEvaluation> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<DigitalSquareEvaluation> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(DigitalSquareEvaluation digitalSquareEvaluation, Wrapper<DigitalSquareEvaluation> wrapper) {
        return this.update(digitalSquareEvaluation, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<DigitalSquareEvaluation> digitalSquareEvaluationList) {
        return this.updateBatchById(digitalSquareEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(DigitalSquareEvaluation digitalSquareEvaluation) {
        return this.updateById(digitalSquareEvaluation);
    }
}
