package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouseMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouseService;
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
 * @description : RentingHouse 服务实现类
 * ---------------------------------
 * @since 2019-03-17
 */

@Service
@CacheConfig(cacheNames = "rentingHouse")
public class RentingHouseServiceImpl extends BaseServiceImpl<RentingHouseMapper, RentingHouse> implements RentingHouseService {

    @Autowired
    private RentingHouseMapper rentingHouseMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long rentingHouseId) {
        RentingHouse rentingHouse = new RentingHouse();
        rentingHouse.setId(rentingHouseId);
        rentingHouse.setDeleted(true);
        return rentingHouseMapper.updateById(rentingHouse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> rentingHouseIds) {
        rentingHouseIds.forEach(rentingHouseId -> {
            myFakeDeleteById(rentingHouseId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long rentingHouseId) {
        return rentingHouseMapper.selectMapById(rentingHouseId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RentingHouse myRentingHouseCreateUpdate(RentingHouse rentingHouse) {
        Long rentingHouseId = rentingHouse.getId();
        if (rentingHouseId == null) {
            rentingHouse.setUuid(ToolUtil.getUUID());
            rentingHouseMapper.insert(rentingHouse);
        } else {
            rentingHouseMapper.updateById(rentingHouse);
        }
        return rentingHouse;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouse rentingHouse) {

        //判空
        rentingHouse.setDeleted(false);
        Wrapper<RentingHouse> wrapper = new EntityWrapper<>(rentingHouse);
        //遍历排序
        List<Sort> sorts = rentingHouse.getSorts();
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
    public List<RentingHouse> mySelectListWithMap(Map<String, Object> map) {
        return rentingHouseMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<RentingHouse> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<RentingHouse> mySelectList(Wrapper<RentingHouse> wrapper) {
        return rentingHouseMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RentingHouse rentingHouse) {
        rentingHouse.setUuid(ToolUtil.getUUID());
        return this.insert(rentingHouse);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RentingHouse> rentingHouseList) {
        rentingHouseList.forEach(rentingHouse -> {
            rentingHouse.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(rentingHouseList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RentingHouse rentingHouse) {
        //没有uuid的话要加上去
        if (rentingHouse.getUuid().equals(null)) {
            rentingHouse.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(rentingHouse);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RentingHouse> rentingHouseList) {
        rentingHouseList.forEach(rentingHouse -> {
            //没有uuid的话要加上去
            if (rentingHouse.getUuid().equals(null)) {
                rentingHouse.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(rentingHouseList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<RentingHouse> mySelectBatchIds(Collection<? extends Serializable> rentingHouseIds) {
        return rentingHouseMapper.selectBatchIds(rentingHouseIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public RentingHouse mySelectById(Serializable rentingHouseId) {
        return rentingHouseMapper.selectById(rentingHouseId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<RentingHouse> wrapper) {
        return rentingHouseMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public RentingHouse mySelectOne(Wrapper<RentingHouse> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<RentingHouse> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RentingHouse rentingHouse, Wrapper<RentingHouse> wrapper) {
        return this.update(rentingHouse, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RentingHouse> rentingHouseList) {
        return this.updateBatchById(rentingHouseList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RentingHouse rentingHouse) {
        return this.updateById(rentingHouse);
    }
}
