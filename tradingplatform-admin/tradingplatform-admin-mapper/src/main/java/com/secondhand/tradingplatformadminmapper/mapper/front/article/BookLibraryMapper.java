package com.secondhand.tradingplatformadminmapper.mapper.front.article;

import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangjk
 * @description : BookLibraryMapper 接口
 * ---------------------------------
 * @since 2019-03-16
 */
@Repository
public interface BookLibraryMapper extends BaseDao<BookLibrary> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     *
     * @param bookLibraryId
     * @return
     */
    Map<String, Object> selectMapById(@Param("bookLibraryId") Long bookLibraryId);
}