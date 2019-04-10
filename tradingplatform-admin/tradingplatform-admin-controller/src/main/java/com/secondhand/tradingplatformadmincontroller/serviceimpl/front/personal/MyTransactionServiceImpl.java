package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyTransaction;
import com.secondhand.tradingplatformadminmapper.mapper.front.personal.MyTransactionMapper;
import com.secondhand.tradingplatformadminservice.service.front.personal.MyTransactionService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : MyTransaction 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Service
@CacheConfig(cacheNames = "myTransaction")
public class MyTransactionServiceImpl extends BaseServiceImpl<MyTransactionMapper, MyTransaction> implements MyTransactionService{

    @Autowired
    private MyTransactionMapper myTransactionMapper;

    @Override
    @Cacheable(key = "#p0")
    public Long mySelectTotalWithParam(MyTransaction myTransaction) {
        return myTransactionMapper.mySelectTotalWithParam(myTransaction);
    }

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p2")
    public List<Map<String, Object>> mySelectListWithParam(MyTransaction myTransaction, int current, int size) {
        //计算分页
        int lowerLimit = (current - 1) * size;
        int upperLimit = lowerLimit + size;
        List<Map<String, Object>> resList = myTransactionMapper.mySelectListWithParam(myTransaction, lowerLimit, upperLimit);
        return resList;
    }

    @Override
    @Cacheable(key = "#p0")
    public Long mySelectSaleTotalWithParam(MyTransaction myTransaction) {
        return myTransactionMapper.mySelectSaleTotalWithParam(myTransaction);
    }

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p2")
    public List<Map<String, Object>> mySelectSaleListWithParam(MyTransaction myTransaction, int current, int size) {
        //计算分页
        int lowerLimit = (current - 1) * size;
        int upperLimit = lowerLimit + size;
        List<Map<String, Object>> resList = myTransactionMapper.mySelectSaleListWithParam(myTransaction, lowerLimit, upperLimit);
        return resList;
    }
}
