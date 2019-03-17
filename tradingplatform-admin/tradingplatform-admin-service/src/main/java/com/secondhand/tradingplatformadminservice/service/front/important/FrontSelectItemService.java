package com.secondhand.tradingplatformadminservice.service.front.important;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.important.FrontSelectItem;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : FrontSelectItem 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface FrontSelectItemService extends BaseService<FrontSelectItem> {

    /**
     * 根据id进行假删除
     *
     * @param frontSelectItemId
     * @return
     */
    Integer myFakeDeleteById(Long frontSelectItemId);

    /**
     * 根据ids进行批量假删除
     *
     * @param frontSelectItemIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> frontSelectItemIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param frontSelectItemId
     * @return
     */
    Map<String, Object> mySelectMapById(Long frontSelectItemId);

    /**
     * 新增或修改frontSelectItem
     *
     * @param frontSelectItem
     * @return
     */
    FrontSelectItem myFrontSelectItemCreateUpdate(FrontSelectItem frontSelectItem);

    /**
     * 分页获取FrontSelectItem列表数据（实体类）
     *
     * @param page
     * @param frontSelectItem
     * @return
     */
    Page<Map<String, Object>> mySelectPageWithParam(Page page, FrontSelectItem frontSelectItem);

    /**
     * 获取FrontSelectItem列表数据（Map）
     *
     * @param map
     * @return
     */
    List<FrontSelectItem> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<FrontSelectItem> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<FrontSelectItem> mySelectList(Wrapper<FrontSelectItem> wrapper);

    /**
     * 插入FrontSelectItem
     *
     * @param frontSelectItem
     * @return
     */
    boolean myInsert(FrontSelectItem frontSelectItem);

    /**
     * 批量插入List<FrontSelectItem>
     *
     * @param frontSelectItemList
     * @return
     */
    boolean myInsertBatch(List<FrontSelectItem> frontSelectItemList);

    /**
     * 插入或更新frontSelectItem
     *
     * @param frontSelectItem
     * @return
     */
    boolean myInsertOrUpdate(FrontSelectItem frontSelectItem);

    /**
     * 批量插入或更新List<FrontSelectItem>
     *
     * @param frontSelectItemList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<FrontSelectItem> frontSelectItemList);

    /**
     * 根据frontSelectItemIds获取List
     *
     * @param frontSelectItemIds
     * @return
     */
    List<FrontSelectItem> mySelectBatchIds(Collection<? extends Serializable> frontSelectItemIds);

    /**
     * 根据frontSelectItemId获取FrontSelectItem
     *
     * @param frontSelectItemId
     * @return
     */
    FrontSelectItem mySelectById(Serializable frontSelectItemId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<FrontSelectItem> wrapper);

    /**
     * 根据wrapper获取FrontSelectItem
     *
     * @param wrapper
     * @return
     */
    FrontSelectItem mySelectOne(Wrapper<FrontSelectItem> wrapper);

    /**
     * 根据wrapper获取List<Object>
     *
     * @param wrapper
     * @return
     */
    List<Object> mySelectObjs(Wrapper<FrontSelectItem> wrapper);

    /**
     * 根据frontSelectItem和wrapper更新frontSelectItem
     *
     * @param frontSelectItem
     * @param wrapper
     * @return
     */
    boolean myUpdate(FrontSelectItem frontSelectItem, Wrapper<FrontSelectItem> wrapper);

    /**
     * 根据frontSelectItemList更新frontSelectItem
     *
     * @param frontSelectItemList
     * @return
     */
    boolean myUpdateBatchById(List<FrontSelectItem> frontSelectItemList);

    /**
     * 根据frontSelectItemId修改frontSelectItem
     *
     * @param frontSelectItem
     * @return
     */
    boolean myUpdateById(FrontSelectItem frontSelectItem);

    /**
     * 根据pid获取子级枚举列表
     *
     * @param pid
     * @return
     */
    List<FrontSelectItem> myGetItemsByPid(Long pid);

    /**
     * 根据pid递归获取所有子级枚举列表
     *
     * @param pid
     * @return
     */
    List<FrontSelectItem> myGetAllItemsByPid(Long pid);
}
