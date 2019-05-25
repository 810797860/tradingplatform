package com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseOrder;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RentingHouseOrder 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface RentingHouseOrderService extends BaseService<RentingHouseOrder> {

        /**
         * 根据id进行假删除
         * @param rentingHouseOrderId
         * @return
         */
        Integer myFakeDeleteById(Long rentingHouseOrderId);

        /**
         * 根据ids进行批量假删除
         * @param rentingHouseOrderIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> rentingHouseOrderIds);

        /**
         * 获取Map数据（Obj）
         * @param rentingHouseOrderId
         * @return
         */
        Map<String, Object> mySelectMapById(Long rentingHouseOrderId);

        /**
         * 新增或修改rentingHouseOrder
         * @param rentingHouseOrder
         * @return
         */
        RentingHouseOrder myRentingHouseOrderCreateUpdate(RentingHouseOrder rentingHouseOrder);

        /**
         * 分页获取RentingHouseOrder列表数据（实体类）
         * @param page
         * @param rentingHouseOrder
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseOrder rentingHouseOrder);

        /**
         * 获取RentingHouseOrder列表数据（Map）
         * @param map
         * @return
         */
        List<RentingHouseOrder> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RentingHouseOrder> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RentingHouseOrder> mySelectList(Wrapper<RentingHouseOrder> wrapper);

        /**
         * 插入RentingHouseOrder
         * @param rentingHouseOrder
         * @return
         */
        boolean myInsert(RentingHouseOrder rentingHouseOrder);

        /**
         * 批量插入List<RentingHouseOrder>
         * @param rentingHouseOrderList
         * @return
         */
        boolean myInsertBatch(List<RentingHouseOrder> rentingHouseOrderList);

        /**
         * 插入或更新rentingHouseOrder
         * @param rentingHouseOrder
         * @return
         */
        boolean myInsertOrUpdate(RentingHouseOrder rentingHouseOrder);

        /**
         * 批量插入或更新List<RentingHouseOrder>
         * @param rentingHouseOrderList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RentingHouseOrder> rentingHouseOrderList);

        /**
         * 根据rentingHouseOrderIds获取List
         * @param rentingHouseOrderIds
         * @return
         */
        List<RentingHouseOrder> mySelectBatchIds(Collection<? extends Serializable> rentingHouseOrderIds);

        /**
         * 根据rentingHouseOrderId获取RentingHouseOrder
         * @param rentingHouseOrderId
         * @return
         */
        RentingHouseOrder mySelectById(Serializable rentingHouseOrderId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RentingHouseOrder> wrapper);

        /**
         * 根据wrapper获取RentingHouseOrder
         * @param wrapper
         * @return
         */
        RentingHouseOrder mySelectOne(Wrapper<RentingHouseOrder> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<RentingHouseOrder> wrapper);

        /**
         * 根据rentingHouseOrder和wrapper更新rentingHouseOrder
         * @param rentingHouseOrder
         * @param wrapper
         * @return
         */
        boolean myUpdate(RentingHouseOrder rentingHouseOrder, Wrapper<RentingHouseOrder> wrapper);

        /**
         * 根据rentingHouseOrderList更新rentingHouseOrder
         * @param rentingHouseOrderList
         * @return
         */
        boolean myUpdateBatchById(List<RentingHouseOrder> rentingHouseOrderList);

        /**
         * 根据rentingHouseOrderId修改rentingHouseOrder
         * @param rentingHouseOrder
         * @return
         */
        boolean myUpdateById(RentingHouseOrder rentingHouseOrder);

        /**
         * 结算
         * @param rentingHouseOrderLists
         * @param balance
         * @return
         */
        Float mySettlementByListId(List<Long> rentingHouseOrderLists, Float balance);

        /**
         * 根据订单id给相应的人发短信
         * @param rentingHouseOrderLists
         */
        void myNotifyByListId(List<Long> rentingHouseOrderLists);
}
