package com.secondhand.tradingplatformadminmapper.mapper.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.ShoppingCart;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : ShoppingCartMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Repository
public interface ShoppingCartMapper extends BaseDao<ShoppingCart>{

    Long mySelectTotalWithParam(@Param("shoppingCart") ShoppingCart shoppingCart);

    /**
     * 整合个人中心ShoppingCart
     * @param shoppingCart
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(@Param("shoppingCart") ShoppingCart shoppingCart,
                                                    @Param("lowerLimit") int lowerLimit, @Param("upperLimit") int upperLimit);
}
