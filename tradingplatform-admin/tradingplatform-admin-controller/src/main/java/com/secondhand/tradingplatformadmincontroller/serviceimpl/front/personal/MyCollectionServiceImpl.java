package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyCollection;
import com.secondhand.tradingplatformadminmapper.mapper.front.personal.MyCollectionMapper;
import com.secondhand.tradingplatformadminservice.service.front.personal.MyCollectionService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : MyCollection 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Service
@CacheConfig(cacheNames = "myCollection")
public class MyCollectionServiceImpl extends BaseServiceImpl<MyCollectionMapper, MyCollection> implements MyCollectionService{

    @Autowired
    private MyCollectionMapper myCollectionMapper;

    @Override
    @Cacheable(key = "#p0")
    public Long mySelectTotalWithParam(MyCollection myCollection) {
        return myCollectionMapper.mySelectTotalWithParam(myCollection);
    }

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p2")
    public List<Map<String, Object>> mySelectListWithParam(MyCollection myCollection, int current, int size) {
        //计算分页
        int lowerLimit = (current - 1) * size;
        int upperLimit = lowerLimit + size;
        List<Map<String, Object>> resList = myCollectionMapper.mySelectListWithParam(myCollection, lowerLimit, upperLimit);
        return resList;
    }
}
