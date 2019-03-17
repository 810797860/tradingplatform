package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.OtherCategories;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : OtherCategories 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-17
 */
public interface OtherCategoriesService extends BaseService<OtherCategories> {

        /**
         * 根据id进行假删除
         * @param otherCategoriesId
         * @return
         */
        Integer myFakeDeleteById(Long otherCategoriesId);

        /**
         * 根据ids进行批量假删除
         * @param otherCategoriesIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> otherCategoriesIds);

        /**
         * 获取Map数据（Obj）
         * @param otherCategoriesId
         * @return
         */
        Map<String, Object> mySelectMapById(Long otherCategoriesId);

        /**
         * 新增或修改otherCategories
         * @param otherCategories
         * @return
         */
        OtherCategories myOtherCategoriesCreateUpdate(OtherCategories otherCategories);

        /**
         * 分页获取OtherCategories列表数据（实体类）
         * @param page
         * @param otherCategories
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, OtherCategories otherCategories);

        /**
         * 获取OtherCategories列表数据（Map）
         * @param map
         * @return
         */
        List<OtherCategories> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<OtherCategories> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<OtherCategories> mySelectList(Wrapper<OtherCategories> wrapper);

        /**
         * 插入OtherCategories
         * @param otherCategories
         * @return
         */
        boolean myInsert(OtherCategories otherCategories);

        /**
         * 批量插入List<OtherCategories>
         * @param otherCategoriesList
         * @return
         */
        boolean myInsertBatch(List<OtherCategories> otherCategoriesList);

        /**
         * 插入或更新otherCategories
         * @param otherCategories
         * @return
         */
        boolean myInsertOrUpdate(OtherCategories otherCategories);

        /**
         * 批量插入或更新List<OtherCategories>
         * @param otherCategoriesList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<OtherCategories> otherCategoriesList);

        /**
         * 根据otherCategoriesIds获取List
         * @param otherCategoriesIds
         * @return
         */
        List<OtherCategories> mySelectBatchIds(Collection<? extends Serializable> otherCategoriesIds);

        /**
         * 根据otherCategoriesId获取OtherCategories
         * @param otherCategoriesId
         * @return
         */
        OtherCategories mySelectById(Serializable otherCategoriesId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<OtherCategories> wrapper);

        /**
         * 根据wrapper获取OtherCategories
         * @param wrapper
         * @return
         */
        OtherCategories mySelectOne(Wrapper<OtherCategories> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<OtherCategories> wrapper);

        /**
         * 根据otherCategories和wrapper更新otherCategories
         * @param otherCategories
         * @param wrapper
         * @return
         */
        boolean myUpdate(OtherCategories otherCategories, Wrapper<OtherCategories> wrapper);

        /**
         * 根据otherCategoriesList更新otherCategories
         * @param otherCategoriesList
         * @return
         */
        boolean myUpdateBatchById(List<OtherCategories> otherCategoriesList);

        /**
         * 根据otherCategoriesId修改otherCategories
         * @param otherCategories
         * @return
         */
        boolean myUpdateById(OtherCategories otherCategories);

}
