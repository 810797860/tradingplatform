package com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseCollection;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RentingHouseCollection 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface RentingHouseCollectionService extends BaseService<RentingHouseCollection> {

        /**
         * 根据id进行假删除
         * @param rentingHouseCollectionId
         * @return
         */
        Integer myFakeDeleteById(Long rentingHouseCollectionId);

        /**
         * 根据ids进行批量假删除
         * @param rentingHouseCollectionIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> rentingHouseCollectionIds);

        /**
         * 获取Map数据（Obj）
         * @param rentingHouseCollectionId
         * @return
         */
        Map<String, Object> mySelectMapById(Long rentingHouseCollectionId);

        /**
         * 新增或修改rentingHouseCollection
         * @param rentingHouseCollection
         * @return
         */
        RentingHouseCollection myRentingHouseCollectionCreateUpdate(RentingHouseCollection rentingHouseCollection);

        /**
         * 分页获取RentingHouseCollection列表数据（实体类）
         * @param page
         * @param rentingHouseCollection
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseCollection rentingHouseCollection);

        /**
         * 获取RentingHouseCollection列表数据（Map）
         * @param map
         * @return
         */
        List<RentingHouseCollection> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RentingHouseCollection> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RentingHouseCollection> mySelectList(Wrapper<RentingHouseCollection> wrapper);

        /**
         * 插入RentingHouseCollection
         * @param rentingHouseCollection
         * @return
         */
        boolean myInsert(RentingHouseCollection rentingHouseCollection);

        /**
         * 批量插入List<RentingHouseCollection>
         * @param rentingHouseCollectionList
         * @return
         */
        boolean myInsertBatch(List<RentingHouseCollection> rentingHouseCollectionList);

        /**
         * 插入或更新rentingHouseCollection
         * @param rentingHouseCollection
         * @return
         */
        boolean myInsertOrUpdate(RentingHouseCollection rentingHouseCollection);

        /**
         * 批量插入或更新List<RentingHouseCollection>
         * @param rentingHouseCollectionList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RentingHouseCollection> rentingHouseCollectionList);

        /**
         * 根据rentingHouseCollectionIds获取List
         * @param rentingHouseCollectionIds
         * @return
         */
        List<RentingHouseCollection> mySelectBatchIds(Collection<? extends Serializable> rentingHouseCollectionIds);

        /**
         * 根据rentingHouseCollectionId获取RentingHouseCollection
         * @param rentingHouseCollectionId
         * @return
         */
        RentingHouseCollection mySelectById(Serializable rentingHouseCollectionId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RentingHouseCollection> wrapper);

        /**
         * 根据wrapper获取RentingHouseCollection
         * @param wrapper
         * @return
         */
        RentingHouseCollection mySelectOne(Wrapper<RentingHouseCollection> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<RentingHouseCollection> wrapper);

        /**
         * 根据rentingHouseCollection和wrapper更新rentingHouseCollection
         * @param rentingHouseCollection
         * @param wrapper
         * @return
         */
        boolean myUpdate(RentingHouseCollection rentingHouseCollection, Wrapper<RentingHouseCollection> wrapper);

        /**
         * 根据rentingHouseCollectionList更新rentingHouseCollection
         * @param rentingHouseCollectionList
         * @return
         */
        boolean myUpdateBatchById(List<RentingHouseCollection> rentingHouseCollectionList);

        /**
         * 根据rentingHouseCollectionId修改rentingHouseCollection
         * @param rentingHouseCollection
         * @return
         */
        boolean myUpdateById(RentingHouseCollection rentingHouseCollection);

}
