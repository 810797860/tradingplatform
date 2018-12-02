package com.secondhand.tradingplatformadminservice.service.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Menu;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleMenu;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RoleMenu 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-02
 */
public interface RoleMenuService extends BaseService<RoleMenu> {

        /**
         * 根据id进行假删除
         * @param roleMenuId
         * @return
         */
        Integer myFakeDeleteById(Long roleMenuId);

        /**
         * 根据ids进行批量假删除
         * @param roleMenuIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> roleMenuIds);

        /**
         * 获取Map数据（Obj）
         * @param roleMenuId
         * @return
         */
        Map<String, Object> mySelectMapById(Long roleMenuId);

        /**
         * 新增或修改roleMenu
         * @param roleMenu
         * @return
         */
        RoleMenu myRoleMenuCreateUpdate(RoleMenu roleMenu);

        /**
         * 分页获取Menu列表数据（实体类）
         * @param page
         * @param roleMenu
         * @return
         */
        Page<Menu> mySelectPageWithParam(Page<Menu> page, RoleMenu roleMenu);

        /**
         * 分页获取能够增加Menu的列表（实体类）
         * @param page
         * @param roleMenu
         * @return
         */
        Page<Menu> mySelectEnableCreatePage(Page<Menu> page, RoleMenu roleMenu);

        /**
         * 获取RoleMenu列表数据（Map）
         * @param map
         * @return
         */
        List<RoleMenu> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RoleMenu> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RoleMenu> mySelectList(Wrapper<RoleMenu> wrapper);

        /**
         * 插入RoleMenu
         * @param roleMenu
         * @return
         */
        boolean myInsert(RoleMenu roleMenu);

        /**
         * 批量插入List<RoleMenu>
         * @param roleMenuList
         * @return
         */
        boolean myInsertBatch(List<RoleMenu> roleMenuList);

        /**
         * 插入或更新roleMenu
         * @param roleMenu
         * @return
         */
        boolean myInsertOrUpdate(RoleMenu roleMenu);

        /**
         * 批量插入或更新List<RoleMenu>
         * @param roleMenuList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RoleMenu> roleMenuList);

        /**
         * 根据roleMenuIds获取List
         * @param roleMenuIds
         * @return
         */
        List<RoleMenu> mySelectBatchIds(Collection<? extends Serializable> roleMenuIds);

        /**
         * 根据roleMenuId获取RoleMenu
         * @param roleMenuId
         * @return
         */
        RoleMenu mySelectById(Serializable roleMenuId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RoleMenu> wrapper);

        /**
         * 根据wrapper获取RoleMenu
         * @param wrapper
         * @return
         */
        RoleMenu mySelectOne(Wrapper<RoleMenu> wrapper);

        /**
         * 根据roleMenu和wrapper更新roleMenu
         * @param roleMenu
         * @param wrapper
         * @return
         */
        boolean myUpdate(RoleMenu roleMenu, Wrapper<RoleMenu> wrapper);

        /**
         * 根据roleMenuList更新roleMenu
         * @param roleMenuList
         * @return
         */
        boolean myUpdateBatchById(List<RoleMenu> roleMenuList);

        /**
         * 根据roleMenuId修改roleMenu
         * @param roleMenu
         * @return
         */
        boolean myUpdateById(RoleMenu roleMenu);

}
