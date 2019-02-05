package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.MenuMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuService;
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
 *   @description : Menu 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-29
 */

@Service
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long menuId) {
        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setDeleted(true);
        return menuMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> menuIds) {
        menuIds.forEach(menuId->{
            myFakeDeleteById(menuId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long menuId) {
        return menuMapper.selectMapById(menuId);
    }

    @Override
    @CacheEvict(cacheNames = {"menu", "roleMenu"}, allEntries = true)
    public Menu myMenuCreateUpdate(Menu menu) {
        Long menuId = menu.getId();
        if (menuId == null){
            menu.setUuid(ToolUtil.getUUID());
            menuMapper.insert(menu);
        } else {
            menuMapper.updateById(menu);
        }
        return menu;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Menu> mySelectPageWithParam(Page<Menu> page, Menu menu) {

        //判空
        menu.setDeleted(false);
        Wrapper<Menu> wrapper = new EntityWrapper<>(menu);
        //遍历排序
        List<Sort> sorts = menu.getSorts();
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
    public List<Menu> mySelectListWithMap(Map<String, Object> map) {
        return menuMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Menu> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Menu> mySelectList(Wrapper<Menu> wrapper) {
        return menuMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Menu menu) {
        menu.setUuid(ToolUtil.getUUID());
        return this.insert(menu);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Menu> menuList) {
        menuList.forEach(menu -> {
            menu.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(menuList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Menu menu) {
        //没有uuid的话要加上去
        if (menu.getUuid().equals(null)){
            menu.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(menu);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Menu> menuList) {
        menuList.forEach(menu -> {
            //没有uuid的话要加上去
            if (menu.getUuid().equals(null)){
                menu.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(menuList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Menu> mySelectBatchIds(Collection<? extends Serializable> menuIds) {
        return menuMapper.selectBatchIds(menuIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Menu mySelectById(Serializable menuId) {
        return menuMapper.selectById(menuId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Menu> wrapper) {
        return menuMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Menu mySelectOne(Wrapper<Menu> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Menu menu, Wrapper<Menu> wrapper) {
        return this.update(menu, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Menu> menuList) {
        return this.updateBatchById(menuList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Menu menu) {
        return this.updateById(menu);
    }
}
