package com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialOrder;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : SportsSpecialOrder 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface SportsSpecialOrderService extends BaseService<SportsSpecialOrder> {

        /**
         * 根据id进行假删除
         * @param sportsSpecialOrderId
         * @return
         */
        Integer myFakeDeleteById(Long sportsSpecialOrderId);

        /**
         * 根据ids进行批量假删除
         * @param sportsSpecialOrderIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> sportsSpecialOrderIds);

        /**
         * 获取Map数据（Obj）
         * @param sportsSpecialOrderId
         * @return
         */
        Map<String, Object> mySelectMapById(Long sportsSpecialOrderId);

        /**
         * 新增或修改sportsSpecialOrder
         * @param sportsSpecialOrder
         * @return
         */
        SportsSpecialOrder mySportsSpecialOrderCreateUpdate(SportsSpecialOrder sportsSpecialOrder);

        /**
         * 分页获取SportsSpecialOrder列表数据（实体类）
         * @param page
         * @param sportsSpecialOrder
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialOrder sportsSpecialOrder);

        /**
         * 获取SportsSpecialOrder列表数据（Map）
         * @param map
         * @return
         */
        List<SportsSpecialOrder> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<SportsSpecialOrder> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<SportsSpecialOrder> mySelectList(Wrapper<SportsSpecialOrder> wrapper);

        /**
         * 插入SportsSpecialOrder
         * @param sportsSpecialOrder
         * @return
         */
        boolean myInsert(SportsSpecialOrder sportsSpecialOrder);

        /**
         * 批量插入List<SportsSpecialOrder>
         * @param sportsSpecialOrderList
         * @return
         */
        boolean myInsertBatch(List<SportsSpecialOrder> sportsSpecialOrderList);

        /**
         * 插入或更新sportsSpecialOrder
         * @param sportsSpecialOrder
         * @return
         */
        boolean myInsertOrUpdate(SportsSpecialOrder sportsSpecialOrder);

        /**
         * 批量插入或更新List<SportsSpecialOrder>
         * @param sportsSpecialOrderList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<SportsSpecialOrder> sportsSpecialOrderList);

        /**
         * 根据sportsSpecialOrderIds获取List
         * @param sportsSpecialOrderIds
         * @return
         */
        List<SportsSpecialOrder> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialOrderIds);

        /**
         * 根据sportsSpecialOrderId获取SportsSpecialOrder
         * @param sportsSpecialOrderId
         * @return
         */
        SportsSpecialOrder mySelectById(Serializable sportsSpecialOrderId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<SportsSpecialOrder> wrapper);

        /**
         * 根据wrapper获取SportsSpecialOrder
         * @param wrapper
         * @return
         */
        SportsSpecialOrder mySelectOne(Wrapper<SportsSpecialOrder> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<SportsSpecialOrder> wrapper);

        /**
         * 根据sportsSpecialOrder和wrapper更新sportsSpecialOrder
         * @param sportsSpecialOrder
         * @param wrapper
         * @return
         */
        boolean myUpdate(SportsSpecialOrder sportsSpecialOrder, Wrapper<SportsSpecialOrder> wrapper);

        /**
         * 根据sportsSpecialOrderList更新sportsSpecialOrder
         * @param sportsSpecialOrderList
         * @return
         */
        boolean myUpdateBatchById(List<SportsSpecialOrder> sportsSpecialOrderList);

        /**
         * 根据sportsSpecialOrderId修改sportsSpecialOrder
         * @param sportsSpecialOrder
         * @return
         */
        boolean myUpdateById(SportsSpecialOrder sportsSpecialOrder);

        /**
         * 结算
         * @param sportsSpecialOrderLists
         * @param balance
         * @return
         */
        Float mySettlementByListId(List<Long> sportsSpecialOrderLists, Float balance);
}
