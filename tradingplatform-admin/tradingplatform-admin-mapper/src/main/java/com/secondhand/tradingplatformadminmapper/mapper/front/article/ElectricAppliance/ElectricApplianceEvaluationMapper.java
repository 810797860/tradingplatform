package com.secondhand.tradingplatformadminmapper.mapper.front.article.ElectricAppliance;

import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : ElectricApplianceEvaluationMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface ElectricApplianceEvaluationMapper extends BaseDao<ElectricApplianceEvaluation> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param electricApplianceEvaluationId
     * @return
     */
    Map<String, Object> selectMapById(@Param("electricApplianceEvaluationId") Long electricApplianceEvaluationId);
}