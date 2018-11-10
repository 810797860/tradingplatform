package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformadminentity.entity.system.SelectItem;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormFieldMapper;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormMapper;
import com.secondhand.tradingplatformadminmapper.mapper.system.SelectItemMapper;
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

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private SelectItemMapper selectItemMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public FormField formFieldCreateUpdate(FormField formField) {
        Long formFieldId = formField.getId();
        if (formFieldId == null){
            formField.setUuid(ToolUtil.getUUID());
            formFieldMapper.insert(formField);
            //获取表名及字段类型
            Form form = formMapper.selectById(formField.getFormId());
            String formName = form.getCollection();
            SelectItem selectItem = selectItemMapper.selectById(formField.getFieldType());
            String fieldType = selectItem.getTitle();
            formFieldMapper.createField(formName, fieldType, formField);
        } else {
            //先获取原来的字段名
            FormField oldFormField = formFieldMapper.selectById(formField.getId());
            String oldFieldName = oldFormField.getFieldName();
            //获取表名
            Form form = formMapper.selectById(formField.getFormId());
            String formName = form.getCollection();
            //获取字段类型
            SelectItem selectItem = selectItemMapper.selectById(formField.getFieldType());
            String fieldType = selectItem.getTitle();
            formFieldMapper.changeField(formName, oldFieldName, fieldType, formField);
            formFieldMapper.updateById(formField);
        }
        return formField;
    }
}
