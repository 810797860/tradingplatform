package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquareCollection;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : DigitalSquareCollection 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface DigitalSquareCollectionService extends BaseService<DigitalSquareCollection> {

        /**
         * 根据id进行假删除
         * @param digitalSquareCollectionId
         * @return
         */
        Integer myFakeDeleteById(Long digitalSquareCollectionId);

        /**
         * 根据ids进行批量假删除
         * @param digitalSquareCollectionIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> digitalSquareCollectionIds);

        /**
         * 获取Map数据（Obj）
         * @param digitalSquareCollectionId
         * @return
         */
        Map<String, Object> mySelectMapById(Long digitalSquareCollectionId);

        /**
         * 新增或修改digitalSquareCollection
         * @param digitalSquareCollection
         * @return
         */
        DigitalSquareCollection myDigitalSquareCollectionCreateUpdate(DigitalSquareCollection digitalSquareCollection);

        /**
         * 分页获取DigitalSquareCollection列表数据（实体类）
         * @param page
         * @param digitalSquareCollection
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareCollection digitalSquareCollection);

        /**
         * 获取DigitalSquareCollection列表数据（Map）
         * @param map
         * @return
         */
        List<DigitalSquareCollection> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<DigitalSquareCollection> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<DigitalSquareCollection> mySelectList(Wrapper<DigitalSquareCollection> wrapper);

        /**
         * 插入DigitalSquareCollection
         * @param digitalSquareCollection
         * @return
         */
        boolean myInsert(DigitalSquareCollection digitalSquareCollection);

        /**
         * 批量插入List<DigitalSquareCollection>
         * @param digitalSquareCollectionList
         * @return
         */
        boolean myInsertBatch(List<DigitalSquareCollection> digitalSquareCollectionList);

        /**
         * 插入或更新digitalSquareCollection
         * @param digitalSquareCollection
         * @return
         */
        boolean myInsertOrUpdate(DigitalSquareCollection digitalSquareCollection);

        /**
         * 批量插入或更新List<DigitalSquareCollection>
         * @param digitalSquareCollectionList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<DigitalSquareCollection> digitalSquareCollectionList);

        /**
         * 根据digitalSquareCollectionIds获取List
         * @param digitalSquareCollectionIds
         * @return
         */
        List<DigitalSquareCollection> mySelectBatchIds(Collection<? extends Serializable> digitalSquareCollectionIds);

        /**
         * 根据digitalSquareCollectionId获取DigitalSquareCollection
         * @param digitalSquareCollectionId
         * @return
         */
        DigitalSquareCollection mySelectById(Serializable digitalSquareCollectionId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<DigitalSquareCollection> wrapper);

        /**
         * 根据wrapper获取DigitalSquareCollection
         * @param wrapper
         * @return
         */
        DigitalSquareCollection mySelectOne(Wrapper<DigitalSquareCollection> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<DigitalSquareCollection> wrapper);

        /**
         * 根据digitalSquareCollection和wrapper更新digitalSquareCollection
         * @param digitalSquareCollection
         * @param wrapper
         * @return
         */
        boolean myUpdate(DigitalSquareCollection digitalSquareCollection, Wrapper<DigitalSquareCollection> wrapper);

        /**
         * 根据digitalSquareCollectionList更新digitalSquareCollection
         * @param digitalSquareCollectionList
         * @return
         */
        boolean myUpdateBatchById(List<DigitalSquareCollection> digitalSquareCollectionList);

        /**
         * 根据digitalSquareCollectionId修改digitalSquareCollection
         * @param digitalSquareCollection
         * @return
         */
        boolean myUpdateById(DigitalSquareCollection digitalSquareCollection);

}
