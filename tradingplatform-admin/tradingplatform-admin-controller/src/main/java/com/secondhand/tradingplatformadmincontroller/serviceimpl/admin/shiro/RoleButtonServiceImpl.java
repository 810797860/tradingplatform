package com.secondhand.tradingplatformadmincontroller.serviceimpl.admin.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.RoleButton;
import com.secondhand.tradingplatformadminmapper.mapper.admin.shiro.RoleButtonMapper;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.ButtonService;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.RoleButtonService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : RoleButton 服务实现类
 * ---------------------------------
 * @since 2018-12-04
 */

@Service
@CacheConfig(cacheNames = "roleButton")
public class RoleButtonServiceImpl extends BaseServiceImpl<RoleButtonMapper, RoleButton> implements RoleButtonService {

    @Autowired
    private RoleButtonMapper roleButtonMapper;

    @Autowired
    private ButtonService buttonService;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteByRoleButton(RoleButton roleButton) {
        Wrapper<RoleButton> wrapper = new EntityWrapper<>();
        wrapper.where("role_id = {0}", roleButton.getRoleId());
        wrapper.where("button_id = {0}", roleButton.getButtonId());
        roleButton.setDeleted(true);
        return roleButtonMapper.update(roleButton, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(Long roleId, List<Integer> buttonIds) {
        RoleButton roleButton = new RoleButton();
        roleButton.setDeleted(true);
        buttonIds.forEach(buttonId -> {
            //这里就直接遍历假删除了，不去调用myFakeDelete
            Wrapper<RoleButton> wrapper = new EntityWrapper<>();
            wrapper.where("role_id = {0}", roleId);
            wrapper.where("button_id = {0}", buttonId.longValue());
            roleButtonMapper.update(roleButton, wrapper);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Integer myUpdateRoleButton(Long roleId, List<Long> buttonIds) {

        //先删除旧的roleButton
        Wrapper<RoleButton> wrapper = new EntityWrapper<>();
        wrapper.where("role_id = {0}", roleId);
        Integer deleteResult = roleButtonMapper.delete(wrapper);

        //再更新新的进去
        buttonIds.forEach(buttonId -> {
            RoleButton roleButton = new RoleButton();
            roleButton.setRoleId(roleId);
            roleButton.setButtonId(buttonId);
            roleButton.setUuid(ToolUtil.getUUID());
            roleButtonMapper.insert(roleButton);
        });
        return deleteResult;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long roleButtonId) {
        return roleButtonMapper.selectMapById(roleButtonId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RoleButton myRoleButtonCreateUpdate(RoleButton roleButton) {
        Long roleButtonId = roleButton.getId();
        if (roleButtonId == null) {
            roleButton.setUuid(ToolUtil.getUUID());
            roleButtonMapper.insert(roleButton);
        } else {
            roleButtonMapper.updateById(roleButton);
        }
        return roleButton;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myRoleButtonBatchCreate(Long roleId, List<Integer> buttonIds) {
        buttonIds.forEach(buttonId -> {
            //这里就自己写了，为了快一点(因为都是新增)
            RoleButton roleButton = new RoleButton();
            roleButton.setUuid(ToolUtil.getUUID());
            roleButton.setRoleId(roleId);
            roleButton.setButtonId(buttonId.longValue());
            roleButtonMapper.insert(roleButton);
        });
        return true;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'MyButton' + #p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Button> mySelectPageWithParam(Page<Button> page, RoleButton roleButton) {

        //先找出buttonIds
        Wrapper<RoleButton> wrapper = new EntityWrapper<>(roleButton);
        wrapper.setSqlSelect("button_id");
        //判断是否删除
        wrapper.where("deleted = {0}", false);
        List<Object> buttonIds = this.selectObjs(wrapper);
        //如果buttonIds为空，返回空的对象
        if (buttonIds.size() == 0) {
            return new Page<>();
        }
        //再根据id找buttonPage
        Wrapper<Button> buttonWrapper = new EntityWrapper<>();
        buttonWrapper.in("id", buttonIds);
        //判空
        buttonWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = roleButton.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            buttonWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                buttonWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        //这里用service，既能redis又只能用redis
        return buttonService.selectPage(page, buttonWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'EnableCreate' + #p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Button> mySelectEnableCreatePage(Page<Button> page, RoleButton roleButton) {

        //先找出buttonIds
        Wrapper<RoleButton> wrapper = new EntityWrapper<>(roleButton);
        wrapper.setSqlSelect("button_id");
        List<Object> buttonIds = this.selectObjs(wrapper);
        //再根据id找buttonPage
        Wrapper<Button> buttonWrapper = new EntityWrapper<>();
        buttonWrapper.notIn("id", buttonIds);
        //判空
        buttonWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = roleButton.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            buttonWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                buttonWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        //这里用service，既能redis又只能用redis
        return buttonService.selectPage(page, buttonWrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<RoleButton> mySelectListWithMap(Map<String, Object> map) {
        return roleButtonMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<RoleButton> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<RoleButton> mySelectList(Wrapper<RoleButton> wrapper) {
        return roleButtonMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RoleButton roleButton) {
        roleButton.setUuid(ToolUtil.getUUID());
        return this.insert(roleButton);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RoleButton> roleButtonList) {
        roleButtonList.forEach(roleButton -> {
            roleButton.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(roleButtonList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RoleButton roleButton) {
        //没有uuid的话要加上去
        if (roleButton.getUuid().equals(null)) {
            roleButton.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(roleButton);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RoleButton> roleButtonList) {
        roleButtonList.forEach(roleButton -> {
            //没有uuid的话要加上去
            if (roleButton.getUuid().equals(null)) {
                roleButton.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(roleButtonList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<RoleButton> mySelectBatchIds(Collection<? extends Serializable> roleButtonIds) {
        return roleButtonMapper.selectBatchIds(roleButtonIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public RoleButton mySelectById(Serializable roleButtonId) {
        return roleButtonMapper.selectById(roleButtonId);
    }

    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<RoleButton> wrapper) {
        return roleButtonMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public RoleButton mySelectOne(Wrapper<RoleButton> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<RoleButton> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RoleButton roleButton, Wrapper<RoleButton> wrapper) {
        return this.update(roleButton, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RoleButton> roleButtonList) {
        return this.updateBatchById(roleButtonList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RoleButton roleButton) {
        return this.updateById(roleButton);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Button> mySelectSelectedList(Long roleId) {
        //找出buttonIds
        Wrapper<RoleButton> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("button_id");
        wrapper.where("role_id = {0}", roleId);
        wrapper.where("deleted = {0}", false);
        List<Object> buttonIds = this.selectObjs(wrapper);
        //判空
        if (buttonIds.size() == 0) {
            return new ArrayList<>();
        }
        //再根据buttonIds来找
        Wrapper<Button> buttonWrapper = new EntityWrapper<>();
        buttonWrapper.in("id", buttonIds);
        buttonWrapper.where("deleted = {0}", false);
        return buttonService.mySelectList(buttonWrapper);
    }
}
