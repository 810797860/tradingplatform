package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.DigitalSquare;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareAdvisory;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare.DigitalSquareAdvisoryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareAdvisoryService;
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
 *   @description : DigitalSquareAdvisory 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "digitalSquareAdvisory")
public class DigitalSquareAdvisoryServiceImpl extends BaseServiceImpl<DigitalSquareAdvisoryMapper, DigitalSquareAdvisory> implements DigitalSquareAdvisoryService {

    @Autowired
    private DigitalSquareAdvisoryMapper digitalSquareAdvisoryMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long digitalSquareAdvisoryId) {
        DigitalSquareAdvisory digitalSquareAdvisory = new DigitalSquareAdvisory();
        digitalSquareAdvisory.setId(digitalSquareAdvisoryId);
        digitalSquareAdvisory.setDeleted(true);
        return digitalSquareAdvisoryMapper.updateById(digitalSquareAdvisory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> digitalSquareAdvisoryIds) {
        digitalSquareAdvisoryIds.forEach(digitalSquareAdvisoryId->{
            myFakeDeleteById(digitalSquareAdvisoryId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long digitalSquareAdvisoryId) {
        return digitalSquareAdvisoryMapper.selectMapById(digitalSquareAdvisoryId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public DigitalSquareAdvisory myDigitalSquareAdvisoryCreateUpdate(DigitalSquareAdvisory digitalSquareAdvisory) {
        Long digitalSquareAdvisoryId = digitalSquareAdvisory.getId();
        if (digitalSquareAdvisoryId == null){
            digitalSquareAdvisory.setUuid(ToolUtil.getUUID());
            digitalSquareAdvisoryMapper.insert(digitalSquareAdvisory);
        } else {
            digitalSquareAdvisoryMapper.updateById(digitalSquareAdvisory);
        }
        return digitalSquareAdvisory;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareAdvisory digitalSquareAdvisory) {

        //判空
        digitalSquareAdvisory.setDeleted(false);
        Wrapper<DigitalSquareAdvisory> wrapper = new EntityWrapper<>(digitalSquareAdvisory);
        //自定义sql回显
        wrapper.setSqlSelect("c_business_digital_square_advisory.id as id, c_business_digital_square_advisory.updated_at as updated_at, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_digital_square_advisory.back_check_status)) AS back_check_status, c_business_digital_square_advisory.pid as pid, c_business_digital_square_advisory.updated_by as updated_by, c_business_digital_square_advisory.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_digital_square_advisory.user_id) ) AS user_id, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_digital_square_advisory.reply_id) ) AS reply_id, c_business_digital_square_advisory.created_by as created_by, ( SELECT concat( '{\"id\":\"', cbds.id, '\",\"title\":\"', cbds.title, '\",\"cover\":\"', cbds.cover, '\",\"price\":\"', cbds.price, '\"}' ) FROM c_business_digital_square cbds WHERE (cbds.id = c_business_digital_square_advisory.digital_id) ) AS digital_id, c_business_digital_square_advisory.deleted as deleted, c_business_digital_square_advisory.content as content, c_business_digital_square_advisory.description as description, c_business_digital_square_advisory.created_at as created_at");
        //遍历排序
        List<Sort> sorts = digitalSquareAdvisory.getSorts();
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
    public List<DigitalSquareAdvisory> mySelectListWithMap(Map<String, Object> map) {
        return digitalSquareAdvisoryMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<DigitalSquareAdvisory> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<DigitalSquareAdvisory> mySelectList(Wrapper<DigitalSquareAdvisory> wrapper) {
        return digitalSquareAdvisoryMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(DigitalSquareAdvisory digitalSquareAdvisory) {
        digitalSquareAdvisory.setUuid(ToolUtil.getUUID());
        return this.insert(digitalSquareAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<DigitalSquareAdvisory> digitalSquareAdvisoryList) {
        digitalSquareAdvisoryList.forEach(digitalSquareAdvisory -> {
            digitalSquareAdvisory.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(digitalSquareAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(DigitalSquareAdvisory digitalSquareAdvisory) {
        //没有uuid的话要加上去
        if (digitalSquareAdvisory.getUuid().equals(null)){
            digitalSquareAdvisory.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(digitalSquareAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<DigitalSquareAdvisory> digitalSquareAdvisoryList) {
        digitalSquareAdvisoryList.forEach(digitalSquareAdvisory -> {
            //没有uuid的话要加上去
            if (digitalSquareAdvisory.getUuid().equals(null)){
                digitalSquareAdvisory.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(digitalSquareAdvisoryList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<DigitalSquareAdvisory> mySelectBatchIds(Collection<? extends Serializable> digitalSquareAdvisoryIds) {
        return digitalSquareAdvisoryMapper.selectBatchIds(digitalSquareAdvisoryIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public DigitalSquareAdvisory mySelectById(Serializable digitalSquareAdvisoryId) {
        return digitalSquareAdvisoryMapper.selectById(digitalSquareAdvisoryId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<DigitalSquareAdvisory> wrapper) {
        return digitalSquareAdvisoryMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public DigitalSquareAdvisory mySelectOne(Wrapper<DigitalSquareAdvisory> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<DigitalSquareAdvisory> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(DigitalSquareAdvisory digitalSquareAdvisory, Wrapper<DigitalSquareAdvisory> wrapper) {
        return this.update(digitalSquareAdvisory, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<DigitalSquareAdvisory> digitalSquareAdvisoryList) {
        return this.updateBatchById(digitalSquareAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(DigitalSquareAdvisory digitalSquareAdvisory) {
        return this.updateById(digitalSquareAdvisory);
    }
}
