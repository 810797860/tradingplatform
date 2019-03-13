package com.secondhand.tradingplatformadminmapper.mapper.admin.system;

import com.secondhand.tradingplatformadminentity.entity.admin.system.SelectItem;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItemMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-02-05
 */
@Repository
public interface SelectItemMapper extends BaseDao<SelectItem> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param selectItemId
     * @return
     */
    Map<String, Object> selectMapById(@Param("selectItemId") Long selectItemId);

    /**
     * 根据pid获取子级枚举列表
     * @param pid
     * @return
     */
    List<SelectItem> myGetItemsByPid(@Param("pid") Long pid);

    /**
     * 根据pid递归获取所有子级枚举列表
     * @param pid
     * @return
     */
    List<SelectItem> myGetAllItemsByPid(@Param("pid") Long pid);
}