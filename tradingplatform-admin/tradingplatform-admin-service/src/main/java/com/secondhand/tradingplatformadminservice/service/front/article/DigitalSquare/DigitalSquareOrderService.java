package com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareOrder;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : DigitalSquareOrder 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface DigitalSquareOrderService extends BaseService<DigitalSquareOrder> {

        /**
         * 根据id进行假删除
         * @param digitalSquareOrderId
         * @return
         */
        Integer myFakeDeleteById(Long digitalSquareOrderId);

        /**
         * 根据ids进行批量假删除
         * @param digitalSquareOrderIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> digitalSquareOrderIds);

        /**
         * 获取Map数据（Obj）
         * @param digitalSquareOrderId
         * @return
         */
        Map<String, Object> mySelectMapById(Long digitalSquareOrderId);

        /**
         * 新增或修改digitalSquareOrder
         * @param digitalSquareOrder
         * @return
         */
        DigitalSquareOrder myDigitalSquareOrderCreateUpdate(DigitalSquareOrder digitalSquareOrder);

        /**
         * 分页获取DigitalSquareOrder列表数据（实体类）
         * @param page
         * @param digitalSquareOrder
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareOrder digitalSquareOrder);

        /**
         * 获取DigitalSquareOrder列表数据（Map）
         * @param map
         * @return
         */
        List<DigitalSquareOrder> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<DigitalSquareOrder> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<DigitalSquareOrder> mySelectList(Wrapper<DigitalSquareOrder> wrapper);

        /**
         * 插入DigitalSquareOrder
         * @param digitalSquareOrder
         * @return
         */
        boolean myInsert(DigitalSquareOrder digitalSquareOrder);

        /**
         * 批量插入List<DigitalSquareOrder>
         * @param digitalSquareOrderList
         * @return
         */
        boolean myInsertBatch(List<DigitalSquareOrder> digitalSquareOrderList);

        /**
         * 插入或更新digitalSquareOrder
         * @param digitalSquareOrder
         * @return
         */
        boolean myInsertOrUpdate(DigitalSquareOrder digitalSquareOrder);

        /**
         * 批量插入或更新List<DigitalSquareOrder>
         * @param digitalSquareOrderList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<DigitalSquareOrder> digitalSquareOrderList);

        /**
         * 根据digitalSquareOrderIds获取List
         * @param digitalSquareOrderIds
         * @return
         */
        List<DigitalSquareOrder> mySelectBatchIds(Collection<? extends Serializable> digitalSquareOrderIds);

        /**
         * 根据digitalSquareOrderId获取DigitalSquareOrder
         * @param digitalSquareOrderId
         * @return
         */
        DigitalSquareOrder mySelectById(Serializable digitalSquareOrderId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<DigitalSquareOrder> wrapper);

        /**
         * 根据wrapper获取DigitalSquareOrder
         * @param wrapper
         * @return
         */
        DigitalSquareOrder mySelectOne(Wrapper<DigitalSquareOrder> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<DigitalSquareOrder> wrapper);

        /**
         * 根据digitalSquareOrder和wrapper更新digitalSquareOrder
         * @param digitalSquareOrder
         * @param wrapper
         * @return
         */
        boolean myUpdate(DigitalSquareOrder digitalSquareOrder, Wrapper<DigitalSquareOrder> wrapper);

        /**
         * 根据digitalSquareOrderList更新digitalSquareOrder
         * @param digitalSquareOrderList
         * @return
         */
        boolean myUpdateBatchById(List<DigitalSquareOrder> digitalSquareOrderList);

        /**
         * 根据digitalSquareOrderId修改digitalSquareOrder
         * @param digitalSquareOrder
         * @return
         */
        boolean myUpdateById(DigitalSquareOrder digitalSquareOrder);

        /**
         * 结算
         * @param digitalSquareOrderLists
         * @param balance
         * @return
         */
        Float mySettlementByListId(List<Long> digitalSquareOrderLists, Float balance);

        /**
         * 根据订单id给相应的人发短信
         * @param digitalSquareOrderLists
         */
        void myNotifyByListId(List<Long> digitalSquareOrderLists);
}
