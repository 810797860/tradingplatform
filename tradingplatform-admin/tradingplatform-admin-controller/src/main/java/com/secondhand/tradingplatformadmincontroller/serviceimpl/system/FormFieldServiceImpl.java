package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormFieldMapper;
import com.secondhand.tradingplatformadminservice.service.system.FormFieldService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *   @description : FormField 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */

@Service
public class FormFieldServiceImpl extends BaseServiceImpl<FormFieldMapper, FormField> implements FormFieldService {

    @Autowired
    private FormFieldMapper formFieldMapper;

    @Override
    public Integer fakeDeleteById(Long formFieldId) {
        FormField formField = new FormField();
        formField.setId(formFieldId);
        formField.setDeleted(true);
        return formFieldMapper.updateById(formField);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean fakeBatchDelete(List<Long> formFieldIds) {
        for (Long formFieldId : formFieldIds){
            fakeDeleteById(formFieldId);
        }
        return true;
    }

    @Override
    public Map<String, Object> selectMapById(Long formFieldId) {
        return formFieldMapper.selectMapById(formFieldId);
    }

    @Override
    public FormField formFieldCreateUpdate(FormField formField) {
        Long formFieldId = formField.getId();
        if (formFieldId == null){
            formField.setUuid(ToolUtil.getUUID());
            formFieldMapper.insert(formField);
        } else {
            formFieldMapper.updateById(formField);
        }
        return formField;
    }
}
