package com.secondhand.tradingplatformadminmapper.mapper.admin.shiro;

import com.secondhand.tradingplatformadminentity.entity.admin.shiro.MenuButton;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangjk
 * @description : MenuButtonMapper 接口
 * ---------------------------------
 * @since 2018-12-06
 */
@Repository
public interface MenuButtonMapper extends BaseDao<MenuButton> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     *
     * @param menuButtonId
     * @return
     */
    Map<String, Object> selectMapById(@Param("menuButtonId") Long menuButtonId);
}