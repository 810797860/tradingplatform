package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleMenu;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.RoleMenuMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuService;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleMenuService;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RoleMenu 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-02
 */

@Service
@CacheConfig(cacheNames = "roleMenu")
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long roleMenuId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setId(roleMenuId);
        roleMenu.setDeleted(true);
        return roleMenuMapper.updateById(roleMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> roleMenuIds) {
        roleMenuIds.forEach(roleMenuId->{
            myFakeDeleteById(roleMenuId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long roleMenuId) {
        return roleMenuMapper.selectMapById(roleMenuId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public RoleMenu myRoleMenuCreateUpdate(RoleMenu roleMenu) {
        Long roleMenuId = roleMenu.getId();
        if (roleMenuId == null){
            roleMenu.setUuid(ToolUtil.getUUID());
            roleMenuMapper.insert(roleMenu);
        } else {
            roleMenuMapper.updateById(roleMenu);
        }
        return roleMenu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'EnableCreate' + #p0 + '' + #p1 + #p1.sorts")
    public Page<Menu> mySelectEnableCreatePage(Page<Menu> page, RoleMenu roleMenu) {

        //先找出menuIds
        Wrapper<RoleMenu> wrapper = new EntityWrapper<>(roleMenu);
        wrapper.setSqlSelect("menu_id");
        List<Object> menuIds = this.selectObjs(wrapper);
        //再根据id找menuPage
        Wrapper<Menu> menuWrapper = new EntityWrapper<>();
        menuWrapper.notIn("id", menuIds);
        //判空
        menuWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = roleMenu.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            menuWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                menuWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        //这里用service，既能redis又只能用redis
        return menuService.selectPage(page, menuWrapper);
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'MyMenu' + #p0 + '' + #p1 + #p1.sorts")
    public Page<Menu> mySelectPageWithParam(Page<Menu> page, RoleMenu roleMenu) {

        //先找出menuIds
        Wrapper<RoleMenu> wrapper = new EntityWrapper<>(roleMenu);
        wrapper.setSqlSelect("menu_id");
        List<Object> menuIds = this.selectObjs(wrapper);
        //再根据id找menuPage
        Wrapper<Menu> menuWrapper = new EntityWrapper<>();
        menuWrapper.in("id", menuIds);
        //判空
        menuWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = roleMenu.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            menuWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                menuWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        //这里用service，既能redis又只能用redis
        return menuService.selectPage(page, menuWrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RoleMenu> mySelectListWithMap(Map<String, Object> map) {
        return roleMenuMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<RoleMenu> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RoleMenu> mySelectList(Wrapper<RoleMenu> wrapper) {
        return roleMenuMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(RoleMenu roleMenu) {
        roleMenu.setUuid(ToolUtil.getUUID());
        return this.insert(roleMenu);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<RoleMenu> roleMenuList) {
        roleMenuList.forEach(roleMenu -> {
            roleMenu.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(roleMenuList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(RoleMenu roleMenu) {
        //没有uuid的话要加上去
        if (roleMenu.getUuid().equals(null)){
            roleMenu.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(roleMenu);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<RoleMenu> roleMenuList) {
        roleMenuList.forEach(roleMenu -> {
            //没有uuid的话要加上去
            if (roleMenu.getUuid().equals(null)){
                roleMenu.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(roleMenuList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<RoleMenu> mySelectBatchIds(Collection<? extends Serializable> roleMenuIds) {
        return roleMenuMapper.selectBatchIds(roleMenuIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RoleMenu mySelectById(Serializable roleMenuId) {
        return roleMenuMapper.selectById(roleMenuId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<RoleMenu> wrapper) {
        return roleMenuMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public RoleMenu mySelectOne(Wrapper<RoleMenu> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(RoleMenu roleMenu, Wrapper<RoleMenu> wrapper) {
        return this.update(roleMenu, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<RoleMenu> roleMenuList) {
        return this.updateBatchById(roleMenuList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(RoleMenu roleMenu) {
        return this.updateById(roleMenu);
    }
}
