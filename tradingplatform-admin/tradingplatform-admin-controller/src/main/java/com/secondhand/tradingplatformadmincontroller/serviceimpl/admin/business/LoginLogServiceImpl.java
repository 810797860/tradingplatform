package com.secondhand.tradingplatformadmincontroller.serviceimpl.admin.business;

import com.secondhand.tradingplatformadminentity.entity.admin.business.LoginLog;
import com.secondhand.tradingplatformadminmapper.mapper.admin.business.LoginLogMapper;
import com.secondhand.tradingplatformadminservice.service.admin.business.LoginLogService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : LoginLog 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-28
 */

@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public boolean fakeDeleteById(Long loginLogId) {
        return loginLogMapper.fakeDeleteById(loginLogId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> loginLogIds) {
        return loginLogMapper.fakeBatchDelete(loginLogIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long loginLogId) {
        return loginLogMapper.selectMapById(loginLogId);
    }

    @Override
    public LoginLog loginLogCreateUpdate(LoginLog loginLog) {
        Long loginLogId = loginLog.getId();
        if (loginLogId == null){
            loginLogMapper.insert(loginLog);
        } else {
            loginLogMapper.updateById(loginLog);
        }
        return loginLog;
    }
}
