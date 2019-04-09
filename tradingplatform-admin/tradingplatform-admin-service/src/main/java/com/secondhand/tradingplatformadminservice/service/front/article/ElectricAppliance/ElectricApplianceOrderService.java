package com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricApplianceOrder;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : ElectricApplianceOrder 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface ElectricApplianceOrderService extends BaseService<ElectricApplianceOrder> {

        /**
         * 根据id进行假删除
         * @param electricApplianceOrderId
         * @return
         */
        Integer myFakeDeleteById(Long electricApplianceOrderId);

        /**
         * 根据ids进行批量假删除
         * @param electricApplianceOrderIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> electricApplianceOrderIds);

        /**
         * 获取Map数据（Obj）
         * @param electricApplianceOrderId
         * @return
         */
        Map<String, Object> mySelectMapById(Long electricApplianceOrderId);

        /**
         * 新增或修改electricApplianceOrder
         * @param electricApplianceOrder
         * @return
         */
        ElectricApplianceOrder myElectricApplianceOrderCreateUpdate(ElectricApplianceOrder electricApplianceOrder);

        /**
         * 分页获取ElectricApplianceOrder列表数据（实体类）
         * @param page
         * @param electricApplianceOrder
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricApplianceOrder electricApplianceOrder);

        /**
         * 获取ElectricApplianceOrder列表数据（Map）
         * @param map
         * @return
         */
        List<ElectricApplianceOrder> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<ElectricApplianceOrder> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<ElectricApplianceOrder> mySelectList(Wrapper<ElectricApplianceOrder> wrapper);

        /**
         * 插入ElectricApplianceOrder
         * @param electricApplianceOrder
         * @return
         */
        boolean myInsert(ElectricApplianceOrder electricApplianceOrder);

        /**
         * 批量插入List<ElectricApplianceOrder>
         * @param electricApplianceOrderList
         * @return
         */
        boolean myInsertBatch(List<ElectricApplianceOrder> electricApplianceOrderList);

        /**
         * 插入或更新electricApplianceOrder
         * @param electricApplianceOrder
         * @return
         */
        boolean myInsertOrUpdate(ElectricApplianceOrder electricApplianceOrder);

        /**
         * 批量插入或更新List<ElectricApplianceOrder>
         * @param electricApplianceOrderList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<ElectricApplianceOrder> electricApplianceOrderList);

        /**
         * 根据electricApplianceOrderIds获取List
         * @param electricApplianceOrderIds
         * @return
         */
        List<ElectricApplianceOrder> mySelectBatchIds(Collection<? extends Serializable> electricApplianceOrderIds);

        /**
         * 根据electricApplianceOrderId获取ElectricApplianceOrder
         * @param electricApplianceOrderId
         * @return
         */
        ElectricApplianceOrder mySelectById(Serializable electricApplianceOrderId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<ElectricApplianceOrder> wrapper);

        /**
         * 根据wrapper获取ElectricApplianceOrder
         * @param wrapper
         * @return
         */
        ElectricApplianceOrder mySelectOne(Wrapper<ElectricApplianceOrder> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<ElectricApplianceOrder> wrapper);

        /**
         * 根据electricApplianceOrder和wrapper更新electricApplianceOrder
         * @param electricApplianceOrder
         * @param wrapper
         * @return
         */
        boolean myUpdate(ElectricApplianceOrder electricApplianceOrder, Wrapper<ElectricApplianceOrder> wrapper);

        /**
         * 根据electricApplianceOrderList更新electricApplianceOrder
         * @param electricApplianceOrderList
         * @return
         */
        boolean myUpdateBatchById(List<ElectricApplianceOrder> electricApplianceOrderList);

        /**
         * 根据electricApplianceOrderId修改electricApplianceOrder
         * @param electricApplianceOrder
         * @return
         */
        boolean myUpdateById(ElectricApplianceOrder electricApplianceOrder);

}
