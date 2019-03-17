package com.secondhand.tradingplatformadminmapper.mapper.front.article;

import com.secondhand.tradingplatformadminentity.entity.front.article.OtherCategories;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangjk
 * @description : OtherCategoriesMapper 接口
 * ---------------------------------
 * @since 2019-03-17
 */
@Repository
public interface OtherCategoriesMapper extends BaseDao<OtherCategories> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     *
     * @param otherCategoriesId
     * @return
     */
    Map<String, Object> selectMapById(@Param("otherCategoriesId") Long otherCategoriesId);
}