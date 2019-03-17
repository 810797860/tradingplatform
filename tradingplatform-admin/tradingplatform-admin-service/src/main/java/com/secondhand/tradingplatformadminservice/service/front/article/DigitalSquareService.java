package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : DigitalSquare 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-17
 */
public interface DigitalSquareService extends BaseService<DigitalSquare> {

        /**
         * 根据id进行假删除
         * @param digitalSquareId
         * @return
         */
        Integer myFakeDeleteById(Long digitalSquareId);

        /**
         * 根据ids进行批量假删除
         * @param digitalSquareIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> digitalSquareIds);

        /**
         * 获取Map数据（Obj）
         * @param digitalSquareId
         * @return
         */
        Map<String, Object> mySelectMapById(Long digitalSquareId);

        /**
         * 新增或修改digitalSquare
         * @param digitalSquare
         * @return
         */
        DigitalSquare myDigitalSquareCreateUpdate(DigitalSquare digitalSquare);

        /**
         * 分页获取DigitalSquare列表数据（实体类）
         * @param page
         * @param digitalSquare
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquare digitalSquare);

        /**
         * 获取DigitalSquare列表数据（Map）
         * @param map
         * @return
         */
        List<DigitalSquare> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<DigitalSquare> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<DigitalSquare> mySelectList(Wrapper<DigitalSquare> wrapper);

        /**
         * 插入DigitalSquare
         * @param digitalSquare
         * @return
         */
        boolean myInsert(DigitalSquare digitalSquare);

        /**
         * 批量插入List<DigitalSquare>
         * @param digitalSquareList
         * @return
         */
        boolean myInsertBatch(List<DigitalSquare> digitalSquareList);

        /**
         * 插入或更新digitalSquare
         * @param digitalSquare
         * @return
         */
        boolean myInsertOrUpdate(DigitalSquare digitalSquare);

        /**
         * 批量插入或更新List<DigitalSquare>
         * @param digitalSquareList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<DigitalSquare> digitalSquareList);

        /**
         * 根据digitalSquareIds获取List
         * @param digitalSquareIds
         * @return
         */
        List<DigitalSquare> mySelectBatchIds(Collection<? extends Serializable> digitalSquareIds);

        /**
         * 根据digitalSquareId获取DigitalSquare
         * @param digitalSquareId
         * @return
         */
        DigitalSquare mySelectById(Serializable digitalSquareId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<DigitalSquare> wrapper);

        /**
         * 根据wrapper获取DigitalSquare
         * @param wrapper
         * @return
         */
        DigitalSquare mySelectOne(Wrapper<DigitalSquare> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<DigitalSquare> wrapper);

        /**
         * 根据digitalSquare和wrapper更新digitalSquare
         * @param digitalSquare
         * @param wrapper
         * @return
         */
        boolean myUpdate(DigitalSquare digitalSquare, Wrapper<DigitalSquare> wrapper);

        /**
         * 根据digitalSquareList更新digitalSquare
         * @param digitalSquareList
         * @return
         */
        boolean myUpdateBatchById(List<DigitalSquare> digitalSquareList);

        /**
         * 根据digitalSquareId修改digitalSquare
         * @param digitalSquare
         * @return
         */
        boolean myUpdateById(DigitalSquare digitalSquare);

}
