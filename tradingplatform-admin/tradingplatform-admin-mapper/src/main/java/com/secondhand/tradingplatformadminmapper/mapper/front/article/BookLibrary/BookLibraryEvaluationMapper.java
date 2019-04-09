package com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary;

import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : BookLibraryEvaluationMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface BookLibraryEvaluationMapper extends BaseDao<BookLibraryEvaluation> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param bookLibraryEvaluationId
     * @return
     */
    Map<String, Object> selectMapById(@Param("bookLibraryEvaluationId") Long bookLibraryEvaluationId);
}