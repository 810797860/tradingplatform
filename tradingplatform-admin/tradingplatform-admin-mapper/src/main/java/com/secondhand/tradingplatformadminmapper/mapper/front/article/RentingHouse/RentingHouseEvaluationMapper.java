package com.secondhand.tradingplatformadminmapper.mapper.front.article.RentingHouse;

import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouseEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : RentingHouseEvaluationMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface RentingHouseEvaluationMapper extends BaseDao<RentingHouseEvaluation> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param rentingHouseEvaluationId
     * @return
     */
    Map<String, Object> selectMapById(@Param("rentingHouseEvaluationId") Long rentingHouseEvaluationId);
}