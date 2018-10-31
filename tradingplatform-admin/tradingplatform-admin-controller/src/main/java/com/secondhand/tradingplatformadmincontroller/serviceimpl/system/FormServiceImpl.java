package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormMapper;
import com.secondhand.tradingplatformadminservice.service.system.FormService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : Form 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-30
 */

@Service
public class FormServiceImpl extends BaseServiceImpl<FormMapper, Form> implements FormService {

    @Autowired
    private FormMapper formMapper;

    @Override
    public boolean fakeDeleteById(Long formId) {
        return formMapper.fakeDeleteById(formId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> formIds) {
        return formMapper.fakeBatchDelete(formIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long formId) {
        return formMapper.selectMapById(formId);
    }

    @Override
    public Form formCreateUpdate(Form form) {
        Long formId = form.getId();
        if (formId == null){
            formMapper.insert(form);
        } else {
            formMapper.updateById(form);
        }
        return form;
    }
}