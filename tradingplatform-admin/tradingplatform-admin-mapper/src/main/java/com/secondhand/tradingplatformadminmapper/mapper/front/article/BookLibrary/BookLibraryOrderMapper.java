package com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary;

import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryOrder;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : BookLibraryOrderMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@Repository
public interface BookLibraryOrderMapper extends BaseDao<BookLibraryOrder> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param bookLibraryOrderId
     * @return
     */
    Map<String, Object> selectMapById(@Param("bookLibraryOrderId") Long bookLibraryOrderId);
}