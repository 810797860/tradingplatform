package com.secondhand.tradingplatformgeccocontroller.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformgeccoentity.entity.Test;
import com.secondhand.tradingplatformgeccomapper.mapper.TestMapper;
import com.secondhand.tradingplatformgeccoservice.service.TestService;
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
 *   @description : Test 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-21
 */

@Service
@CacheConfig(cacheNames = "test")
public class TestServiceImpl extends BaseServiceImpl<TestMapper, Test> implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long testId) {
        Test test = new Test();
        test.setId(testId);
        test.setDeleted(true);
        return testMapper.updateById(test);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> testIds) {
        testIds.forEach(testId->{
            myFakeDeleteById(testId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long testId) {
        return testMapper.selectMapById(testId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Test myTestCreateUpdate(Test test) {
        Long testId = test.getId();
        if (testId == null){
            test.setUuid(ToolUtil.getUUID());
            testMapper.insert(test);
        } else {
            testMapper.updateById(test);
        }
        return test;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, Test test) {

        //判空
        test.setDeleted(false);
        Wrapper<Test> wrapper = new EntityWrapper<>(test);
        //遍历排序
        List<Sort> sorts = test.getSorts();
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
    public List<Test> mySelectListWithMap(Map<String, Object> map) {
        return testMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<Test> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Test> mySelectList(Wrapper<Test> wrapper) {
        return testMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Test test) {
        test.setUuid(ToolUtil.getUUID());
        return this.insert(test);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Test> testList) {
        testList.forEach(test -> {
            test.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(testList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Test test) {
        //没有uuid的话要加上去
        if (test.getUuid().equals(null)){
            test.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(test);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Test> testList) {
        testList.forEach(test -> {
            //没有uuid的话要加上去
            if (test.getUuid().equals(null)){
                test.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(testList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Test> mySelectBatchIds(Collection<? extends Serializable> testIds) {
        return testMapper.selectBatchIds(testIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Test mySelectById(Serializable testId) {
        return testMapper.selectById(testId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<Test> wrapper) {
        return testMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Test mySelectOne(Wrapper<Test> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<Test> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Test test, Wrapper<Test> wrapper) {
        return this.update(test, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Test> testList) {
        return this.updateBatchById(testList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Test test) {
        return this.updateById(test);
    }
}
