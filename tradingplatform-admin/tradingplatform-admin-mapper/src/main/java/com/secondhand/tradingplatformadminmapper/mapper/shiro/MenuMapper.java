package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : MenuMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-29
 */
@Repository
public interface MenuMapper extends BaseDao<Menu> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param menuId
     * @return
     */
    Map<String, Object> selectMapById(@Param("menuId") Long menuId);
}