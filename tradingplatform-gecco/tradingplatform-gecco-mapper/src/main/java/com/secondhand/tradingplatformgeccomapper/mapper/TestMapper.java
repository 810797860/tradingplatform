package com.secondhand.tradingplatformgeccomapper.mapper;

import com.secondhand.tradingplatformgeccoentity.entity.Test;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : TestMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-21
 */
@Repository
public interface TestMapper extends BaseDao<Test> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param testId
     * @return
     */
    Map<String, Object> selectMapById(@Param("testId") Long testId);
}