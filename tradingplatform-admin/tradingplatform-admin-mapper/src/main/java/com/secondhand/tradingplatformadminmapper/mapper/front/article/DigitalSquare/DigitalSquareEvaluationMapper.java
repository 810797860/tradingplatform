package com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare;

import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquareEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : DigitalSquareEvaluationMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface DigitalSquareEvaluationMapper extends BaseDao<DigitalSquareEvaluation> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param digitalSquareEvaluationId
     * @return
     */
    Map<String, Object> selectMapById(@Param("digitalSquareEvaluationId") Long digitalSquareEvaluationId);
}