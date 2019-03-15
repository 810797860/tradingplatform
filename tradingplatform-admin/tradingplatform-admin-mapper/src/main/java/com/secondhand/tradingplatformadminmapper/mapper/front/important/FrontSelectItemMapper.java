package com.secondhand.tradingplatformadminmapper.mapper.front.important;

import com.secondhand.tradingplatformadminentity.entity.front.important.FrontSelectItem;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : FrontSelectItemMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-15
 */
@Repository
public interface FrontSelectItemMapper extends BaseDao<FrontSelectItem> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param frontSelectItemId
     * @return
     */
    Map<String, Object> selectMapById(@Param("frontSelectItemId") Long frontSelectItemId);

    /**
     * 根据pid获取子级枚举列表
     * @param pid
     * @return
     */
    List<FrontSelectItem> myGetItemsByPid(@Param("pid") Long pid);

    /**
     * 根据pid递归获取所有子级枚举列表
     * @param pid
     * @return
     */
    List<FrontSelectItem> myGetAllItemsByPid(@Param("pid") Long pid);
}