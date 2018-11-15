package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.UserMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : User 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-13
 */

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setDeleted(true);
        return userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> userIds) {
        userIds.forEach(userId->{
            myFakeDeleteById(userId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long userId) {
        return userMapper.selectMapById(userId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public User myUserCreateUpdate(User user) {
        Long userId = user.getId();
        if (userId == null){
            user.setUuid(ToolUtil.getUUID());
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
        return user;
    }

    @Override
    @Cacheable(key = "#p0")
    public User selectByUsername(String username) {
        User user = new User();
        Wrapper<User> wrapper = new EntityWrapper<>(user);
        wrapper.where("user_name = {0}", username);
        wrapper.where("deleted = {0}", false);
        List<User> userList = userMapper.selectList(wrapper);
        if(userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + '' + #p1")
    public Page<User> mySelectPageWithParam(Page<User> page, User user) {
        Wrapper<User> wrapper = new EntityWrapper<>(user);
        return this.selectPage(page, wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<User> mySelectListWithMap(Map<String, Object> map) {
        return userMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<User> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<User> mySelectList(Wrapper<User> wrapper) {
        return userMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(User user) {
        user.setUuid(ToolUtil.getUUID());
        return this.insert(user);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<User> userList) {
        userList.forEach(user -> {
            user.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(userList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(User user) {
        //没有uuid的话要加上去
        if (user.getUuid().equals(null)){
            user.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(user);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<User> userList) {
        userList.forEach(user -> {
            //没有uuid的话要加上去
            if (user.getUuid().equals(null)){
                user.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(userList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<User> mySelectBatchIds(Collection<? extends Serializable> userIds) {
        return userMapper.selectBatchIds(userIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public User mySelectById(Serializable userId) {
        return userMapper.selectById(userId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<User> wrapper) {
        return userMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public User mySelectOne(Wrapper<User> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(User user, Wrapper<User> wrapper) {
        return this.update(user, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<User> userList) {
        return this.updateBatchById(userList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(User user) {
        return this.updateById(user);
    }
}
