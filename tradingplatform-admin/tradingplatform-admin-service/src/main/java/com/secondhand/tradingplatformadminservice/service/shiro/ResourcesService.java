package com.secondhand.tradingplatformadminservice.service.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 *   @description : Resources 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-20
 */
public interface ResourcesService extends BaseService<Resources> {

        /**
         * 根据id进行假删除
         * @param resourcesId
         * @return
         */
        boolean fakeDeleteById(Long resourcesId);

        /**
         * 根据ids进行批量假删除
         * @param resourcesIds
         * @return
         */
        boolean fakeBatchDelete(List<Long> resourcesIds);

        /**
         * 获取Map数据（Obj）
         * @param resourcesId
         * @return
         */
        Map<String, Object> selectMapById(Long resourcesId);

        /**
         * 新增或修改resources
         * @param resources
         * @return
         */
        Resources resourcesCreateUpdate(Resources resources);

        /**
         * 查询所有的Resources(ShiroConfig)
         * @return
         */
        List<Resources> queryAll();

        /**
         * 加载所有的user_resources(MyShiroRealm)
         * @param map
         * @return
         */
        List<Resources> loadUserResources(Map<String,Object> map);

}
