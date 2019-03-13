package com.secondhand.tradingplatformadminservice.service.admin.system;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.system.FormField;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : FormField 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */
public interface FormFieldService extends BaseService<FormField> {

        /**
         * 根据id进行假删除
         * @param formFieldId
         * @return
         */
        Integer myFakeDeleteById(Long formFieldId);

        /**
         * 根据ids进行批量假删除
         * @param formFieldIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> formFieldIds);

        /**
         * 获取Map数据（Obj）
         * @param formFieldId
         * @return
         */
        Map<String, Object> mySelectMapById(Long formFieldId);

        /**
         * 新增或修改formField
         * @param formField
         * @return
         */
        FormField myFormFieldCreateUpdate(FormField formField);

        /**
         * 根据表单id更新FormField
         * @param formId
         * @return
         */
        boolean formFieldUpdateByFormId(Long formId);

        /**
         * 分页获取FormField列表数据（实体类）
         * @param page
         * @param formField
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, FormField formField);

        /**
         * 获取FormField列表数据（Map）
         * @param map
         * @return
         */
        List<FormField> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<FormField> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<FormField> mySelectList(Wrapper<FormField> wrapper);

        /**
         * 插入FormField
         * @param formField
         * @return
         */
        boolean myInsert(FormField formField);

        /**
         * 批量插入List<FormField>
         * @param formFieldList
         * @return
         */
        boolean myInsertBatch(List<FormField> formFieldList);

        /**
         * 插入或更新formField
         * @param formField
         * @return
         */
        boolean myInsertOrUpdate(FormField formField);

        /**
         * 批量插入或更新List<FormField>
         * @param formFieldList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<FormField> formFieldList);

        /**
         * 根据formFieldIds获取List
         * @param formFieldIds
         * @return
         */
        List<FormField> mySelectBatchIds(Collection<? extends Serializable> formFieldIds);

        /**
         * 根据formFieldId获取FormField
         * @param formFieldId
         * @return
         */
        FormField mySelectById(Serializable formFieldId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<FormField> wrapper);

        /**
         * 根据wrapper获取FormField
         * @param wrapper
         * @return
         */
        FormField mySelectOne(Wrapper<FormField> wrapper);

        /**
         * 根据formField和wrapper更新formField
         * @param formField
         * @param wrapper
         * @return
         */
        boolean myUpdate(FormField formField, Wrapper<FormField> wrapper);

        /**
         * 根据formFieldList更新formField
         * @param formFieldList
         * @return
         */
        boolean myUpdateBatchById(List<FormField> formFieldList);

        /**
         * 根据formFieldId修改formField
         * @param formField
         * @return
         */
        boolean myUpdateById(FormField formField);
}
