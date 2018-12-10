package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.MenuButton;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *   @description : MenuButtonMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-06
 */
@Repository
public interface MenuButtonMapper extends BaseDao<MenuButton> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param menuButtonId
     * @return
     */
    Map<String, Object> selectMapById(@Param("menuButtonId") Long menuButtonId);
}