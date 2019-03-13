package com.secondhand.tradingplatformadminservice.service.admin.shiro;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Resources;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : Resources 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-12
 */
public interface ResourcesService extends BaseService<Resources> {

        /**
         * 根据id进行假删除
         * @param resourcesId
         * @return
         */
        Integer myFakeDeleteById(Long resourcesId);

        /**
         * 根据ids进行批量假删除
         * @param resourcesIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> resourcesIds);

        /**
         * 获取Map数据（Obj）
         * @param resourcesId
         * @return
         */
        Map<String, Object> mySelectMapById(Long resourcesId);

        /**
         * 新增或修改resources
         * @param resources
         * @return
         */
        Resources myResourcesCreateUpdate(Resources resources);

        /**
         * 分页获取Resources列表数据（实体类）
         * @param page
         * @param resources
         * @param roleId
         * @return
         */
        Page<Resources> mySelectPageWithParam(Page<Resources> page, Resources resources);

        /**
         * 根据资源搜寻列表
         * @param resources
         * @param roleId
         * @return
         */
        List<Resources> mySelectListWithParam(Resources resources, Long roleId);

        /**
         * 获取Resources列表数据（Map）
         * @param map
         * @return
         */
        List<Resources> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<Resources> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<Resources> mySelectList(Wrapper<Resources> wrapper);

        /**
         * 插入Resources
         * @param resources
         * @return
         */
        boolean myInsert(Resources resources);

        /**
         * 批量插入List<Resources>
         * @param resourcesList
         * @return
         */
        boolean myInsertBatch(List<Resources> resourcesList);

        /**
         * 插入或更新resources
         * @param resources
         * @return
         */
        boolean myInsertOrUpdate(Resources resources);

        /**
         * 批量插入或更新List<Resources>
         * @param resourcesList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<Resources> resourcesList);

        /**
         * 根据resourcesIds获取List
         * @param resourcesIds
         * @return
         */
        List<Resources> mySelectBatchIds(Collection<? extends Serializable> resourcesIds);

        /**
         * 根据resourcesId获取Resources
         * @param resourcesId
         * @return
         */
        Resources mySelectById(Serializable resourcesId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<Resources> wrapper);

        /**
         * 根据wrapper获取Resources
         * @param wrapper
         * @return
         */
        Resources mySelectOne(Wrapper<Resources> wrapper);

        /**
         * 根据resources和wrapper更新resources
         * @param resources
         * @param wrapper
         * @return
         */
        boolean myUpdate(Resources resources, Wrapper<Resources> wrapper);

        /**
         * 根据resourcesList更新resources
         * @param resourcesList
         * @return
         */
        boolean myUpdateBatchById(List<Resources> resourcesList);

        /**
         * 根据resourcesId修改resources
         * @param resources
         * @return
         */
        boolean myUpdateById(Resources resources);

        /**
         * 查询所有的Resources(ShiroConfig)
         * @return
         */
        List<Resources> myQueryAll();

        /**
         * 加载所有的user_resources(MyShiroRealm)
         * @param userId
         * @return
         */
        List<Resources> myLoadUserResources(Long userId);
}
