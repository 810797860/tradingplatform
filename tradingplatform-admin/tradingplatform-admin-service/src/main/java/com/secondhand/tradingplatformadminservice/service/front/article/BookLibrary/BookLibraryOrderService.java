package com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryOrder;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : BookLibraryOrder 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface BookLibraryOrderService extends BaseService<BookLibraryOrder> {

        /**
         * 根据id进行假删除
         * @param bookLibraryOrderId
         * @return
         */
        Integer myFakeDeleteById(Long bookLibraryOrderId);

        /**
         * 根据ids进行批量假删除
         * @param bookLibraryOrderIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> bookLibraryOrderIds);

        /**
         * 获取Map数据（Obj）
         * @param bookLibraryOrderId
         * @return
         */
        Map<String, Object> mySelectMapById(Long bookLibraryOrderId);

        /**
         * 新增或修改bookLibraryOrder
         * @param bookLibraryOrder
         * @return
         */
        BookLibraryOrder myBookLibraryOrderCreateUpdate(BookLibraryOrder bookLibraryOrder);

        /**
         * 分页获取BookLibraryOrder列表数据（实体类）
         * @param page
         * @param bookLibraryOrder
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryOrder bookLibraryOrder);

        /**
         * 获取BookLibraryOrder列表数据（Map）
         * @param map
         * @return
         */
        List<BookLibraryOrder> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<BookLibraryOrder> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<BookLibraryOrder> mySelectList(Wrapper<BookLibraryOrder> wrapper);

        /**
         * 插入BookLibraryOrder
         * @param bookLibraryOrder
         * @return
         */
        boolean myInsert(BookLibraryOrder bookLibraryOrder);

        /**
         * 批量插入List<BookLibraryOrder>
         * @param bookLibraryOrderList
         * @return
         */
        boolean myInsertBatch(List<BookLibraryOrder> bookLibraryOrderList);

        /**
         * 插入或更新bookLibraryOrder
         * @param bookLibraryOrder
         * @return
         */
        boolean myInsertOrUpdate(BookLibraryOrder bookLibraryOrder);

        /**
         * 批量插入或更新List<BookLibraryOrder>
         * @param bookLibraryOrderList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<BookLibraryOrder> bookLibraryOrderList);

        /**
         * 根据bookLibraryOrderIds获取List
         * @param bookLibraryOrderIds
         * @return
         */
        List<BookLibraryOrder> mySelectBatchIds(Collection<? extends Serializable> bookLibraryOrderIds);

        /**
         * 根据bookLibraryOrderId获取BookLibraryOrder
         * @param bookLibraryOrderId
         * @return
         */
        BookLibraryOrder mySelectById(Serializable bookLibraryOrderId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<BookLibraryOrder> wrapper);

        /**
         * 根据wrapper获取BookLibraryOrder
         * @param wrapper
         * @return
         */
        BookLibraryOrder mySelectOne(Wrapper<BookLibraryOrder> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<BookLibraryOrder> wrapper);

        /**
         * 根据bookLibraryOrder和wrapper更新bookLibraryOrder
         * @param bookLibraryOrder
         * @param wrapper
         * @return
         */
        boolean myUpdate(BookLibraryOrder bookLibraryOrder, Wrapper<BookLibraryOrder> wrapper);

        /**
         * 根据bookLibraryOrderList更新bookLibraryOrder
         * @param bookLibraryOrderList
         * @return
         */
        boolean myUpdateBatchById(List<BookLibraryOrder> bookLibraryOrderList);

        /**
         * 根据bookLibraryOrderId修改bookLibraryOrder
         * @param bookLibraryOrder
         * @return
         */
        boolean myUpdateById(BookLibraryOrder bookLibraryOrder);

        /**
         * 结算
         * @param bookLibraryOrderLists
         * @param balance
         * @return
         */
        Float mySettlementByListId(List<Long> bookLibraryOrderLists, Float balance);

        /**
         * 根据订单id给相应的人发短信
         * @param bookLibraryOrderLists
         */
        void myNotifyByListId(List<Long> bookLibraryOrderLists);
}
