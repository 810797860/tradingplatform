package com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RentingHouseEvaluation 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface RentingHouseEvaluationService extends BaseService<RentingHouseEvaluation> {

        /**
         * 根据id进行假删除
         * @param rentingHouseEvaluationId
         * @return
         */
        Integer myFakeDeleteById(Long rentingHouseEvaluationId);

        /**
         * 根据ids进行批量假删除
         * @param rentingHouseEvaluationIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> rentingHouseEvaluationIds);

        /**
         * 获取Map数据（Obj）
         * @param rentingHouseEvaluationId
         * @return
         */
        Map<String, Object> mySelectMapById(Long rentingHouseEvaluationId);

        /**
         * 新增或修改rentingHouseEvaluation
         * @param rentingHouseEvaluation
         * @return
         */
        RentingHouseEvaluation myRentingHouseEvaluationCreateUpdate(RentingHouseEvaluation rentingHouseEvaluation);

        /**
         * 分页获取RentingHouseEvaluation列表数据（实体类）
         * @param page
         * @param rentingHouseEvaluation
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouseEvaluation rentingHouseEvaluation);

        /**
         * 获取RentingHouseEvaluation列表数据（Map）
         * @param map
         * @return
         */
        List<RentingHouseEvaluation> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RentingHouseEvaluation> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RentingHouseEvaluation> mySelectList(Wrapper<RentingHouseEvaluation> wrapper);

        /**
         * 插入RentingHouseEvaluation
         * @param rentingHouseEvaluation
         * @return
         */
        boolean myInsert(RentingHouseEvaluation rentingHouseEvaluation);

        /**
         * 批量插入List<RentingHouseEvaluation>
         * @param rentingHouseEvaluationList
         * @return
         */
        boolean myInsertBatch(List<RentingHouseEvaluation> rentingHouseEvaluationList);

        /**
         * 插入或更新rentingHouseEvaluation
         * @param rentingHouseEvaluation
         * @return
         */
        boolean myInsertOrUpdate(RentingHouseEvaluation rentingHouseEvaluation);

        /**
         * 批量插入或更新List<RentingHouseEvaluation>
         * @param rentingHouseEvaluationList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RentingHouseEvaluation> rentingHouseEvaluationList);

        /**
         * 根据rentingHouseEvaluationIds获取List
         * @param rentingHouseEvaluationIds
         * @return
         */
        List<RentingHouseEvaluation> mySelectBatchIds(Collection<? extends Serializable> rentingHouseEvaluationIds);

        /**
         * 根据rentingHouseEvaluationId获取RentingHouseEvaluation
         * @param rentingHouseEvaluationId
         * @return
         */
        RentingHouseEvaluation mySelectById(Serializable rentingHouseEvaluationId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RentingHouseEvaluation> wrapper);

        /**
         * 根据wrapper获取RentingHouseEvaluation
         * @param wrapper
         * @return
         */
        RentingHouseEvaluation mySelectOne(Wrapper<RentingHouseEvaluation> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<RentingHouseEvaluation> wrapper);

        /**
         * 根据rentingHouseEvaluation和wrapper更新rentingHouseEvaluation
         * @param rentingHouseEvaluation
         * @param wrapper
         * @return
         */
        boolean myUpdate(RentingHouseEvaluation rentingHouseEvaluation, Wrapper<RentingHouseEvaluation> wrapper);

        /**
         * 根据rentingHouseEvaluationList更新rentingHouseEvaluation
         * @param rentingHouseEvaluationList
         * @return
         */
        boolean myUpdateBatchById(List<RentingHouseEvaluation> rentingHouseEvaluationList);

        /**
         * 根据rentingHouseEvaluationId修改rentingHouseEvaluation
         * @param rentingHouseEvaluation
         * @return
         */
        boolean myUpdateById(RentingHouseEvaluation rentingHouseEvaluation);

}
