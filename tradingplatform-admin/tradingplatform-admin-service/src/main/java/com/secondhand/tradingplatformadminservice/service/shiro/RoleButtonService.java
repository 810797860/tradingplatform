package com.secondhand.tradingplatformadminservice.service.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformadminentity.entity.shiro.RoleButton;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : RoleButton 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-04
 */
public interface RoleButtonService extends BaseService<RoleButton> {

        /**
         * 根据id进行假删除
         * @param roleButton
         * @return
         */
        Integer myFakeDeleteByRoleButton(RoleButton roleButton);

        /**
         * 根据ids进行批量假删除
         * @param roleId
         * @param buttonIds
         * @return
         */
        boolean myFakeBatchDelete(Long roleId, List<Integer> buttonIds);

        /**
         * 获取Map数据（Obj）
         * @param roleButtonId
         * @return
         */
        Map<String, Object> mySelectMapById(Long roleButtonId);

        /**
         * 新增或修改roleButton
         * @param roleButton
         * @return
         */
        RoleButton myRoleButtonCreateUpdate(RoleButton roleButton);

        /**
         * 批量新增roleButton
         * @param roleId
         * @param buttonIds
         * @return
         */
        boolean myRoleButtonBatchCreate(Long roleId, List<Integer> buttonIds);

        /**
         * 分页获取RoleButton列表数据（实体类）
         * @param page
         * @param roleButton
         * @return
         */
        Page<Button> mySelectPageWithParam(Page<Button> page, RoleButton roleButton);

        /**
         * 分页获取能够增加Button的列表（实体类）
         * @param page
         * @param roleButton
         * @return
         */
        Page<Button> mySelectEnableCreatePage(Page<Button> page, RoleButton roleButton);

        /**
         * 获取RoleButton列表数据（Map）
         * @param map
         * @return
         */
        List<RoleButton> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<RoleButton> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<RoleButton> mySelectList(Wrapper<RoleButton> wrapper);

        /**
         * 插入RoleButton
         * @param roleButton
         * @return
         */
        boolean myInsert(RoleButton roleButton);

        /**
         * 批量插入List<RoleButton>
         * @param roleButtonList
         * @return
         */
        boolean myInsertBatch(List<RoleButton> roleButtonList);

        /**
         * 插入或更新roleButton
         * @param roleButton
         * @return
         */
        boolean myInsertOrUpdate(RoleButton roleButton);

        /**
         * 批量插入或更新List<RoleButton>
         * @param roleButtonList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<RoleButton> roleButtonList);

        /**
         * 根据roleButtonIds获取List
         * @param roleButtonIds
         * @return
         */
        List<RoleButton> mySelectBatchIds(Collection<? extends Serializable> roleButtonIds);

        /**
         * 根据roleButtonId获取RoleButton
         * @param roleButtonId
         * @return
         */
        RoleButton mySelectById(Serializable roleButtonId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<RoleButton> wrapper);

        /**
         * 根据wrapper获取RoleButton
         * @param wrapper
         * @return
         */
        RoleButton mySelectOne(Wrapper<RoleButton> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<RoleButton> wrapper);

        /**
         * 根据roleButton和wrapper更新roleButton
         * @param roleButton
         * @param wrapper
         * @return
         */
        boolean myUpdate(RoleButton roleButton, Wrapper<RoleButton> wrapper);

        /**
         * 根据roleButtonList更新roleButton
         * @param roleButtonList
         * @return
         */
        boolean myUpdateBatchById(List<RoleButton> roleButtonList);

        /**
         * 根据roleButtonId修改roleButton
         * @param roleButton
         * @return
         */
        boolean myUpdateById(RoleButton roleButton);

}
