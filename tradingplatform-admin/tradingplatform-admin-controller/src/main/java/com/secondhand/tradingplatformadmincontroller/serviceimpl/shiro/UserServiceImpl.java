package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.UserMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : User 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-21
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

    @Override
    public User selectByUsername(String username) {
        User user = new User();
        Wrapper<User> wrapper = new EntityWrapper<>(user);
        wrapper.where("user_name = {0}", username);
        wrapper.where("deleted = {0}", false);
        List<User> userList = this.selectList(wrapper);
        if(userList.size()>0){
            return userList.get(0);
        }
        return null;
    }
}
