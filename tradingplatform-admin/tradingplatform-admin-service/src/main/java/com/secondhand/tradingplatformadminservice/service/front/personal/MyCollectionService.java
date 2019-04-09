package com.secondhand.tradingplatformadminservice.service.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyCollection;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : MyCollection 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface MyCollectionService extends BaseService<MyCollection>{

    /**
     * 获取数据总量
     * @param myCollection
     * @return
     */
    Long mySelectTotalWithParam(MyCollection myCollection);

    /**
     * 分页获取MyCollection列表数据（实体类）
     * @param myCollection
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(MyCollection myCollection, int current, int size);
}
