package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormFieldMapper;
import com.secondhand.tradingplatformadminservice.service.system.FormFieldService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : FormField 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-30
 */

@Service
public class FormFieldServiceImpl extends BaseServiceImpl<FormFieldMapper, FormField> implements FormFieldService {

    @Autowired
    private FormFieldMapper formFieldMapper;

    @Override
    public boolean fakeDeleteById(Long formFieldId) {
        return formFieldMapper.fakeDeleteById(formFieldId);
    }

    @Override
    public boolean fakeBatchDelete(List<Long> formFieldIds) {
        return formFieldMapper.fakeBatchDelete(formFieldIds);
    }

    @Override
    public Map<String, Object> selectMapById(Long formFieldId) {
        return formFieldMapper.selectMapById(formFieldId);
    }

    @Override
    public FormField formFieldCreateUpdate(FormField formField) {
        Long formFieldId = formField.getId();
        if (formFieldId == null){
            formFieldMapper.insert(formField);
        } else {
            formFieldMapper.updateById(formField);
        }
        return formField;
    }
}
