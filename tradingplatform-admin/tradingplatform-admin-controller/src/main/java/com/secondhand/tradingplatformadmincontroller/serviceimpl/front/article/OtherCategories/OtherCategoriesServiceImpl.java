package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.OtherCategories;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.OtherCategories.OtherCategories;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.OtherCategories.OtherCategoriesMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.OtherCategories.OtherCategoriesService;
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
 * @author zhangjk
 * @description : OtherCategories 服务实现类
 * ---------------------------------
 * @since 2019-03-17
 */

@Service
@CacheConfig(cacheNames = "otherCategories")
public class OtherCategoriesServiceImpl extends BaseServiceImpl<OtherCategoriesMapper, OtherCategories> implements OtherCategoriesService {

    @Autowired
    private OtherCategoriesMapper otherCategoriesMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long otherCategoriesId) {
        OtherCategories otherCategories = new OtherCategories();
        otherCategories.setId(otherCategoriesId);
        otherCategories.setDeleted(true);
        return otherCategoriesMapper.updateById(otherCategories);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> otherCategoriesIds) {
        otherCategoriesIds.forEach(otherCategoriesId -> {
            myFakeDeleteById(otherCategoriesId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long otherCategoriesId) {
        return otherCategoriesMapper.selectMapById(otherCategoriesId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public OtherCategories myOtherCategoriesCreateUpdate(OtherCategories otherCategories) {
        Long otherCategoriesId = otherCategories.getId();
        if (otherCategoriesId == null) {
            otherCategories.setUuid(ToolUtil.getUUID());
            otherCategoriesMapper.insert(otherCategories);
        } else {
            otherCategoriesMapper.updateById(otherCategories);
        }
        return otherCategories;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, OtherCategories otherCategories) {

        //判空
        otherCategories.setDeleted(false);
        Wrapper<OtherCategories> wrapper = new EntityWrapper<>(otherCategories);
        //遍历排序
        List<Sort> sorts = otherCategories.getSorts();
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
    public List<OtherCategories> mySelectListWithMap(Map<String, Object> map) {
        return otherCategoriesMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<OtherCategories> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<OtherCategories> mySelectList(Wrapper<OtherCategories> wrapper) {
        return otherCategoriesMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(OtherCategories otherCategories) {
        otherCategories.setUuid(ToolUtil.getUUID());
        return this.insert(otherCategories);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<OtherCategories> otherCategoriesList) {
        otherCategoriesList.forEach(otherCategories -> {
            otherCategories.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(otherCategoriesList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(OtherCategories otherCategories) {
        //没有uuid的话要加上去
        if (otherCategories.getUuid().equals(null)) {
            otherCategories.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(otherCategories);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<OtherCategories> otherCategoriesList) {
        otherCategoriesList.forEach(otherCategories -> {
            //没有uuid的话要加上去
            if (otherCategories.getUuid().equals(null)) {
                otherCategories.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(otherCategoriesList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<OtherCategories> mySelectBatchIds(Collection<? extends Serializable> otherCategoriesIds) {
        return otherCategoriesMapper.selectBatchIds(otherCategoriesIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public OtherCategories mySelectById(Serializable otherCategoriesId) {
        return otherCategoriesMapper.selectById(otherCategoriesId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<OtherCategories> wrapper) {
        return otherCategoriesMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public OtherCategories mySelectOne(Wrapper<OtherCategories> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<OtherCategories> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(OtherCategories otherCategories, Wrapper<OtherCategories> wrapper) {
        return this.update(otherCategories, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<OtherCategories> otherCategoriesList) {
        return this.updateBatchById(otherCategoriesList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(OtherCategories otherCategories) {
        return this.updateById(otherCategories);
    }
}
