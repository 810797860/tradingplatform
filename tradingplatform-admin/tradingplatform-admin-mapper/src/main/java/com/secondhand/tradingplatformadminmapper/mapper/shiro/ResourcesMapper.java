package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : ResourcesMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-12
 */
@Repository
public interface ResourcesMapper extends BaseDao<Resources> {

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param resourcesId
     * @return
     */
    Map<String, Object> selectMapById(@Param("resourcesId") Long resourcesId);

    /**
     * 加载所有的user_resources(MyShiroRealm)
     * @param userId
     * @return
     */
    List<Resources> loadUserResources(@Param("userId") Long userId);
}