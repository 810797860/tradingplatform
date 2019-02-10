package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.shiro.MenuButton;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleButton;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.ButtonMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.ButtonService;
import com.secondhand.tradingplatformadminservice.service.shiro.MenuButtonService;
import com.secondhand.tradingplatformadminservice.service.shiro.RoleButtonService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
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
 *   @description : Button 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-04
 */

@Service
@CacheConfig(cacheNames = "button")
public class ButtonServiceImpl extends BaseServiceImpl<ButtonMapper, Button> implements ButtonService {

    @Autowired
    private ButtonMapper buttonMapper;

    @Autowired
    private MenuButtonService menuButtonService;

    @Autowired
    private RoleButtonService roleButtonService;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long buttonId) {
        Button button = new Button();
        button.setId(buttonId);
        button.setDeleted(true);
        return buttonMapper.updateById(button);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> buttonIds) {
        buttonIds.forEach(buttonId->{
            myFakeDeleteById(buttonId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long buttonId) {
        return buttonMapper.selectMapById(buttonId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"button", "roleButton"}, allEntries = true)
    public Button myButtonCreateUpdate(Button button) {
        Long buttonId = button.getId();
        if (buttonId == null){
            button.setUuid(ToolUtil.getUUID());
            Integer insertId = buttonMapper.insert(button);

            //默认给管理员也加上去
            RoleButton roleButton = new RoleButton();
            roleButton.setButtonId(Long.valueOf(insertId));
            roleButton.setRoleId(MagicalValue.ADMINISTRATOR_ID);
            roleButton.setUuid(ToolUtil.getUUID());
            roleButtonService.myInsert(roleButton);
        } else {
            buttonMapper.updateById(button);
        }
        return button;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts + ',' + #p1.description")
    public Page<Button> mySelectPageWithParam(Page<Button> page, Button button) {

        //判空
        button.setDeleted(false);
        Wrapper<Button> wrapper = new EntityWrapper<>(button);

        //字符串模糊匹配
        wrapper.like("title", button.getTitle(), SqlLike.DEFAULT);
        button.setTitle(null);
        wrapper.like("description", button.getDescription(), SqlLike.DEFAULT);
        button.setDescription(null);
        //遍历排序
        List<Sort> sorts = button.getSorts();
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
    @Cacheable(key = "#p0 + '' + #p0.description + #p1")
    public List<Button> mySelectListWithParamWithMenuId(Button button, Long menuId) {

        button.setDeleted(null);
        Wrapper<Button> wrapper = new EntityWrapper<>(button);

        //判断“找自己菜单对应的按钮的情况”
        if (menuId != null){
            //找出button的ids
            Wrapper<MenuButton> menuButtonWrapper = new EntityWrapper<>();
            menuButtonWrapper.setSqlSelect("button_id");
            menuButtonWrapper.where("menu_id = {0}", menuId);
            menuButtonWrapper.where("deleted = {0}", false);
            List<Object> buttonIds = menuButtonService.mySelectObjs(menuButtonWrapper);
            //如果buttonIds为空，返回空的对象
            if (buttonIds.size() == 0){
                return new ArrayList<>();
            }
            //放进去搜索按钮的条件里
            wrapper.in("id", buttonIds);
        }

        //字符串模糊匹配
        wrapper.like("title", button.getTitle(), SqlLike.DEFAULT);
        button.setTitle(null);
        wrapper.like("description", button.getDescription(), SqlLike.DEFAULT);
        button.setDescription(null);
        wrapper.orderBy("id", false);
        return this.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "#p0 + '' + #p0.description + #p1")
    public List<Button> mySelectListWithParamWithRoleId(Button button, Long roleId) {

        button.setDeleted(null);
        Wrapper<Button> wrapper = new EntityWrapper<>(button);

        //判断“找自己角色对应的按钮的情况”
        if (roleId != null){
            //找出button的ids
            Wrapper<RoleButton> roleButtonWrapper = new EntityWrapper<>();
            roleButtonWrapper.setSqlSelect("button_id");
            roleButtonWrapper.where("role_id = {0}", roleId);
            roleButtonWrapper.where("deleted = {0}", false);
            List<Object> buttonIds = roleButtonService.mySelectObjs(roleButtonWrapper);
            //如果buttonIds为空，返回空的对象
            if (buttonIds.size() == 0){
                return new ArrayList<>();
            }
            //放进去搜索按钮的条件里
            wrapper.in("id", buttonIds);
        }

        //字符串模糊匹配
        wrapper.like("title", button.getTitle(), SqlLike.DEFAULT);
        button.setTitle(null);
        wrapper.like("description", button.getDescription(), SqlLike.DEFAULT);
        button.setDescription(null);
        wrapper.orderBy("id", false);
        return this.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Button> mySelectListWithMap(Map<String, Object> map) {
        return buttonMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Button> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Button> mySelectList(Wrapper<Button> wrapper) {
        return buttonMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Button button) {
        button.setUuid(ToolUtil.getUUID());
        return this.insert(button);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Button> buttonList) {
        buttonList.forEach(button -> {
            button.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(buttonList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Button button) {
        //没有uuid的话要加上去
        if (button.getUuid().equals(null)){
            button.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(button);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Button> buttonList) {
        buttonList.forEach(button -> {
            //没有uuid的话要加上去
            if (button.getUuid().equals(null)){
                button.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(buttonList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Button> mySelectBatchIds(Collection<? extends Serializable> buttonIds) {
        return buttonMapper.selectBatchIds(buttonIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Button mySelectById(Serializable buttonId) {
        return buttonMapper.selectById(buttonId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Button> wrapper) {
        return buttonMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Button mySelectOne(Wrapper<Button> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Button button, Wrapper<Button> wrapper) {
        return this.update(button, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Button> buttonList) {
        return this.updateBatchById(buttonList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Button button) {
        return this.updateById(button);
    }
}
