package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadmincontroller.shiro.DesEncryptionTool;
import com.secondhand.tradingplatformadminentity.entity.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.shiro.UserRole;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.UserMapper;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.UserRoleMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.UserService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.CustomizeStatus;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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

    @Autowired
    private UserRoleMapper userRoleMapper;

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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public User myUserCreateUpdate(User user) throws CustomizeException {
        Long userId = user.getId();
        //加密
        //通用，有密码时统一加密
        String password = user.getPassword();
        if (password != null){
            user.setPassword(DesEncryptionTool.encrypt(password));
        }
        if (userId == null){
            //判断该账号名是否被人注册了
            User judgeUser = new User();
            judgeUser.setType(SystemSelectItem.USER_TYPE_BACK_DESK);
            judgeUser.setDeleted(false);
            judgeUser.setAccount(user.getAccount());
            judgeUser = userMapper.selectOne(judgeUser);
            if (!ToolUtil.objIsEmpty(judgeUser)){
                throw new CustomizeException(CustomizeStatus.ADMIN_USER_ACCOUNT_ALREADY_EXISTS, this.getClass());
            }
            user.setUuid(ToolUtil.getUUID());
            //设置为后台用户的类型
            user.setType(SystemSelectItem.USER_TYPE_BACK_DESK);
            userMapper.insert(user);

            //为用户配上默认的角色
            UserRole userRole = new UserRole();
            userRole.setRoleId(MagicalValue.DEFAULT_ROLE_ID);
            userRole.setUserId(user.getId());
            userRole.setUuid(ToolUtil.getUUID());
            userRoleMapper.insert(userRole);
        } else {
            userMapper.updateById(user);
        }
        return user;
    }

    @Override
    @Cacheable(key = "'account:' + #p0")
    public User selectByUserAccount(String account) {
        User user = new User();
        Wrapper<User> wrapper = new EntityWrapper<>(user);
        wrapper.where("account = {0}", account);
        wrapper.where("deleted = {0}", false);
        List<User> userList = userMapper.selectList(wrapper);
        if(userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<User> mySelectPageWithParam(Page<User> page, User user) {
        Wrapper<User> wrapper = new EntityWrapper<>(user);
        //遍历排序
        List<Sort> sorts = user.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
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
