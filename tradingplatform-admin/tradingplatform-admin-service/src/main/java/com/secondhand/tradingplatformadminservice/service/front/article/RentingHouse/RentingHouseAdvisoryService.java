package com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseAdvisory;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RentingHouseAdvisory 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface RentingHouseAdvisoryService extends BaseService<RentingHouseAdvisory> {

        /**
         * 根据id进行假删除
         * @param rentingHouseAdvisoryId
         * @return
         */
        Integer myFakeDeleteById(Long rentingHouseAdvisoryId);

        /**
         * 根据ids进行批量假删除
         * @param rentingHouseAdvisoryIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> rentingHouseAdvisoryIds);

        /**
         * 获取Map数据（Obj）
         * @param rentingHouseAdvisoryId
         * @return
         */
        Map<String, Object> mySelectMapById(Long rentingHouseAdvisoryId);

        /**
         * 新增或修改rentingHouseAdvisory
         * @param rentingHouseAdvisory
         * @return
         */
        RentingHouseAdvisory myRentingHouseAdvisoryCreateUpdate(RentingHouseAdvisory rentingHouseAdvisory);

        /**
         * 分页获取RentingHouseAdvisory列表数据（实体类）
         * @param page
         * @param rentingHouseAdvisory
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseAdvisory rentingHouseAdvisory);

        /**
         * 获取RentingHouseAdvisory列表数据（Map）
         * @param map
         * @return
         */
        List<RentingHouseAdvisory> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RentingHouseAdvisory> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RentingHouseAdvisory> mySelectList(Wrapper<RentingHouseAdvisory> wrapper);

        /**
         * 插入RentingHouseAdvisory
         * @param rentingHouseAdvisory
         * @return
         */
        boolean myInsert(RentingHouseAdvisory rentingHouseAdvisory);

        /**
         * 批量插入List<RentingHouseAdvisory>
         * @param rentingHouseAdvisoryList
         * @return
         */
        boolean myInsertBatch(List<RentingHouseAdvisory> rentingHouseAdvisoryList);

        /**
         * 插入或更新rentingHouseAdvisory
         * @param rentingHouseAdvisory
         * @return
         */
        boolean myInsertOrUpdate(RentingHouseAdvisory rentingHouseAdvisory);

        /**
         * 批量插入或更新List<RentingHouseAdvisory>
         * @param rentingHouseAdvisoryList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RentingHouseAdvisory> rentingHouseAdvisoryList);

        /**
         * 根据rentingHouseAdvisoryIds获取List
         * @param rentingHouseAdvisoryIds
         * @return
         */
        List<RentingHouseAdvisory> mySelectBatchIds(Collection<? extends Serializable> rentingHouseAdvisoryIds);

        /**
         * 根据rentingHouseAdvisoryId获取RentingHouseAdvisory
         * @param rentingHouseAdvisoryId
         * @return
         */
        RentingHouseAdvisory mySelectById(Serializable rentingHouseAdvisoryId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RentingHouseAdvisory> wrapper);

        /**
         * 根据wrapper获取RentingHouseAdvisory
         * @param wrapper
         * @return
         */
        RentingHouseAdvisory mySelectOne(Wrapper<RentingHouseAdvisory> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<RentingHouseAdvisory> wrapper);

        /**
         * 根据rentingHouseAdvisory和wrapper更新rentingHouseAdvisory
         * @param rentingHouseAdvisory
         * @param wrapper
         * @return
         */
        boolean myUpdate(RentingHouseAdvisory rentingHouseAdvisory, Wrapper<RentingHouseAdvisory> wrapper);

        /**
         * 根据rentingHouseAdvisoryList更新rentingHouseAdvisory
         * @param rentingHouseAdvisoryList
         * @return
         */
        boolean myUpdateBatchById(List<RentingHouseAdvisory> rentingHouseAdvisoryList);

        /**
         * 根据rentingHouseAdvisoryId修改rentingHouseAdvisory
         * @param rentingHouseAdvisory
         * @return
         */
        boolean myUpdateById(RentingHouseAdvisory rentingHouseAdvisory);

}
