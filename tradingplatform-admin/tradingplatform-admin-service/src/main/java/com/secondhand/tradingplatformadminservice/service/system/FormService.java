package com.secondhand.tradingplatformadminservice.service.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : Form 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-30
 */
public interface FormService extends BaseService<Form> {

        /**
         * 根据id进行假删除
         * @param formId
         * @return
         */
        boolean fakeDeleteById(Long formId);

        /**
         * 根据ids进行批量假删除
         * @param formIds
         * @return
         */
        boolean fakeBatchDelete(List<Long> formIds);

        /**
         * 获取Map数据（Obj）
         * @param formId
         * @return
         */
        Map<String, Object> selectMapById(Long formId);

        /**
         * 新增或修改form
         * @param form
         * @return
         */
        Form formCreateUpdate(Form form);

}
