package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.ShoppingCart;
import com.secondhand.tradingplatformadminmapper.mapper.front.personal.ShoppingCartMapper;
import com.secondhand.tradingplatformadminservice.service.front.personal.ShoppingCartService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : ShoppingCart 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Service
@CacheConfig(cacheNames = "shoppingCart")
public class ShoppingCartServiceImpl extends BaseServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Cacheable(key = "#p0")
    public Long mySelectTotalWithParam(ShoppingCart shoppingCart) {
        return shoppingCartMapper.mySelectTotalWithParam(shoppingCart);
    }

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p2")
    public List<Map<String, Object>> mySelectListWithParam(ShoppingCart shoppingCart, int current, int size) {
        //计算分页
        int lowerLimit = (current - 1) * size;
        int upperLimit = lowerLimit + size;
        List<Map<String, Object>> resList = shoppingCartMapper.mySelectListWithParam(shoppingCart, lowerLimit, upperLimit);
        return resList;
    }
}
