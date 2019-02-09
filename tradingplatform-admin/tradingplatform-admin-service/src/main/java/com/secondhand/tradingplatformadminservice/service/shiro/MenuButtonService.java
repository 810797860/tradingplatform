package com.secondhand.tradingplatformadminservice.service.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.shiro.MenuButton;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : MenuButton 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-06
 */
public interface MenuButtonService extends BaseService<MenuButton> {

        /**
         * 根据id进行假删除
         * @param menuButton
         * @return
         */
        Integer myFakeDeleteByMenuButton(MenuButton menuButton);

        /**
         * 根据ids进行批量假删除
         * @param menuId
         * @param buttonIds
         * @return
         */
        boolean myFakeBatchDelete(Long menuId, List<Integer> buttonIds);

        /**
         * 获取Map数据（Obj）
         * @param menuButtonId
         * @return
         */
        Map<String, Object> mySelectMapById(Long menuButtonId);

        /**
         * 配置菜单-按钮
         * @param buttonIds
         * @param menuId
         * @return
         */
        Boolean myMenuButtonCreateUpdate(List<Long> buttonIds, Long menuId);

        /**
         * 批量新增menuButton
         * @param menuId
         * @param buttonIds
         * @return
         */
        boolean myMenuButtonBatchCreate(Long menuId, List<Integer> buttonIds);

        /**
         * 分页获取MenuButton列表数据（实体类）
         * @param page
         * @param menuButton
         * @return
         */
        Page<Button> mySelectPageWithParam(Page<Button> page, MenuButton menuButton);
        
        /**
         * 分页获取能够增加Button的列表（实体类）
         * @param page
         * @param menuButton
         * @return
         */
        Page<Button> mySelectEnableCreatePage(Page<Button> page, MenuButton menuButton);

        /**
         * 根据菜单id获取Button列表数据（List）
         * @param menuId
         * @param roleId
         * @return
         */
        List<Button> mySelectListWithMenuId(Long menuId, Long roleId);

        /**
         * 获取MenuButton列表数据（Map）
         * @param map
         * @return
         */
        List<MenuButton> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<MenuButton> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<MenuButton> mySelectList(Wrapper<MenuButton> wrapper);

        /**
         * 插入MenuButton
         * @param menuButton
         * @return
         */
        boolean myInsert(MenuButton menuButton);

        /**
         * 批量插入List<MenuButton>
         * @param menuButtonList
         * @return
         */
        boolean myInsertBatch(List<MenuButton> menuButtonList);

        /**
         * 插入或更新menuButton
         * @param menuButton
         * @return
         */
        boolean myInsertOrUpdate(MenuButton menuButton);

        /**
         * 批量插入或更新List<MenuButton>
         * @param menuButtonList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<MenuButton> menuButtonList);

        /**
         * 根据menuButtonIds获取List
         * @param menuButtonIds
         * @return
         */
        List<MenuButton> mySelectBatchIds(Collection<? extends Serializable> menuButtonIds);

        /**
         * 根据menuButtonId获取MenuButton
         * @param menuButtonId
         * @return
         */
        MenuButton mySelectById(Serializable menuButtonId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<MenuButton> wrapper);

        /**
         * 根据wrapper获取MenuButton
         * @param wrapper
         * @return
         */
        MenuButton mySelectOne(Wrapper<MenuButton> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<MenuButton> wrapper);

        /**
         * 根据menuButton和wrapper更新menuButton
         * @param menuButton
         * @param wrapper
         * @return
         */
        boolean myUpdate(MenuButton menuButton, Wrapper<MenuButton> wrapper);

        /**
         * 根据menuButtonList更新menuButton
         * @param menuButtonList
         * @return
         */
        boolean myUpdateBatchById(List<MenuButton> menuButtonList);

        /**
         * 根据menuButtonId修改menuButton
         * @param menuButton
         * @return
         */
        boolean myUpdateById(MenuButton menuButton);

}
