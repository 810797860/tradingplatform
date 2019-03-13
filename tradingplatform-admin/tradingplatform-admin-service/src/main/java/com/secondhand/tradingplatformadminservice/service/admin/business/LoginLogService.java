package com.secondhand.tradingplatformadminservice.service.admin.business;

import com.secondhand.tradingplatformadminentity.entity.admin.business.LoginLog;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : LoginLog 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-28
 */
public interface LoginLogService extends BaseService<LoginLog> {

        /**
         * 根据id进行假删除
         * @param loginLogId
         * @return
         */
        boolean fakeDeleteById(Long loginLogId);

        /**
         * 根据ids进行批量假删除
         * @param loginLogIds
         * @return
         */
        boolean fakeBatchDelete(List<Long> loginLogIds);

        /**
         * 获取Map数据（Obj）
         * @param loginLogId
         * @return
         */
        Map<String, Object> selectMapById(Long loginLogId);

        /**
         * 新增或修改loginLog
         * @param loginLog
         * @return
         */
        LoginLog loginLogCreateUpdate(LoginLog loginLog);

}
