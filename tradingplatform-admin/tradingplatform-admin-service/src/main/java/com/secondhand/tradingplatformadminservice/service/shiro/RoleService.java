package com.secondhand.tradingplatformadminservice.service.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Role;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : Role 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-21
 */
public interface RoleService extends BaseService<Role> {

        /**
         * 根据id进行假删除
         * @param roleId
         * @return
         */
        Integer fakeDeleteById(Long roleId);

        /**
         * 根据ids进行批量假删除
         * @param roleIds
         * @return
         */
        boolean fakeBatchDelete(List<Long> roleIds);

        /**
         * 获取Map数据（Obj）
         * @param roleId
         * @return
         */
        Map<String, Object> selectMapById(Long roleId);

        /**
         * 新增或修改role
         * @param role
         * @return
         */
        Role roleCreateUpdate(Role role);

}
