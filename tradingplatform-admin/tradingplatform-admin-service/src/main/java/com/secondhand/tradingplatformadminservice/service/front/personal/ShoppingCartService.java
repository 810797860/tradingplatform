package com.secondhand.tradingplatformadminservice.service.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.ShoppingCart;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : ShoppingCart 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface ShoppingCartService extends BaseService<ShoppingCart>{

    /**
     * 获取数据总量
     * @param shoppingCart
     * @return
     */
    Long mySelectTotalWithParam(ShoppingCart shoppingCart);

    /**
     * 分页获取ShoppingCart列表数据（实体类）
     * @param shoppingCart
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(ShoppingCart shoppingCart, int current, int size);
}
