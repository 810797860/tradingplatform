package com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance.ElectricAppliance;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : ElectricAppliance 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface ElectricApplianceService extends BaseService<ElectricAppliance> {

    /**
     * 根据id进行假删除
     *
     * @param electricApplianceId
     * @return
     */
    Integer myFakeDeleteById(Long electricApplianceId);

    /**
     * 根据ids进行批量假删除
     *
     * @param electricApplianceIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> electricApplianceIds);

    /**
     * 根据ids进行批量审核通过
     *
     * @param electricApplianceIds
     * @return
     */
    boolean myExaminationBatchPass(List<Long> electricApplianceIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param electricApplianceId
     * @return
     */
    Map<String, Object> mySelectMapById(Long electricApplianceId);

    /**
     * 新增或修改electricAppliance
     *
     * @param electricAppliance
     * @return
     */
    ElectricAppliance myElectricApplianceCreateUpdate(ElectricAppliance electricAppliance);

    /**
     * 分页获取ElectricAppliance列表数据（实体类）
     *
     * @param page
     * @param electricAppliance
     * @return
     */
    Page<Map<String, Object>> mySelectPageWithParam(Page page, ElectricAppliance electricAppliance);

    /**
     * 获取ElectricAppliance列表数据（Map）
     *
     * @param map
     * @return
     */
    List<ElectricAppliance> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<ElectricAppliance> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<ElectricAppliance> mySelectList(Wrapper<ElectricAppliance> wrapper);

    /**
     * 插入ElectricAppliance
     *
     * @param electricAppliance
     * @return
     */
    boolean myInsert(ElectricAppliance electricAppliance);

    /**
     * 批量插入List<ElectricAppliance>
     *
     * @param electricApplianceList
     * @return
     */
    boolean myInsertBatch(List<ElectricAppliance> electricApplianceList);

    /**
     * 插入或更新electricAppliance
     *
     * @param electricAppliance
     * @return
     */
    boolean myInsertOrUpdate(ElectricAppliance electricAppliance);

    /**
     * 批量插入或更新List<ElectricAppliance>
     *
     * @param electricApplianceList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<ElectricAppliance> electricApplianceList);

    /**
     * 根据electricApplianceIds获取List
     *
     * @param electricApplianceIds
     * @return
     */
    List<ElectricAppliance> mySelectBatchIds(Collection<? extends Serializable> electricApplianceIds);

    /**
     * 根据electricApplianceId获取ElectricAppliance
     *
     * @param electricApplianceId
     * @return
     */
    ElectricAppliance mySelectById(Serializable electricApplianceId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<ElectricAppliance> wrapper);

    /**
     * 根据wrapper获取ElectricAppliance
     *
     * @param wrapper
     * @return
     */
    ElectricAppliance mySelectOne(Wrapper<ElectricAppliance> wrapper);

    /**
     * 根据wrapper获取List<Object>
     *
     * @param wrapper
     * @return
     */
    List<Object> mySelectObjs(Wrapper<ElectricAppliance> wrapper);

    /**
     * 根据electricAppliance和wrapper更新electricAppliance
     *
     * @param electricAppliance
     * @param wrapper
     * @return
     */
    boolean myUpdate(ElectricAppliance electricAppliance, Wrapper<ElectricAppliance> wrapper);

    /**
     * 根据electricApplianceList更新electricAppliance
     *
     * @param electricApplianceList
     * @return
     */
    boolean myUpdateBatchById(List<ElectricAppliance> electricApplianceList);

    /**
     * 根据electricApplianceId修改electricAppliance
     *
     * @param electricAppliance
     * @return
     */
    boolean myUpdateById(ElectricAppliance electricAppliance);

}
