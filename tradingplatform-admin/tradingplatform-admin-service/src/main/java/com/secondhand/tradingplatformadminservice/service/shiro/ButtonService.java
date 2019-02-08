package com.secondhand.tradingplatformadminservice.service.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.shiro.Button;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : Button 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-04
 */
public interface ButtonService extends BaseService<Button> {

        /**
         * 根据id进行假删除
         * @param buttonId
         * @return
         */
        Integer myFakeDeleteById(Long buttonId);

        /**
         * 根据ids进行批量假删除
         * @param buttonIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> buttonIds);

        /**
         * 获取Map数据（Obj）
         * @param buttonId
         * @return
         */
        Map<String, Object> mySelectMapById(Long buttonId);

        /**
         * 新增或修改button
         * @param button
         * @return
         */
        Button myButtonCreateUpdate(Button button);

        /**
         * 分页获取Button列表数据（实体类）
         * @param page
         * @param button
         * @return
         */
        Page<Button> mySelectPageWithParam(Page<Button> page, Button button, Long menuId);

        /**
         * 获取Button列表数据（Map）
         * @param map
         * @return
         */
        List<Button> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<Button> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<Button> mySelectList(Wrapper<Button> wrapper);

        /**
         * 插入Button
         * @param button
         * @return
         */
        boolean myInsert(Button button);

        /**
         * 批量插入List<Button>
         * @param buttonList
         * @return
         */
        boolean myInsertBatch(List<Button> buttonList);

        /**
         * 插入或更新button
         * @param button
         * @return
         */
        boolean myInsertOrUpdate(Button button);

        /**
         * 批量插入或更新List<Button>
         * @param buttonList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<Button> buttonList);

        /**
         * 根据buttonIds获取List
         * @param buttonIds
         * @return
         */
        List<Button> mySelectBatchIds(Collection<? extends Serializable> buttonIds);

        /**
         * 根据buttonId获取Button
         * @param buttonId
         * @return
         */
        Button mySelectById(Serializable buttonId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<Button> wrapper);

        /**
         * 根据wrapper获取Button
         * @param wrapper
         * @return
         */
        Button mySelectOne(Wrapper<Button> wrapper);

        /**
         * 根据button和wrapper更新button
         * @param button
         * @param wrapper
         * @return
         */
        boolean myUpdate(Button button, Wrapper<Button> wrapper);

        /**
         * 根据buttonList更新button
         * @param buttonList
         * @return
         */
        boolean myUpdateBatchById(List<Button> buttonList);

        /**
         * 根据buttonId修改button
         * @param button
         * @return
         */
        boolean myUpdateById(Button button);

}
