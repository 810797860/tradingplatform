
package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItemMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-31
 */
@Repository
public interface SelectItemMapper extends BaseDao<SelectItem> {

    /**
     * 根据id进行假删除
     * @param selectItemId
     * @return
     */
    boolean fakeDeleteById(@Param("selectItemId") Long selectItemId);

    /**
     * 批量假删除
     * @param selectItemIds
     * @return
     */
    boolean fakeBatchDelete(@Param("selectItemIds") List<Long> selectItemIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param selectItemId
     * @return
     */
    @Select("select sbsi.id as id, sbsi.title as title, sbsi.pid as pid, (SELECT concat( '[', group_concat( concat( '{\"id\":\"', bsi.id, '\",\"pid\":\"', bsi.pid, '\",\"title\":\"', bsi.title, '\"}' ) SEPARATOR ',' ), ']' ) FROM s_base_select_item bsi WHERE find_in_set( bsi.id, sbsi.item_value ) ) AS item_value, sbsi.sort as sort, sbsi.deleted as deleted, sbsi.created_at as created_at from s_base_select_item sbsi where id = #{selectItemId}")
    Map<String, Object> selectMapById(@Param("selectItemId") Long selectItemId);
}