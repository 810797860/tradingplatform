package com.secondhand.tradingplatformadmincontroller.serviceimpl.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.business.Annex;
import com.secondhand.tradingplatformadminmapper.mapper.business.AnnexMapper;
import com.secondhand.tradingplatformadminservice.service.business.AnnexService;
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
 *   @description : Annex 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-14
 */

@Service
@CacheConfig(cacheNames = "annex")
public class AnnexServiceImpl extends BaseServiceImpl<AnnexMapper, Annex> implements AnnexService {

    @Autowired
    private AnnexMapper annexMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long annexId) {
        Annex annex = new Annex();
        annex.setId(annexId);
        annex.setDeleted(true);
        return annexMapper.updateById(annex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> annexIds) {
        annexIds.forEach(annexId->{
            myFakeDeleteById(annexId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long annexId) {
        return annexMapper.selectMapById(annexId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Annex myAnnexCreateUpdate(Annex annex) {
        Long annexId = annex.getId();
        if (annexId == null){
            annex.setUuid(ToolUtil.getUUID());
            annexMapper.insert(annex);
        } else {
            annexMapper.updateById(annex);
        }
        return annex;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Annex> mySelectPageWithParam(Page<Annex> page, Annex annex) {

        //判空
        annex.setDeleted(false);
        Wrapper<Annex> wrapper = new EntityWrapper<>(annex);
        //遍历排序
        List<Sort> sorts = annex.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectPage(page, wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Annex> mySelectListWithMap(Map<String, Object> map) {
        return annexMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Annex> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Annex> mySelectList(Wrapper<Annex> wrapper) {
        return annexMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Annex annex) {
        annex.setUuid(ToolUtil.getUUID());
        return this.insert(annex);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Annex> annexList) {
        annexList.forEach(annex -> {
            annex.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(annexList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Annex annex) {
        //没有uuid的话要加上去
        if (annex.getUuid().equals(null)){
            annex.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(annex);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Annex> annexList) {
        annexList.forEach(annex -> {
            //没有uuid的话要加上去
            if (annex.getUuid().equals(null)){
                annex.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(annexList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Annex> mySelectBatchIds(Collection<? extends Serializable> annexIds) {
        return annexMapper.selectBatchIds(annexIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Annex mySelectById(Serializable annexId) {
        return annexMapper.selectById(annexId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Annex> wrapper) {
        return annexMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Annex mySelectOne(Wrapper<Annex> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Annex annex, Wrapper<Annex> wrapper) {
        return this.update(annex, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Annex> annexList) {
        return this.updateBatchById(annexList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Annex annex) {
        return this.updateById(annex);
    }
}
