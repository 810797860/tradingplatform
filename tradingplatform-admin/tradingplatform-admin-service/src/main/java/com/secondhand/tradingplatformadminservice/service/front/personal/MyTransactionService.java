package com.secondhand.tradingplatformadminservice.service.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyTransaction;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : MyTransaction 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface MyTransactionService extends BaseService<MyTransaction>{

    /**
     * 获取数据总量
     * @param myTransaction
     * @return
     */
    Long mySelectTotalWithParam(MyTransaction myTransaction);

    /**
     * 分页获取MyTransaction列表数据（实体类）
     * @param myTransaction
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectSaleListWithParam(MyTransaction myTransaction, int current, int size);

    /**
     * 获取数据总量
     * @param myTransaction
     * @return
     */
    Long mySelectSaleTotalWithParam(MyTransaction myTransaction);

    /**
     * 分页获取MyTransaction列表数据（实体类）
     * @param myTransaction
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(MyTransaction myTransaction, int current, int size);
}
