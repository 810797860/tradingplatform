package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminmapper.mapper.system.SelectItemMapper;
import com.secondhand.tradingplatformadminservice.service.system.SelectItemService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *   @description : SelectItem 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */

@Service
public class SelectItemServiceImpl extends BaseServiceImpl<SelectItemMapper, SelectItem> implements SelectItemService {

    @Autowired
    private SelectItemMapper selectItemMapper;

    @Override
    public Integer fakeDeleteById(Long selectItemId) {
        SelectItem selectItem = new SelectItem();
        selectItem.setId(selectItemId);
        selectItem.setDeleted(true);
        return selectItemMapper.updateById(selectItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean fakeBatchDelete(List<Long> selectItemIds) {
        for (Long selectItemId : selectItemIds){
            fakeDeleteById(selectItemId);
        }
        return true;
    }

    @Override
    public Map<String, Object> selectMapById(Long selectItemId) {
        return selectItemMapper.selectMapById(selectItemId);
    }

    @Override
    public List<Map<String, Object>> getSelectItemByPidForList(Long pid) {
        return null;
    }

    @Override
    public SelectItem selectItemCreateUpdate(SelectItem selectItem) {
        Long selectItemId = selectItem.getId();
        if (selectItemId == null){
            selectItem.setUuid(ToolUtil.getUUID());
            selectItemMapper.insert(selectItem);
        } else {
            selectItemMapper.updateById(selectItem);
        }
        return selectItem;
    }

    @Override
    public List<SelectItem> getItemsByPid(Long pid) {
        return selectItemMapper.getItemsByPid(pid);
    }

    @Override
    public List<SelectItem> getAllItemsByPid(Long pid) {
        return selectItemMapper.getAllItemsByPid(pid);
    }
}
