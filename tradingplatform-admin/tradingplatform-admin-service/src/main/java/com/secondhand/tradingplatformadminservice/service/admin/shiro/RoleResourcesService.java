package com.secondhand.tradingplatformadminservice.service.admin.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Resources;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.RoleResources;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RoleResources 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-12
 */
public interface RoleResourcesService extends BaseService<RoleResources> {

        /**
         * 根据id进行假删除
         * @param roleResources
         * @return
         */
        Integer myFakeDeleteByRoleResources(RoleResources roleResources);

        /**
         * 根据ids进行批量假删除
         * @param roleId
         * @param resourcesIds
         * @return
         */
        boolean myFakeBatchDelete(Long roleId, List<Integer> resourcesIds);

        /**
         * 根据角色id配置权限
         * @param roleId
         * @param resourcesIds
         * @return
         */
        Integer myUpdateRoleResources(Long roleId, List<Long> resourcesIds);

        /**
         * 获取Map数据（Obj）
         * @param roleResourcesId
         * @return
         */
        Map<String, Object> mySelectMapById(Long roleResourcesId);

        /**
         * 新增或修改roleResources
         * @param roleResources
         * @return
         */
        RoleResources myRoleResourcesCreateUpdate(RoleResources roleResources);

        /**
         * 批量新增roleResources
         * @param roleId
         * @param resourcesIds
         * @return
         */
        boolean myRoleResourcesBatchCreate(Long roleId, List<Integer> resourcesIds);

        /**
         * 分页获取RoleResources列表数据（实体类）
         * @param page
         * @param roleResources
         * @return
         */
        Page<Resources> mySelectPageWithParam(Page<Resources> page, RoleResources roleResources);

        /**
         * 分页获取能够增加Resources的列表（实体类）
         * @param page
         * @param roleResources
         * @return
         */
        Page<Resources> mySelectEnableCreatePage(Page<Resources> page, RoleResources roleResources);

        /**
         * 获取RoleResources列表数据（Map）
         * @param map
         * @return
         */
        List<RoleResources> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RoleResources> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RoleResources> mySelectList(Wrapper<RoleResources> wrapper);

        /**
         * 插入RoleResources
         * @param roleResources
         * @return
         */
        boolean myInsert(RoleResources roleResources);

        /**
         * 批量插入List<RoleResources>
         * @param roleResourcesList
         * @return
         */
        boolean myInsertBatch(List<RoleResources> roleResourcesList);

        /**
         * 插入或更新roleResources
         * @param roleResources
         * @return
         */
        boolean myInsertOrUpdate(RoleResources roleResources);

        /**
         * 批量插入或更新List<RoleResources>
         * @param roleResourcesList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RoleResources> roleResourcesList);

        /**
         * 根据roleResourcesIds获取List
         * @param roleResourcesIds
         * @return
         */
        List<RoleResources> mySelectBatchIds(Collection<? extends Serializable> roleResourcesIds);

        /**
         * 根据roleResourcesId获取RoleResources
         * @param roleResourcesId
         * @return
         */
        RoleResources mySelectById(Serializable roleResourcesId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RoleResources> wrapper);

        /**
         * 根据wrapper获取RoleResources
         * @param wrapper
         * @return
         */
        RoleResources mySelectOne(Wrapper<RoleResources> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<RoleResources> wrapper);

        /**
         * 根据roleResources和wrapper更新roleResources
         * @param roleResources
         * @param wrapper
         * @return
         */
        boolean myUpdate(RoleResources roleResources, Wrapper<RoleResources> wrapper);

        /**
         * 根据roleResourcesList更新roleResources
         * @param roleResourcesList
         * @return
         */
        boolean myUpdateBatchById(List<RoleResources> roleResourcesList);

        /**
         * 根据roleResourcesId修改roleResources
         * @param roleResources
         * @return
         */
        boolean myUpdateById(RoleResources roleResources);

        /**
         * 根据角色id查找权限
         * @param roleId
         * @return
         */
        List<Resources> mySelectSelectedList(Long roleId);
}
