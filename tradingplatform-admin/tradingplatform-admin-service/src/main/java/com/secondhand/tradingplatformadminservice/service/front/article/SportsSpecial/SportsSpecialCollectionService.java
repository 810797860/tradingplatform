package com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecialCollection;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : SportsSpecialCollection 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface SportsSpecialCollectionService extends BaseService<SportsSpecialCollection> {

        /**
         * 根据id进行假删除
         * @param sportsSpecialCollectionId
         * @return
         */
        Integer myFakeDeleteById(Long sportsSpecialCollectionId);

        /**
         * 根据ids进行批量假删除
         * @param sportsSpecialCollectionIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> sportsSpecialCollectionIds);

        /**
         * 获取Map数据（Obj）
         * @param sportsSpecialCollectionId
         * @return
         */
        Map<String, Object> mySelectMapById(Long sportsSpecialCollectionId);

        /**
         * 新增或修改sportsSpecialCollection
         * @param sportsSpecialCollection
         * @return
         */
        SportsSpecialCollection mySportsSpecialCollectionCreateUpdate(SportsSpecialCollection sportsSpecialCollection);

        /**
         * 分页获取SportsSpecialCollection列表数据（实体类）
         * @param page
         * @param sportsSpecialCollection
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecialCollection sportsSpecialCollection);

        /**
         * 获取SportsSpecialCollection列表数据（Map）
         * @param map
         * @return
         */
        List<SportsSpecialCollection> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<SportsSpecialCollection> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<SportsSpecialCollection> mySelectList(Wrapper<SportsSpecialCollection> wrapper);

        /**
         * 插入SportsSpecialCollection
         * @param sportsSpecialCollection
         * @return
         */
        boolean myInsert(SportsSpecialCollection sportsSpecialCollection);

        /**
         * 批量插入List<SportsSpecialCollection>
         * @param sportsSpecialCollectionList
         * @return
         */
        boolean myInsertBatch(List<SportsSpecialCollection> sportsSpecialCollectionList);

        /**
         * 插入或更新sportsSpecialCollection
         * @param sportsSpecialCollection
         * @return
         */
        boolean myInsertOrUpdate(SportsSpecialCollection sportsSpecialCollection);

        /**
         * 批量插入或更新List<SportsSpecialCollection>
         * @param sportsSpecialCollectionList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<SportsSpecialCollection> sportsSpecialCollectionList);

        /**
         * 根据sportsSpecialCollectionIds获取List
         * @param sportsSpecialCollectionIds
         * @return
         */
        List<SportsSpecialCollection> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialCollectionIds);

        /**
         * 根据sportsSpecialCollectionId获取SportsSpecialCollection
         * @param sportsSpecialCollectionId
         * @return
         */
        SportsSpecialCollection mySelectById(Serializable sportsSpecialCollectionId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<SportsSpecialCollection> wrapper);

        /**
         * 根据wrapper获取SportsSpecialCollection
         * @param wrapper
         * @return
         */
        SportsSpecialCollection mySelectOne(Wrapper<SportsSpecialCollection> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<SportsSpecialCollection> wrapper);

        /**
         * 根据sportsSpecialCollection和wrapper更新sportsSpecialCollection
         * @param sportsSpecialCollection
         * @param wrapper
         * @return
         */
        boolean myUpdate(SportsSpecialCollection sportsSpecialCollection, Wrapper<SportsSpecialCollection> wrapper);

        /**
         * 根据sportsSpecialCollectionList更新sportsSpecialCollection
         * @param sportsSpecialCollectionList
         * @return
         */
        boolean myUpdateBatchById(List<SportsSpecialCollection> sportsSpecialCollectionList);

        /**
         * 根据sportsSpecialCollectionId修改sportsSpecialCollection
         * @param sportsSpecialCollection
         * @return
         */
        boolean myUpdateById(SportsSpecialCollection sportsSpecialCollection);

}
