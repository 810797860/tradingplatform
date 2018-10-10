package com.secondhand.tradingplatformadmincontroller.serviceimpl;

import com.secondhand.tradingplatformadminentity.entity.User;
import com.secondhand.tradingplatformadminmapper.mapper.UserMapper;
import com.secondhand.tradingplatformadminservice.service.UserService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean fakeDeleteById(Long userId) {
        return userMapper.fakeDeleteById(userId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> userIds) {
        return userMapper.fakeBatchDelete(userIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long userId) {
        return userMapper.selectMapById(userId);
    }

    @Override
    public User userCreateUpdate(User user) {
        Long userId = user.getId();
        if (userId == null){
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
        return user;
    }
}
