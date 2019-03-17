package com.secondhand.tradingplatformadminservice.service.admin.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Menu;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : Menu 服务接口
 * ---------------------------------
 * @since 2018-11-29
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 根据id进行假删除
     *
     * @param menuId
     * @return
     */
    Integer myFakeDeleteById(Long menuId);

    /**
     * 根据ids进行批量假删除
     *
     * @param menuIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> menuIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param menuId
     * @return
     */
    Map<String, Object> mySelectMapById(Long menuId);

    /**
     * 新增或修改menu
     *
     * @param menu
     * @return
     */
    Menu myMenuCreateUpdate(Menu menu);

    /**
     * 分页获取Menu列表数据（实体类）
     *
     * @param page
     * @param menu
     * @return
     */
    Page<Menu> mySelectPageWithParam(Page<Menu> page, Menu menu);

    /**
     * 获取Menu列表数据（Map）
     *
     * @param map
     * @return
     */
    List<Menu> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<Menu> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<Menu> mySelectList(Wrapper<Menu> wrapper);

    /**
     * 插入Menu
     *
     * @param menu
     * @return
     */
    boolean myInsert(Menu menu);

    /**
     * 批量插入List<Menu>
     *
     * @param menuList
     * @return
     */
    boolean myInsertBatch(List<Menu> menuList);

    /**
     * 插入或更新menu
     *
     * @param menu
     * @return
     */
    boolean myInsertOrUpdate(Menu menu);

    /**
     * 批量插入或更新List<Menu>
     *
     * @param menuList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<Menu> menuList);

    /**
     * 根据menuIds获取List
     *
     * @param menuIds
     * @return
     */
    List<Menu> mySelectBatchIds(Collection<? extends Serializable> menuIds);

    /**
     * 根据menuId获取Menu
     *
     * @param menuId
     * @return
     */
    Menu mySelectById(Serializable menuId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<Menu> wrapper);

    /**
     * 根据wrapper获取Menu
     *
     * @param wrapper
     * @return
     */
    Menu mySelectOne(Wrapper<Menu> wrapper);

    /**
     * 根据menu和wrapper更新menu
     *
     * @param menu
     * @param wrapper
     * @return
     */
    boolean myUpdate(Menu menu, Wrapper<Menu> wrapper);

    /**
     * 根据menuList更新menu
     *
     * @param menuList
     * @return
     */
    boolean myUpdateBatchById(List<Menu> menuList);

    /**
     * 根据menuId修改menu
     *
     * @param menu
     * @return
     */
    boolean myUpdateById(Menu menu);

    /**
     * 查找所有的菜单
     *
     * @return
     */
    List<Menu> mySelectAllList();
}
