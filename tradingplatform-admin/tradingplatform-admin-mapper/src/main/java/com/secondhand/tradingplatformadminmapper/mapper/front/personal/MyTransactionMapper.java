package com.secondhand.tradingplatformadminmapper.mapper.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyTransaction;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : MyTransactionMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Repository
public interface MyTransactionMapper extends BaseDao<MyTransaction>{

    /**
     * 计算总数
     * @param myTransaction
     * @return
     */
    Long mySelectTotalWithParam(@Param("myTransaction") MyTransaction myTransaction);

    /**
     * 整合个人中心MyTransaction
     * @param myTransaction
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(@Param("myTransaction") MyTransaction myTransaction,
                                                    @Param("lowerLimit") int lowerLimit, @Param("upperLimit") int upperLimit);

    /**
     * 计算总数
     * @param myTransaction
     * @return
     */
    Long mySelectSaleTotalWithParam(@Param("myTransaction") MyTransaction myTransaction);

    /**
     * 整合个人中心MyTransaction
     * @param myTransaction
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    List<Map<String, Object>> mySelectSaleListWithParam(@Param("myTransaction") MyTransaction myTransaction,
                                                    @Param("lowerLimit") int lowerLimit, @Param("upperLimit") int upperLimit);
}
