package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.RoleMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : Role 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-21
 */

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean fakeDeleteById(Long roleId) {
        return roleMapper.fakeDeleteById(roleId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> roleIds) {
        return roleMapper.fakeBatchDelete(roleIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long roleId) {
        Role role = new Role();
        return roleMapper.selectMapById(roleId);
    }

    @Override
    public Role roleCreateUpdate(Role role) {
        Long roleId = role.getId();
        if (roleId == null){
            roleMapper.insert(role);
        } else {
            roleMapper.updateById(role);
        }
        return role;
    }
}
