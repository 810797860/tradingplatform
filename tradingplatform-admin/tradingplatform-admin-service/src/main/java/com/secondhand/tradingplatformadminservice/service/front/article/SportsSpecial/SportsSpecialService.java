package com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecial;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : SportsSpecial 服务接口
 * ---------------------------------
 * @since 2019-03-16
 */
public interface SportsSpecialService extends BaseService<SportsSpecial> {

    /**
     * 根据id进行假删除
     *
     * @param sportsSpecialId
     * @return
     */
    Integer myFakeDeleteById(Long sportsSpecialId);

    /**
     * 根据ids进行批量假删除
     *
     * @param sportsSpecialIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> sportsSpecialIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param sportsSpecialId
     * @return
     */
    Map<String, Object> mySelectMapById(Long sportsSpecialId);

    /**
     * 新增或修改sportsSpecial
     *
     * @param sportsSpecial
     * @return
     */
    SportsSpecial mySportsSpecialCreateUpdate(SportsSpecial sportsSpecial);

    /**
     * 分页获取SportsSpecial列表数据（实体类）
     *
     * @param page
     * @param sportsSpecial
     * @return
     */
    Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecial sportsSpecial);

    /**
     * 获取SportsSpecial列表数据（Map）
     *
     * @param map
     * @return
     */
    List<SportsSpecial> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<SportsSpecial> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<SportsSpecial> mySelectList(Wrapper<SportsSpecial> wrapper);

    /**
     * 插入SportsSpecial
     *
     * @param sportsSpecial
     * @return
     */
    boolean myInsert(SportsSpecial sportsSpecial);

    /**
     * 批量插入List<SportsSpecial>
     *
     * @param sportsSpecialList
     * @return
     */
    boolean myInsertBatch(List<SportsSpecial> sportsSpecialList);

    /**
     * 插入或更新sportsSpecial
     *
     * @param sportsSpecial
     * @return
     */
    boolean myInsertOrUpdate(SportsSpecial sportsSpecial);

    /**
     * 批量插入或更新List<SportsSpecial>
     *
     * @param sportsSpecialList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<SportsSpecial> sportsSpecialList);

    /**
     * 根据sportsSpecialIds获取List
     *
     * @param sportsSpecialIds
     * @return
     */
    List<SportsSpecial> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialIds);

    /**
     * 根据sportsSpecialId获取SportsSpecial
     *
     * @param sportsSpecialId
     * @return
     */
    SportsSpecial mySelectById(Serializable sportsSpecialId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<SportsSpecial> wrapper);

    /**
     * 根据wrapper获取SportsSpecial
     *
     * @param wrapper
     * @return
     */
    SportsSpecial mySelectOne(Wrapper<SportsSpecial> wrapper);

    /**
     * 根据wrapper获取List<Object>
     *
     * @param wrapper
     * @return
     */
    List<Object> mySelectObjs(Wrapper<SportsSpecial> wrapper);

    /**
     * 根据sportsSpecial和wrapper更新sportsSpecial
     *
     * @param sportsSpecial
     * @param wrapper
     * @return
     */
    boolean myUpdate(SportsSpecial sportsSpecial, Wrapper<SportsSpecial> wrapper);

    /**
     * 根据sportsSpecialList更新sportsSpecial
     *
     * @param sportsSpecialList
     * @return
     */
    boolean myUpdateBatchById(List<SportsSpecial> sportsSpecialList);

    /**
     * 根据sportsSpecialId修改sportsSpecial
     *
     * @param sportsSpecial
     * @return
     */
    boolean myUpdateById(SportsSpecial sportsSpecial);

}
