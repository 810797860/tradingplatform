package com.secondhand.tradingplatformadmincontroller.serviceimpl.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformadminmapper.mapper.shiro.ResourcesMapper;
import com.secondhand.tradingplatformadminservice.service.shiro.ResourcesService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : Resources 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-20
 */

@Service
public class ResourcesServiceImpl extends BaseServiceImpl<ResourcesMapper, Resources> implements ResourcesService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    public boolean fakeDeleteById(Long resourcesId) {
        return resourcesMapper.fakeDeleteById(resourcesId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> resourcesIds) {
        return resourcesMapper.fakeBatchDelete(resourcesIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long resourcesId) {
        return resourcesMapper.selectMapById(resourcesId);
    }

    @Override
    public Resources resourcesCreateUpdate(Resources resources) {
        Long resourcesId = resources.getId();
        if (resourcesId == null){
            resourcesMapper.insert(resources);
        } else {
            resourcesMapper.updateById(resources);
        }
        return resources;
    }
}
