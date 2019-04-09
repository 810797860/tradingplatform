package com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : DigitalSquareEvaluation 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface DigitalSquareEvaluationService extends BaseService<DigitalSquareEvaluation> {

        /**
         * 根据id进行假删除
         * @param digitalSquareEvaluationId
         * @return
         */
        Integer myFakeDeleteById(Long digitalSquareEvaluationId);

        /**
         * 根据ids进行批量假删除
         * @param digitalSquareEvaluationIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> digitalSquareEvaluationIds);

        /**
         * 获取Map数据（Obj）
         * @param digitalSquareEvaluationId
         * @return
         */
        Map<String, Object> mySelectMapById(Long digitalSquareEvaluationId);

        /**
         * 新增或修改digitalSquareEvaluation
         * @param digitalSquareEvaluation
         * @return
         */
        DigitalSquareEvaluation myDigitalSquareEvaluationCreateUpdate(DigitalSquareEvaluation digitalSquareEvaluation);

        /**
         * 分页获取DigitalSquareEvaluation列表数据（实体类）
         * @param page
         * @param digitalSquareEvaluation
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareEvaluation digitalSquareEvaluation);

        /**
         * 获取DigitalSquareEvaluation列表数据（Map）
         * @param map
         * @return
         */
        List<DigitalSquareEvaluation> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<DigitalSquareEvaluation> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<DigitalSquareEvaluation> mySelectList(Wrapper<DigitalSquareEvaluation> wrapper);

        /**
         * 插入DigitalSquareEvaluation
         * @param digitalSquareEvaluation
         * @return
         */
        boolean myInsert(DigitalSquareEvaluation digitalSquareEvaluation);

        /**
         * 批量插入List<DigitalSquareEvaluation>
         * @param digitalSquareEvaluationList
         * @return
         */
        boolean myInsertBatch(List<DigitalSquareEvaluation> digitalSquareEvaluationList);

        /**
         * 插入或更新digitalSquareEvaluation
         * @param digitalSquareEvaluation
         * @return
         */
        boolean myInsertOrUpdate(DigitalSquareEvaluation digitalSquareEvaluation);

        /**
         * 批量插入或更新List<DigitalSquareEvaluation>
         * @param digitalSquareEvaluationList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<DigitalSquareEvaluation> digitalSquareEvaluationList);

        /**
         * 根据digitalSquareEvaluationIds获取List
         * @param digitalSquareEvaluationIds
         * @return
         */
        List<DigitalSquareEvaluation> mySelectBatchIds(Collection<? extends Serializable> digitalSquareEvaluationIds);

        /**
         * 根据digitalSquareEvaluationId获取DigitalSquareEvaluation
         * @param digitalSquareEvaluationId
         * @return
         */
        DigitalSquareEvaluation mySelectById(Serializable digitalSquareEvaluationId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<DigitalSquareEvaluation> wrapper);

        /**
         * 根据wrapper获取DigitalSquareEvaluation
         * @param wrapper
         * @return
         */
        DigitalSquareEvaluation mySelectOne(Wrapper<DigitalSquareEvaluation> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<DigitalSquareEvaluation> wrapper);

        /**
         * 根据digitalSquareEvaluation和wrapper更新digitalSquareEvaluation
         * @param digitalSquareEvaluation
         * @param wrapper
         * @return
         */
        boolean myUpdate(DigitalSquareEvaluation digitalSquareEvaluation, Wrapper<DigitalSquareEvaluation> wrapper);

        /**
         * 根据digitalSquareEvaluationList更新digitalSquareEvaluation
         * @param digitalSquareEvaluationList
         * @return
         */
        boolean myUpdateBatchById(List<DigitalSquareEvaluation> digitalSquareEvaluationList);

        /**
         * 根据digitalSquareEvaluationId修改digitalSquareEvaluation
         * @param digitalSquareEvaluation
         * @return
         */
        boolean myUpdateById(DigitalSquareEvaluation digitalSquareEvaluation);

}
