package com.secondhand.tradingplatformadminservice.service.system;

import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItem 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-31
 */
public interface SelectItemService extends BaseService<SelectItem> {

        /**
         * 根据id进行假删除
         * @param selectItemId
         * @return
         */
        boolean fakeDeleteById(Long selectItemId);

        /**
         * 根据ids进行批量假删除
         * @param selectItemIds
         * @return
         */
        boolean fakeBatchDelete(List<Long> selectItemIds);

        /**
         * 获取Map数据（Obj）
         * @param selectItemId
         * @return
         */
        Map<String, Object> selectMapById(Long selectItemId);

        /**
         * 新增或修改selectItem
         * @param selectItem
         * @return
         */
        SelectItem selectItemCreateUpdate(SelectItem selectItem);

}
