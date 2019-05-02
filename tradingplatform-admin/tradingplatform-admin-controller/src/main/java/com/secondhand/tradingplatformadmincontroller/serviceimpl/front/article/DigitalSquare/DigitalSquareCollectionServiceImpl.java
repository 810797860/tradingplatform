package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.DigitalSquare;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareCollection;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare.DigitalSquareCollectionMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareCollectionService;
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
 *   @description : DigitalSquareCollection 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "digitalSquareCollection")
public class DigitalSquareCollectionServiceImpl extends BaseServiceImpl<DigitalSquareCollectionMapper, DigitalSquareCollection> implements DigitalSquareCollectionService {

    @Autowired
    private DigitalSquareCollectionMapper digitalSquareCollectionMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long digitalSquareCollectionId) {
        DigitalSquareCollection digitalSquareCollection = new DigitalSquareCollection();
        digitalSquareCollection.setId(digitalSquareCollectionId);
        digitalSquareCollection.setDeleted(true);
        return digitalSquareCollectionMapper.updateById(digitalSquareCollection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> digitalSquareCollectionIds) {
        digitalSquareCollectionIds.forEach(digitalSquareCollectionId->{
            myFakeDeleteById(digitalSquareCollectionId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long digitalSquareCollectionId) {
        return digitalSquareCollectionMapper.selectMapById(digitalSquareCollectionId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public DigitalSquareCollection myDigitalSquareCollectionCreateUpdate(DigitalSquareCollection digitalSquareCollection) {
        Long digitalSquareCollectionId = digitalSquareCollection.getId();
        if (digitalSquareCollectionId == null){
            digitalSquareCollection.setUuid(ToolUtil.getUUID());
            digitalSquareCollectionMapper.insert(digitalSquareCollection);
        } else {
            digitalSquareCollectionMapper.updateById(digitalSquareCollection);
        }
        return digitalSquareCollection;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareCollection digitalSquareCollection) {

        //判空
        digitalSquareCollection.setDeleted(false);
        Wrapper<DigitalSquareCollection> wrapper = new EntityWrapper<>(digitalSquareCollection);
        //自动生成sql回显
        wrapper.setSqlSelect("c_business_digital_square_collection.id as id, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_digital_square_collection.user_id) ) AS user_id, c_business_digital_square_collection.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbds.id, '\",\"title\":\"', cbds.title, '\",\"cover\":\"', cbds.cover, '\",\"price\":\"', cbds.price, '\"}' ) FROM c_business_digital_square cbds WHERE (cbds.id = c_business_digital_square_collection.digital_id) ) AS digital_id, c_business_digital_square_collection.created_by as created_by, c_business_digital_square_collection.deleted as deleted, c_business_digital_square_collection.description as description, c_business_digital_square_collection.updated_at as updated_at, c_business_digital_square_collection.created_at as created_at");
        //遍历排序
        List<Sort> sorts = digitalSquareCollection.getSorts();
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
    public List<DigitalSquareCollection> mySelectListWithMap(Map<String, Object> map) {
        return digitalSquareCollectionMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<DigitalSquareCollection> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<DigitalSquareCollection> mySelectList(Wrapper<DigitalSquareCollection> wrapper) {
        return digitalSquareCollectionMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "'mySelectCollectionList : ' + #p0")
    public List<Object> mySelectCollectionList(Long userId) {
        Wrapper<DigitalSquareCollection> wrapper = new EntityWrapper<>();
        //没被删除的
        wrapper.setSqlSelect("digital_id");
        wrapper.where("user_id = {0}", userId)
                .and("deleted = {0}", false);
        return digitalSquareCollectionMapper.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(DigitalSquareCollection digitalSquareCollection) {
        digitalSquareCollection.setUuid(ToolUtil.getUUID());
        return this.insert(digitalSquareCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<DigitalSquareCollection> digitalSquareCollectionList) {
        digitalSquareCollectionList.forEach(digitalSquareCollection -> {
            digitalSquareCollection.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(digitalSquareCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(DigitalSquareCollection digitalSquareCollection) {
        //没有uuid的话要加上去
        if (digitalSquareCollection.getUuid().equals(null)){
            digitalSquareCollection.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(digitalSquareCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<DigitalSquareCollection> digitalSquareCollectionList) {
        digitalSquareCollectionList.forEach(digitalSquareCollection -> {
            //没有uuid的话要加上去
            if (digitalSquareCollection.getUuid().equals(null)){
                digitalSquareCollection.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(digitalSquareCollectionList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<DigitalSquareCollection> mySelectBatchIds(Collection<? extends Serializable> digitalSquareCollectionIds) {
        return digitalSquareCollectionMapper.selectBatchIds(digitalSquareCollectionIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public DigitalSquareCollection mySelectById(Serializable digitalSquareCollectionId) {
        return digitalSquareCollectionMapper.selectById(digitalSquareCollectionId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<DigitalSquareCollection> wrapper) {
        return digitalSquareCollectionMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public DigitalSquareCollection mySelectOne(Wrapper<DigitalSquareCollection> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<DigitalSquareCollection> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(DigitalSquareCollection digitalSquareCollection, Wrapper<DigitalSquareCollection> wrapper) {
        return this.update(digitalSquareCollection, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<DigitalSquareCollection> digitalSquareCollectionList) {
        return this.updateBatchById(digitalSquareCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(DigitalSquareCollection digitalSquareCollection) {
        return this.updateById(digitalSquareCollection);
    }
}
