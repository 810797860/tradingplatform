package com.secondhand.tradingplatformadminmapper.mapper.admin.shiro;

import com.secondhand.tradingplatformadminentity.entity.admin.shiro.RoleButton;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangjk
 * @description : RoleButtonMapper 接口
 * ---------------------------------
 * @since 2018-12-04
 */
@Repository
public interface RoleButtonMapper extends BaseDao<RoleButton> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     *
     * @param roleButtonId
     * @return
     */
    Map<String, Object> selectMapById(@Param("roleButtonId") Long roleButtonId);
}