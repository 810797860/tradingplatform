package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquareAdvisory;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : DigitalSquareAdvisory 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface DigitalSquareAdvisoryService extends BaseService<DigitalSquareAdvisory> {

        /**
         * 根据id进行假删除
         * @param digitalSquareAdvisoryId
         * @return
         */
        Integer myFakeDeleteById(Long digitalSquareAdvisoryId);

        /**
         * 根据ids进行批量假删除
         * @param digitalSquareAdvisoryIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> digitalSquareAdvisoryIds);

        /**
         * 获取Map数据（Obj）
         * @param digitalSquareAdvisoryId
         * @return
         */
        Map<String, Object> mySelectMapById(Long digitalSquareAdvisoryId);

        /**
         * 新增或修改digitalSquareAdvisory
         * @param digitalSquareAdvisory
         * @return
         */
        DigitalSquareAdvisory myDigitalSquareAdvisoryCreateUpdate(DigitalSquareAdvisory digitalSquareAdvisory);

        /**
         * 分页获取DigitalSquareAdvisory列表数据（实体类）
         * @param page
         * @param digitalSquareAdvisory
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquareAdvisory digitalSquareAdvisory);

        /**
         * 获取DigitalSquareAdvisory列表数据（Map）
         * @param map
         * @return
         */
        List<DigitalSquareAdvisory> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<DigitalSquareAdvisory> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<DigitalSquareAdvisory> mySelectList(Wrapper<DigitalSquareAdvisory> wrapper);

        /**
         * 插入DigitalSquareAdvisory
         * @param digitalSquareAdvisory
         * @return
         */
        boolean myInsert(DigitalSquareAdvisory digitalSquareAdvisory);

        /**
         * 批量插入List<DigitalSquareAdvisory>
         * @param digitalSquareAdvisoryList
         * @return
         */
        boolean myInsertBatch(List<DigitalSquareAdvisory> digitalSquareAdvisoryList);

        /**
         * 插入或更新digitalSquareAdvisory
         * @param digitalSquareAdvisory
         * @return
         */
        boolean myInsertOrUpdate(DigitalSquareAdvisory digitalSquareAdvisory);

        /**
         * 批量插入或更新List<DigitalSquareAdvisory>
         * @param digitalSquareAdvisoryList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<DigitalSquareAdvisory> digitalSquareAdvisoryList);

        /**
         * 根据digitalSquareAdvisoryIds获取List
         * @param digitalSquareAdvisoryIds
         * @return
         */
        List<DigitalSquareAdvisory> mySelectBatchIds(Collection<? extends Serializable> digitalSquareAdvisoryIds);

        /**
         * 根据digitalSquareAdvisoryId获取DigitalSquareAdvisory
         * @param digitalSquareAdvisoryId
         * @return
         */
        DigitalSquareAdvisory mySelectById(Serializable digitalSquareAdvisoryId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<DigitalSquareAdvisory> wrapper);

        /**
         * 根据wrapper获取DigitalSquareAdvisory
         * @param wrapper
         * @return
         */
        DigitalSquareAdvisory mySelectOne(Wrapper<DigitalSquareAdvisory> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<DigitalSquareAdvisory> wrapper);

        /**
         * 根据digitalSquareAdvisory和wrapper更新digitalSquareAdvisory
         * @param digitalSquareAdvisory
         * @param wrapper
         * @return
         */
        boolean myUpdate(DigitalSquareAdvisory digitalSquareAdvisory, Wrapper<DigitalSquareAdvisory> wrapper);

        /**
         * 根据digitalSquareAdvisoryList更新digitalSquareAdvisory
         * @param digitalSquareAdvisoryList
         * @return
         */
        boolean myUpdateBatchById(List<DigitalSquareAdvisory> digitalSquareAdvisoryList);

        /**
         * 根据digitalSquareAdvisoryId修改digitalSquareAdvisory
         * @param digitalSquareAdvisory
         * @return
         */
        boolean myUpdateById(DigitalSquareAdvisory digitalSquareAdvisory);

}
