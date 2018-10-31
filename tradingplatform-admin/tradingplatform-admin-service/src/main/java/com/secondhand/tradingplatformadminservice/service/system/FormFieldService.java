package com.secondhand.tradingplatformadminservice.service.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : FormField 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-30
 */
public interface FormFieldService extends BaseService<FormField> {

        /**
         * 根据id进行假删除
         * @param formFieldId
         * @return
         */
        boolean fakeDeleteById(Long formFieldId);

        /**
         * 根据ids进行批量假删除
         * @param formFieldIds
         * @return
         */
        boolean fakeBatchDelete(List<Long> formFieldIds);

        /**
         * 获取Map数据（Obj）
         * @param formFieldId
         * @return
         */
        Map<String, Object> selectMapById(Long formFieldId);

        /**
         * 新增或修改formField
         * @param formField
         * @return
         */
        FormField formFieldCreateUpdate(FormField formField);

}
