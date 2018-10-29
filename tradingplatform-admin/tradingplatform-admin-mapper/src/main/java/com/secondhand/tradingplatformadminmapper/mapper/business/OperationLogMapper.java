
package com.secondhand.tradingplatformadminmapper.mapper.business;

import com.secondhand.tradingplatformadminentity.entity.business.OperationLog;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 *   @description : OperationLogMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-28
 */
@Repository
public interface OperationLogMapper extends BaseDao<OperationLog> {

    /**
     * 根据id进行假删除
     * @param operationLogId
     * @return
     */
    boolean fakeDeleteById(@Param("operationLogId") Long operationLogId);

    /**
     * 批量假删除
     * @param operationLogIds
     * @return
     */
    boolean fakeBatchDelete(@Param("operationLogIds") List<Long> operationLogIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param operationLogId
     * @return
     */
    @Select("select * from c_business_operation_log where id = #{operationLogId}")
    Map<String, Object> selectMapById(@Param("operationLogId") Long operationLogId);
}