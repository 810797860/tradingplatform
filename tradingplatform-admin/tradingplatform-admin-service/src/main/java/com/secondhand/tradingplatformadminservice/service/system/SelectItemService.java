package com.secondhand.tradingplatformadminservice.service.system;

import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItem 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */
public interface SelectItemService extends BaseService<SelectItem> {

        /**
         * 根据id进行假删除
         * @param selectItemId
         * @return
         */
        Integer fakeDeleteById(Long selectItemId);

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
         * 根据pid获取List数据(Obj)
         * @param pid
         * @return
         */
        List<Map<String, Object>> getSelectItemByPidForList(Long pid);

        /**
         * 新增或修改selectItem
         * @param selectItem
         * @return
         */
        SelectItem selectItemCreateUpdate(SelectItem selectItem);

        /**
         * 根据pid获取子级枚举列表
         * @param pid
         * @return
         */
        List<SelectItem> getItemsByPid(Long pid);

        /**
         * 根据pid递归获取所有子级枚举列表
         * @param pid
         * @return
         */
        List<SelectItem> getAllItemsByPid(Long pid);
}
