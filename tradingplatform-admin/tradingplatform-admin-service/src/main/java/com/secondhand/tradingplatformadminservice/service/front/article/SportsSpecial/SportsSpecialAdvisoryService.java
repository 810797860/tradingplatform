package com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialAdvisory;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : SportsSpecialAdvisory 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface SportsSpecialAdvisoryService extends BaseService<SportsSpecialAdvisory> {

        /**
         * 根据id进行假删除
         * @param sportsSpecialAdvisoryId
         * @return
         */
        Integer myFakeDeleteById(Long sportsSpecialAdvisoryId);

        /**
         * 根据ids进行批量假删除
         * @param sportsSpecialAdvisoryIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> sportsSpecialAdvisoryIds);

        /**
         * 获取Map数据（Obj）
         * @param sportsSpecialAdvisoryId
         * @return
         */
        Map<String, Object> mySelectMapById(Long sportsSpecialAdvisoryId);

        /**
         * 新增或修改sportsSpecialAdvisory
         * @param sportsSpecialAdvisory
         * @return
         */
        SportsSpecialAdvisory mySportsSpecialAdvisoryCreateUpdate(SportsSpecialAdvisory sportsSpecialAdvisory);

        /**
         * 分页获取SportsSpecialAdvisory列表数据（实体类）
         * @param page
         * @param sportsSpecialAdvisory
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialAdvisory sportsSpecialAdvisory);

        /**
         * 获取SportsSpecialAdvisory列表数据（Map）
         * @param map
         * @return
         */
        List<SportsSpecialAdvisory> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<SportsSpecialAdvisory> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<SportsSpecialAdvisory> mySelectList(Wrapper<SportsSpecialAdvisory> wrapper);

        /**
         * 插入SportsSpecialAdvisory
         * @param sportsSpecialAdvisory
         * @return
         */
        boolean myInsert(SportsSpecialAdvisory sportsSpecialAdvisory);

        /**
         * 批量插入List<SportsSpecialAdvisory>
         * @param sportsSpecialAdvisoryList
         * @return
         */
        boolean myInsertBatch(List<SportsSpecialAdvisory> sportsSpecialAdvisoryList);

        /**
         * 插入或更新sportsSpecialAdvisory
         * @param sportsSpecialAdvisory
         * @return
         */
        boolean myInsertOrUpdate(SportsSpecialAdvisory sportsSpecialAdvisory);

        /**
         * 批量插入或更新List<SportsSpecialAdvisory>
         * @param sportsSpecialAdvisoryList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<SportsSpecialAdvisory> sportsSpecialAdvisoryList);

        /**
         * 根据sportsSpecialAdvisoryIds获取List
         * @param sportsSpecialAdvisoryIds
         * @return
         */
        List<SportsSpecialAdvisory> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialAdvisoryIds);

        /**
         * 根据sportsSpecialAdvisoryId获取SportsSpecialAdvisory
         * @param sportsSpecialAdvisoryId
         * @return
         */
        SportsSpecialAdvisory mySelectById(Serializable sportsSpecialAdvisoryId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<SportsSpecialAdvisory> wrapper);

        /**
         * 根据wrapper获取SportsSpecialAdvisory
         * @param wrapper
         * @return
         */
        SportsSpecialAdvisory mySelectOne(Wrapper<SportsSpecialAdvisory> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<SportsSpecialAdvisory> wrapper);

        /**
         * 根据sportsSpecialAdvisory和wrapper更新sportsSpecialAdvisory
         * @param sportsSpecialAdvisory
         * @param wrapper
         * @return
         */
        boolean myUpdate(SportsSpecialAdvisory sportsSpecialAdvisory, Wrapper<SportsSpecialAdvisory> wrapper);

        /**
         * 根据sportsSpecialAdvisoryList更新sportsSpecialAdvisory
         * @param sportsSpecialAdvisoryList
         * @return
         */
        boolean myUpdateBatchById(List<SportsSpecialAdvisory> sportsSpecialAdvisoryList);

        /**
         * 根据sportsSpecialAdvisoryId修改sportsSpecialAdvisory
         * @param sportsSpecialAdvisory
         * @return
         */
        boolean myUpdateById(SportsSpecialAdvisory sportsSpecialAdvisory);

}
