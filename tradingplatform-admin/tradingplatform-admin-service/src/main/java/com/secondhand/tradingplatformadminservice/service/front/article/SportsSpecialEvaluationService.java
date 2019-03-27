package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecialEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : SportsSpecialEvaluation 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface SportsSpecialEvaluationService extends BaseService<SportsSpecialEvaluation> {

        /**
         * 根据id进行假删除
         * @param sportsSpecialEvaluationId
         * @return
         */
        Integer myFakeDeleteById(Long sportsSpecialEvaluationId);

        /**
         * 根据ids进行批量假删除
         * @param sportsSpecialEvaluationIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> sportsSpecialEvaluationIds);

        /**
         * 获取Map数据（Obj）
         * @param sportsSpecialEvaluationId
         * @return
         */
        Map<String, Object> mySelectMapById(Long sportsSpecialEvaluationId);

        /**
         * 新增或修改sportsSpecialEvaluation
         * @param sportsSpecialEvaluation
         * @return
         */
        SportsSpecialEvaluation mySportsSpecialEvaluationCreateUpdate(SportsSpecialEvaluation sportsSpecialEvaluation);

        /**
         * 分页获取SportsSpecialEvaluation列表数据（实体类）
         * @param page
         * @param sportsSpecialEvaluation
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialEvaluation sportsSpecialEvaluation);

        /**
         * 获取SportsSpecialEvaluation列表数据（Map）
         * @param map
         * @return
         */
        List<SportsSpecialEvaluation> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<SportsSpecialEvaluation> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<SportsSpecialEvaluation> mySelectList(Wrapper<SportsSpecialEvaluation> wrapper);

        /**
         * 插入SportsSpecialEvaluation
         * @param sportsSpecialEvaluation
         * @return
         */
        boolean myInsert(SportsSpecialEvaluation sportsSpecialEvaluation);

        /**
         * 批量插入List<SportsSpecialEvaluation>
         * @param sportsSpecialEvaluationList
         * @return
         */
        boolean myInsertBatch(List<SportsSpecialEvaluation> sportsSpecialEvaluationList);

        /**
         * 插入或更新sportsSpecialEvaluation
         * @param sportsSpecialEvaluation
         * @return
         */
        boolean myInsertOrUpdate(SportsSpecialEvaluation sportsSpecialEvaluation);

        /**
         * 批量插入或更新List<SportsSpecialEvaluation>
         * @param sportsSpecialEvaluationList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<SportsSpecialEvaluation> sportsSpecialEvaluationList);

        /**
         * 根据sportsSpecialEvaluationIds获取List
         * @param sportsSpecialEvaluationIds
         * @return
         */
        List<SportsSpecialEvaluation> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialEvaluationIds);

        /**
         * 根据sportsSpecialEvaluationId获取SportsSpecialEvaluation
         * @param sportsSpecialEvaluationId
         * @return
         */
        SportsSpecialEvaluation mySelectById(Serializable sportsSpecialEvaluationId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<SportsSpecialEvaluation> wrapper);

        /**
         * 根据wrapper获取SportsSpecialEvaluation
         * @param wrapper
         * @return
         */
        SportsSpecialEvaluation mySelectOne(Wrapper<SportsSpecialEvaluation> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<SportsSpecialEvaluation> wrapper);

        /**
         * 根据sportsSpecialEvaluation和wrapper更新sportsSpecialEvaluation
         * @param sportsSpecialEvaluation
         * @param wrapper
         * @return
         */
        boolean myUpdate(SportsSpecialEvaluation sportsSpecialEvaluation, Wrapper<SportsSpecialEvaluation> wrapper);

        /**
         * 根据sportsSpecialEvaluationList更新sportsSpecialEvaluation
         * @param sportsSpecialEvaluationList
         * @return
         */
        boolean myUpdateBatchById(List<SportsSpecialEvaluation> sportsSpecialEvaluationList);

        /**
         * 根据sportsSpecialEvaluationId修改sportsSpecialEvaluation
         * @param sportsSpecialEvaluation
         * @return
         */
        boolean myUpdateById(SportsSpecialEvaluation sportsSpecialEvaluation);

}
