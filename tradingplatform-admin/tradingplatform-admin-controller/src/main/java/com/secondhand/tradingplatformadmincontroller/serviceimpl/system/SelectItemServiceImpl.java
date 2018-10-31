package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminmapper.mapper.system.SelectItemMapper;
import com.secondhand.tradingplatformadminservice.service.system.SelectItemService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItem 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-31
 */

@Service
public class SelectItemServiceImpl extends BaseServiceImpl<SelectItemMapper, SelectItem> implements SelectItemService {

    @Autowired
    private SelectItemMapper selectItemMapper;

    @Override
    public boolean fakeDeleteById(Long selectItemId) {
        return selectItemMapper.fakeDeleteById(selectItemId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> selectItemIds) {
        return selectItemMapper.fakeBatchDelete(selectItemIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long selectItemId) {
        return selectItemMapper.selectMapById(selectItemId);
    }

    @Override
    public SelectItem selectItemCreateUpdate(SelectItem selectItem) {
        Long selectItemId = selectItem.getId();
        if (selectItemId == null){
            selectItemMapper.insert(selectItem);
        } else {
            selectItemMapper.updateById(selectItem);
        }
        return selectItem;
    }

    @Override
    public List<SelectItem> getItemsByPid(Long pid) {
        Wrapper<SelectItem> wrapper = new EntityWrapper<>();
        wrapper.where("pid = {0}", pid);
        wrapper.where("deleted = {0}", false);
        return this.selectList(wrapper);
    }
}
