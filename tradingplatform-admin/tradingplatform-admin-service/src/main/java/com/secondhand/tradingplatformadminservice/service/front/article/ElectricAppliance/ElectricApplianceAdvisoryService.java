package com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceAdvisory;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : ElectricApplianceAdvisory 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface ElectricApplianceAdvisoryService extends BaseService<ElectricApplianceAdvisory> {

        /**
         * 根据id进行假删除
         * @param electricApplianceAdvisoryId
         * @return
         */
        Integer myFakeDeleteById(Long electricApplianceAdvisoryId);

        /**
         * 根据ids进行批量假删除
         * @param electricApplianceAdvisoryIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> electricApplianceAdvisoryIds);

        /**
         * 获取Map数据（Obj）
         * @param electricApplianceAdvisoryId
         * @return
         */
        Map<String, Object> mySelectMapById(Long electricApplianceAdvisoryId);

        /**
         * 新增或修改electricApplianceAdvisory
         * @param electricApplianceAdvisory
         * @return
         */
        ElectricApplianceAdvisory myElectricApplianceAdvisoryCreateUpdate(ElectricApplianceAdvisory electricApplianceAdvisory);

        /**
         * 分页获取ElectricApplianceAdvisory列表数据（实体类）
         * @param page
         * @param electricApplianceAdvisory
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceAdvisory electricApplianceAdvisory);

        /**
         * 获取ElectricApplianceAdvisory列表数据（Map）
         * @param map
         * @return
         */
        List<ElectricApplianceAdvisory> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<ElectricApplianceAdvisory> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<ElectricApplianceAdvisory> mySelectList(Wrapper<ElectricApplianceAdvisory> wrapper);

        /**
         * 插入ElectricApplianceAdvisory
         * @param electricApplianceAdvisory
         * @return
         */
        boolean myInsert(ElectricApplianceAdvisory electricApplianceAdvisory);

        /**
         * 批量插入List<ElectricApplianceAdvisory>
         * @param electricApplianceAdvisoryList
         * @return
         */
        boolean myInsertBatch(List<ElectricApplianceAdvisory> electricApplianceAdvisoryList);

        /**
         * 插入或更新electricApplianceAdvisory
         * @param electricApplianceAdvisory
         * @return
         */
        boolean myInsertOrUpdate(ElectricApplianceAdvisory electricApplianceAdvisory);

        /**
         * 批量插入或更新List<ElectricApplianceAdvisory>
         * @param electricApplianceAdvisoryList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<ElectricApplianceAdvisory> electricApplianceAdvisoryList);

        /**
         * 根据electricApplianceAdvisoryIds获取List
         * @param electricApplianceAdvisoryIds
         * @return
         */
        List<ElectricApplianceAdvisory> mySelectBatchIds(Collection<? extends Serializable> electricApplianceAdvisoryIds);

        /**
         * 根据electricApplianceAdvisoryId获取ElectricApplianceAdvisory
         * @param electricApplianceAdvisoryId
         * @return
         */
        ElectricApplianceAdvisory mySelectById(Serializable electricApplianceAdvisoryId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<ElectricApplianceAdvisory> wrapper);

        /**
         * 根据wrapper获取ElectricApplianceAdvisory
         * @param wrapper
         * @return
         */
        ElectricApplianceAdvisory mySelectOne(Wrapper<ElectricApplianceAdvisory> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<ElectricApplianceAdvisory> wrapper);

        /**
         * 根据electricApplianceAdvisory和wrapper更新electricApplianceAdvisory
         * @param electricApplianceAdvisory
         * @param wrapper
         * @return
         */
        boolean myUpdate(ElectricApplianceAdvisory electricApplianceAdvisory, Wrapper<ElectricApplianceAdvisory> wrapper);

        /**
         * 根据electricApplianceAdvisoryList更新electricApplianceAdvisory
         * @param electricApplianceAdvisoryList
         * @return
         */
        boolean myUpdateBatchById(List<ElectricApplianceAdvisory> electricApplianceAdvisoryList);

        /**
         * 根据electricApplianceAdvisoryId修改electricApplianceAdvisory
         * @param electricApplianceAdvisory
         * @return
         */
        boolean myUpdateById(ElectricApplianceAdvisory electricApplianceAdvisory);

}
