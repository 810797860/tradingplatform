
package com.secondhand.tradingplatformadminmapper.mapper.admin.business;

import com.secondhand.tradingplatformadminentity.entity.admin.business.LoginLog;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : LoginLogMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-28
 */
@Repository
public interface LoginLogMapper extends BaseDao<LoginLog> {

    /**
     * 根据id进行假删除
     * @param loginLogId
     * @return
     */
    boolean fakeDeleteById(@Param("loginLogId") Long loginLogId);

    /**
     * 批量假删除
     * @param loginLogIds
     * @return
     */
    boolean fakeBatchDelete(@Param("loginLogIds") List<Long> loginLogIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param loginLogId
     * @return
     */
    @Select("select * from c_business_login_log where id = #{loginLogId}")
    Map<String, Object> selectMapById(@Param("loginLogId") Long loginLogId);
}