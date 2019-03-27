package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricApplianceCollection;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : ElectricApplianceCollection 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface ElectricApplianceCollectionService extends BaseService<ElectricApplianceCollection> {

        /**
         * 根据id进行假删除
         * @param electricApplianceCollectionId
         * @return
         */
        Integer myFakeDeleteById(Long electricApplianceCollectionId);

        /**
         * 根据ids进行批量假删除
         * @param electricApplianceCollectionIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> electricApplianceCollectionIds);

        /**
         * 获取Map数据（Obj）
         * @param electricApplianceCollectionId
         * @return
         */
        Map<String, Object> mySelectMapById(Long electricApplianceCollectionId);

        /**
         * 新增或修改electricApplianceCollection
         * @param electricApplianceCollection
         * @return
         */
        ElectricApplianceCollection myElectricApplianceCollectionCreateUpdate(ElectricApplianceCollection electricApplianceCollection);

        /**
         * 分页获取ElectricApplianceCollection列表数据（实体类）
         * @param page
         * @param electricApplianceCollection
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceCollection electricApplianceCollection);

        /**
         * 获取ElectricApplianceCollection列表数据（Map）
         * @param map
         * @return
         */
        List<ElectricApplianceCollection> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<ElectricApplianceCollection> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<ElectricApplianceCollection> mySelectList(Wrapper<ElectricApplianceCollection> wrapper);

        /**
         * 插入ElectricApplianceCollection
         * @param electricApplianceCollection
         * @return
         */
        boolean myInsert(ElectricApplianceCollection electricApplianceCollection);

        /**
         * 批量插入List<ElectricApplianceCollection>
         * @param electricApplianceCollectionList
         * @return
         */
        boolean myInsertBatch(List<ElectricApplianceCollection> electricApplianceCollectionList);

        /**
         * 插入或更新electricApplianceCollection
         * @param electricApplianceCollection
         * @return
         */
        boolean myInsertOrUpdate(ElectricApplianceCollection electricApplianceCollection);

        /**
         * 批量插入或更新List<ElectricApplianceCollection>
         * @param electricApplianceCollectionList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<ElectricApplianceCollection> electricApplianceCollectionList);

        /**
         * 根据electricApplianceCollectionIds获取List
         * @param electricApplianceCollectionIds
         * @return
         */
        List<ElectricApplianceCollection> mySelectBatchIds(Collection<? extends Serializable> electricApplianceCollectionIds);

        /**
         * 根据electricApplianceCollectionId获取ElectricApplianceCollection
         * @param electricApplianceCollectionId
         * @return
         */
        ElectricApplianceCollection mySelectById(Serializable electricApplianceCollectionId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<ElectricApplianceCollection> wrapper);

        /**
         * 根据wrapper获取ElectricApplianceCollection
         * @param wrapper
         * @return
         */
        ElectricApplianceCollection mySelectOne(Wrapper<ElectricApplianceCollection> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<ElectricApplianceCollection> wrapper);

        /**
         * 根据electricApplianceCollection和wrapper更新electricApplianceCollection
         * @param electricApplianceCollection
         * @param wrapper
         * @return
         */
        boolean myUpdate(ElectricApplianceCollection electricApplianceCollection, Wrapper<ElectricApplianceCollection> wrapper);

        /**
         * 根据electricApplianceCollectionList更新electricApplianceCollection
         * @param electricApplianceCollectionList
         * @return
         */
        boolean myUpdateBatchById(List<ElectricApplianceCollection> electricApplianceCollectionList);

        /**
         * 根据electricApplianceCollectionId修改electricApplianceCollection
         * @param electricApplianceCollection
         * @return
         */
        boolean myUpdateById(ElectricApplianceCollection electricApplianceCollection);

}
