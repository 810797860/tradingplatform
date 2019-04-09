package com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : ElectricApplianceEvaluation 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface ElectricApplianceEvaluationService extends BaseService<ElectricApplianceEvaluation> {

        /**
         * 根据id进行假删除
         * @param electricApplianceEvaluationId
         * @return
         */
        Integer myFakeDeleteById(Long electricApplianceEvaluationId);

        /**
         * 根据ids进行批量假删除
         * @param electricApplianceEvaluationIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> electricApplianceEvaluationIds);

        /**
         * 获取Map数据（Obj）
         * @param electricApplianceEvaluationId
         * @return
         */
        Map<String, Object> mySelectMapById(Long electricApplianceEvaluationId);

        /**
         * 新增或修改electricApplianceEvaluation
         * @param electricApplianceEvaluation
         * @return
         */
        ElectricApplianceEvaluation myElectricApplianceEvaluationCreateUpdate(ElectricApplianceEvaluation electricApplianceEvaluation);

        /**
         * 分页获取ElectricApplianceEvaluation列表数据（实体类）
         * @param page
         * @param electricApplianceEvaluation
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceEvaluation electricApplianceEvaluation);

        /**
         * 获取ElectricApplianceEvaluation列表数据（Map）
         * @param map
         * @return
         */
        List<ElectricApplianceEvaluation> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<ElectricApplianceEvaluation> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<ElectricApplianceEvaluation> mySelectList(Wrapper<ElectricApplianceEvaluation> wrapper);

        /**
         * 插入ElectricApplianceEvaluation
         * @param electricApplianceEvaluation
         * @return
         */
        boolean myInsert(ElectricApplianceEvaluation electricApplianceEvaluation);

        /**
         * 批量插入List<ElectricApplianceEvaluation>
         * @param electricApplianceEvaluationList
         * @return
         */
        boolean myInsertBatch(List<ElectricApplianceEvaluation> electricApplianceEvaluationList);

        /**
         * 插入或更新electricApplianceEvaluation
         * @param electricApplianceEvaluation
         * @return
         */
        boolean myInsertOrUpdate(ElectricApplianceEvaluation electricApplianceEvaluation);

        /**
         * 批量插入或更新List<ElectricApplianceEvaluation>
         * @param electricApplianceEvaluationList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<ElectricApplianceEvaluation> electricApplianceEvaluationList);

        /**
         * 根据electricApplianceEvaluationIds获取List
         * @param electricApplianceEvaluationIds
         * @return
         */
        List<ElectricApplianceEvaluation> mySelectBatchIds(Collection<? extends Serializable> electricApplianceEvaluationIds);

        /**
         * 根据electricApplianceEvaluationId获取ElectricApplianceEvaluation
         * @param electricApplianceEvaluationId
         * @return
         */
        ElectricApplianceEvaluation mySelectById(Serializable electricApplianceEvaluationId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<ElectricApplianceEvaluation> wrapper);

        /**
         * 根据wrapper获取ElectricApplianceEvaluation
         * @param wrapper
         * @return
         */
        ElectricApplianceEvaluation mySelectOne(Wrapper<ElectricApplianceEvaluation> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<ElectricApplianceEvaluation> wrapper);

        /**
         * 根据electricApplianceEvaluation和wrapper更新electricApplianceEvaluation
         * @param electricApplianceEvaluation
         * @param wrapper
         * @return
         */
        boolean myUpdate(ElectricApplianceEvaluation electricApplianceEvaluation, Wrapper<ElectricApplianceEvaluation> wrapper);

        /**
         * 根据electricApplianceEvaluationList更新electricApplianceEvaluation
         * @param electricApplianceEvaluationList
         * @return
         */
        boolean myUpdateBatchById(List<ElectricApplianceEvaluation> electricApplianceEvaluationList);

        /**
         * 根据electricApplianceEvaluationId修改electricApplianceEvaluation
         * @param electricApplianceEvaluation
         * @return
         */
        boolean myUpdateById(ElectricApplianceEvaluation electricApplianceEvaluation);

}
