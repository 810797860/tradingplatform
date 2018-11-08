package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormMapper;
import com.secondhand.tradingplatformadminservice.service.system.FormService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *   @description : Form 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-08
 */

@Service
public class FormServiceImpl extends BaseServiceImpl<FormMapper, Form> implements FormService {

    @Autowired
    private FormMapper formMapper;

    @Override
    public Integer fakeDeleteById(Long formId) {
        Form form = new Form();
        form.setId(formId);
        form.setDeleted(true);
        return formMapper.updateById(form);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean fakeBatchDelete(List<Long> formIds) {
        for (Long formId : formIds){
            fakeDeleteById(formId);
        }
        return true;
    }

    @Override
    public Map<String, Object> selectMapById(Long formId) {
        return formMapper.selectMapById(formId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Form formCreateUpdate(Form form) {
        Long formId = form.getId();
        if (formId == null){
            form.setUuid(ToolUtil.getUUID());
            formMapper.createTable(form);
            formMapper.insert(form);
        } else {
            formMapper.updateById(form);
        }
        return form;
    }
}
