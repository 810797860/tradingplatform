package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.shiro.MenuButton;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.MenuButtonMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.ButtonService;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuButtonService;
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
 *   @description : MenuButton 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-06
 */

@Service
@CacheConfig(cacheNames = "menuButton")
public class MenuButtonServiceImpl extends BaseServiceImpl<MenuButtonMapper, MenuButton> implements MenuButtonService {

    @Autowired
    private MenuButtonMapper menuButtonMapper;

    @Autowired
    private ButtonService buttonService;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteByMenuButton(MenuButton menuButton) {
        Wrapper<MenuButton> wrapper = new EntityWrapper<>();
        wrapper.where("menu_id = {0}", menuButton.getMenuId());
        wrapper.where("button_id = {0}", menuButton.getButtonId());
        menuButton.setDeleted(true);
        return menuButtonMapper.update(menuButton, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(Long menuId, List<Integer> buttonIds) {
        MenuButton menuButton = new MenuButton();
        menuButton.setDeleted(true);
        buttonIds.forEach(buttonId -> {
            //这里就直接遍历假删除了，不去调用myFakeDelete
            Wrapper<MenuButton> wrapper = new EntityWrapper<>();
            wrapper.where("menu_id = {0}", menuId);
            wrapper.where("button_id = {0}", buttonId.longValue());
            menuButtonMapper.update(menuButton, wrapper);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long menuButtonId) {
        return menuButtonMapper.selectMapById(menuButtonId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Boolean myMenuButtonCreateUpdate(List<Long> buttonIds, Long menuId) {

        //先删除该菜单的按钮
        Wrapper<MenuButton> wrapper = new EntityWrapper<>();
        wrapper.where("menu_id = {0}", menuId);
        menuButtonMapper.delete(wrapper);

        //然后再加进去
        buttonIds.forEach(buttonId->{
            //这里就自己写了，为了快一点(因为都是新增)
            MenuButton menuButton = new MenuButton();
            menuButton.setUuid(ToolUtil.getUUID());
            menuButton.setMenuId(menuId);
            menuButton.setButtonId(buttonId.longValue());
            menuButtonMapper.insert(menuButton);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myMenuButtonBatchCreate(Long menuId, List<Integer> buttonIds) {
        buttonIds.forEach(buttonId->{
            //这里就自己写了，为了快一点(因为都是新增)
            MenuButton menuButton = new MenuButton();
            menuButton.setUuid(ToolUtil.getUUID());
            menuButton.setMenuId(menuId);
            menuButton.setButtonId(buttonId.longValue());
            menuButtonMapper.insert(menuButton);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'EnableCreate' + #p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Button> mySelectEnableCreatePage(Page<Button> page, MenuButton menuButton) {

        //先找出buttonIds
        Wrapper<MenuButton> wrapper = new EntityWrapper<>(menuButton);
        wrapper.setSqlSelect("button_id");
        List<Object> buttonIds = this.selectObjs(wrapper);
        //再根据id找buttonPage
        Wrapper<Button> buttonWrapper = new EntityWrapper<>();
        buttonWrapper.notIn("id", buttonIds);
        //判空
        buttonWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = menuButton.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            buttonWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                buttonWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        //这里用service，既能redis又只能用redis
        return buttonService.selectPage(page, buttonWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "#p0")
    public List<Button> mySelectListWithMenuId(Long menuId) {

        //先找出buttonIds
        Wrapper<MenuButton> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("button_id");
        wrapper.where("menu_id = {0}", menuId);
        wrapper.where("deleted = {0}", false);
        List<Object> buttonIds = this.selectObjs(wrapper);
        //如果buttonIds为空，返回空的对象
        if (buttonIds.size() == 0){
            return new ArrayList<>();
        }
        //再根据id找List<Button>
        Wrapper<Button> buttonWrapper = new EntityWrapper<>();
        buttonWrapper.in("id", buttonIds);
        //判空
        buttonWrapper.where("deleted = {0}", false);
        return buttonService.mySelectList(buttonWrapper);
    }

    //以下是继承BaseServiceImpl

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key = "'MyButton' + #p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Button> mySelectPageWithParam(Page<Button> page, MenuButton menuButton) {

        //先找出buttonIds
        Wrapper<MenuButton> wrapper = new EntityWrapper<>(menuButton);
        wrapper.setSqlSelect("button_id");
        List<Object> buttonIds = this.selectObjs(wrapper);
        //如果buttonIds为空，返回空的对象
        if (buttonIds.size() == 0){
            return new Page<>();
        }
        //再根据id找buttonPage
        Wrapper<Button> buttonWrapper = new EntityWrapper<>();
        buttonWrapper.in("id", buttonIds);
        //判空
        buttonWrapper.where("deleted = {0}", false);
        //遍历排序
        List<Sort> sorts = menuButton.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            buttonWrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                buttonWrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        //这里用service，既能redis又只能用redis
        return buttonService.selectPage(page, buttonWrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<MenuButton> mySelectListWithMap(Map<String, Object> map) {
        return menuButtonMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<MenuButton> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<MenuButton> mySelectList(Wrapper<MenuButton> wrapper) {
        return menuButtonMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(MenuButton menuButton) {
        menuButton.setUuid(ToolUtil.getUUID());
        return this.insert(menuButton);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<MenuButton> menuButtonList) {
        menuButtonList.forEach(menuButton -> {
            menuButton.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(menuButtonList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(MenuButton menuButton) {
        //没有uuid的话要加上去
        if (menuButton.getUuid().equals(null)){
            menuButton.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(menuButton);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<MenuButton> menuButtonList) {
        menuButtonList.forEach(menuButton -> {
            //没有uuid的话要加上去
            if (menuButton.getUuid().equals(null)){
                menuButton.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(menuButtonList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<MenuButton> mySelectBatchIds(Collection<? extends Serializable> menuButtonIds) {
        return menuButtonMapper.selectBatchIds(menuButtonIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public MenuButton mySelectById(Serializable menuButtonId) {
        return menuButtonMapper.selectById(menuButtonId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<MenuButton> wrapper) {
        return menuButtonMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public MenuButton mySelectOne(Wrapper<MenuButton> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(MenuButton menuButton, Wrapper<MenuButton> wrapper) {
        return this.update(menuButton, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<MenuButton> menuButtonList) {
        return this.updateBatchById(menuButtonList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(MenuButton menuButton) {
        return this.updateById(menuButton);
    }
}
