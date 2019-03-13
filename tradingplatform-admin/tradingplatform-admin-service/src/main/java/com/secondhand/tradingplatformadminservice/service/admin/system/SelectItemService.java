package com.secondhand.tradingplatformadminservice.service.admin.system;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.system.SelectItem;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItem 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-02-05
 */
public interface SelectItemService extends BaseService<SelectItem> {

        /**
         * 根据id进行假删除
         * @param selectItemId
         * @return
         */
        Integer myFakeDeleteById(Long selectItemId);

        /**
         * 根据ids进行批量假删除
         * @param selectItemIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> selectItemIds);

        /**
         * 获取Map数据（Obj）
         * @param selectItemId
         * @return
         */
        Map<String, Object> mySelectMapById(Long selectItemId);

        /**
         * 新增或修改selectItem
         * @param selectItem
         * @return
         */
        SelectItem mySelectItemCreateUpdate(SelectItem selectItem);

        /**
         * 分页获取SelectItem列表数据（实体类）
         * @param page
         * @param selectItem
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, SelectItem selectItem);

        /**
         * 获取SelectItem列表数据（Map）
         * @param map
         * @return
         */
        List<SelectItem> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<SelectItem> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<SelectItem> mySelectList(Wrapper<SelectItem> wrapper);

        /**
         * 插入SelectItem
         * @param selectItem
         * @return
         */
        boolean myInsert(SelectItem selectItem);

        /**
         * 批量插入List<SelectItem>
         * @param selectItemList
         * @return
         */
        boolean myInsertBatch(List<SelectItem> selectItemList);

        /**
         * 插入或更新selectItem
         * @param selectItem
         * @return
         */
        boolean myInsertOrUpdate(SelectItem selectItem);

        /**
         * 批量插入或更新List<SelectItem>
         * @param selectItemList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<SelectItem> selectItemList);

        /**
         * 根据selectItemIds获取List
         * @param selectItemIds
         * @return
         */
        List<SelectItem> mySelectBatchIds(Collection<? extends Serializable> selectItemIds);

        /**
         * 根据selectItemId获取SelectItem
         * @param selectItemId
         * @return
         */
        SelectItem mySelectById(Serializable selectItemId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<SelectItem> wrapper);

        /**
         * 根据wrapper获取SelectItem
         * @param wrapper
         * @return
         */
        SelectItem mySelectOne(Wrapper<SelectItem> wrapper);

        /**
         * 根据selectItem和wrapper更新selectItem
         * @param selectItem
         * @param wrapper
         * @return
         */
        boolean myUpdate(SelectItem selectItem, Wrapper<SelectItem> wrapper);

        /**
         * 根据selectItemList更新selectItem
         * @param selectItemList
         * @return
         */
        boolean myUpdateBatchById(List<SelectItem> selectItemList);

        /**
         * 根据selectItemId修改selectItem
         * @param selectItem
         * @return
         */
        boolean myUpdateById(SelectItem selectItem);

        /**
         * 根据pid获取子级枚举列表
         * @param pid
         * @return
         */
        List<SelectItem> myGetItemsByPid(Long pid);

        /**
         * 根据pid递归获取所有子级枚举列表
         * @param pid
         * @return
         */
        List<SelectItem> myGetAllItemsByPid(Long pid);
}
