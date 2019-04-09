package com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse.RentingHouse;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : RentingHouse 服务接口
 * ---------------------------------
 * @since 2019-03-17
 */
public interface RentingHouseService extends BaseService<RentingHouse> {

    /**
     * 根据id进行假删除
     *
     * @param rentingHouseId
     * @return
     */
    Integer myFakeDeleteById(Long rentingHouseId);

    /**
     * 根据ids进行批量假删除
     *
     * @param rentingHouseIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> rentingHouseIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param rentingHouseId
     * @return
     */
    Map<String, Object> mySelectMapById(Long rentingHouseId);

    /**
     * 新增或修改rentingHouse
     *
     * @param rentingHouse
     * @return
     */
    RentingHouse myRentingHouseCreateUpdate(RentingHouse rentingHouse);

    /**
     * 分页获取RentingHouse列表数据（实体类）
     *
     * @param page
     * @param rentingHouse
     * @return
     */
    Page<Map<String, Object>> mySelectPageWithParam(Page page, RentingHouse rentingHouse);

    /**
     * 获取RentingHouse列表数据（Map）
     *
     * @param map
     * @return
     */
    List<RentingHouse> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<RentingHouse> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<RentingHouse> mySelectList(Wrapper<RentingHouse> wrapper);

    /**
     * 插入RentingHouse
     *
     * @param rentingHouse
     * @return
     */
    boolean myInsert(RentingHouse rentingHouse);

    /**
     * 批量插入List<RentingHouse>
     *
     * @param rentingHouseList
     * @return
     */
    boolean myInsertBatch(List<RentingHouse> rentingHouseList);

    /**
     * 插入或更新rentingHouse
     *
     * @param rentingHouse
     * @return
     */
    boolean myInsertOrUpdate(RentingHouse rentingHouse);

    /**
     * 批量插入或更新List<RentingHouse>
     *
     * @param rentingHouseList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<RentingHouse> rentingHouseList);

    /**
     * 根据rentingHouseIds获取List
     *
     * @param rentingHouseIds
     * @return
     */
    List<RentingHouse> mySelectBatchIds(Collection<? extends Serializable> rentingHouseIds);

    /**
     * 根据rentingHouseId获取RentingHouse
     *
     * @param rentingHouseId
     * @return
     */
    RentingHouse mySelectById(Serializable rentingHouseId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<RentingHouse> wrapper);

    /**
     * 根据wrapper获取RentingHouse
     *
     * @param wrapper
     * @return
     */
    RentingHouse mySelectOne(Wrapper<RentingHouse> wrapper);

    /**
     * 根据wrapper获取List<Object>
     *
     * @param wrapper
     * @return
     */
    List<Object> mySelectObjs(Wrapper<RentingHouse> wrapper);

    /**
     * 根据rentingHouse和wrapper更新rentingHouse
     *
     * @param rentingHouse
     * @param wrapper
     * @return
     */
    boolean myUpdate(RentingHouse rentingHouse, Wrapper<RentingHouse> wrapper);

    /**
     * 根据rentingHouseList更新rentingHouse
     *
     * @param rentingHouseList
     * @return
     */
    boolean myUpdateBatchById(List<RentingHouse> rentingHouseList);

    /**
     * 根据rentingHouseId修改rentingHouse
     *
     * @param rentingHouse
     * @return
     */
    boolean myUpdateById(RentingHouse rentingHouse);

}
