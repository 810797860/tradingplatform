package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : FormField 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */

@Service
@CacheConfig(cacheNames = "formField")
public class FormFieldServiceImpl extends BaseServiceImpl<FormFieldMapper, FormField> implements FormFieldService {

    @Autowired
    private FormFieldMapper formFieldMapper;

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private SelectItemMapper selectItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#p0")
    public Integer myFakeDeleteById(Long formFieldId) {
//        为了代码安全，暂时不让删除，只删除记录(备用)

//        //先找出该条记录
//        FormField deleteFormField = formFieldMapper.selectById(formFieldId);
//        //找出表名与字段名
//        Form form = formMapper.selectById(deleteFormField.getFormId());
//        String tableName = form.getCollection();
//        String fieldName = deleteFormField.getFieldName();
//        formFieldMapper.deleteField(tableName, fieldName);

        FormField formField = new FormField();
        formField.setId(formFieldId);
        formField.setDeleted(true);
        return formFieldMapper.updateById(formField);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#p0")
    public boolean myFakeBatchDelete(List<Long> formFieldIds) {
        for (Long formFieldId : formFieldIds){
            myFakeDeleteById(formFieldId);
        }
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long formFieldId) {
        return formFieldMapper.selectMapById(formFieldId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#p0")
    public FormField myFormFieldCreateUpdate(FormField formField) {
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
//            为了代码安全，暂时不让改，只改记录(备用)

//            //先获取原来的字段名
//            FormField oldFormField = formFieldMapper.selectById(formField.getId());
//            String oldFieldName = oldFormField.getFieldName();
//            //获取表名
//            Form form = formMapper.selectById(formField.getFormId());
//            String formName = form.getCollection();
//            //获取字段类型
//            SelectItem selectItem = selectItemMapper.selectById(formField.getFieldType());
//            String fieldType = selectItem.getTitle();
//            formFieldMapper.changeField(formName, oldFieldName, fieldType, formField);

            formFieldMapper.updateById(formField);
        }
        return formField;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p1")
    public Page<FormField> mySelectPageWithParam(Page<FormField> page, FormField formField) {
        Wrapper<FormField> wrapper = new EntityWrapper<>(formField);
        return this.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<FormField> mySelectListWithMap(Map<String, Object> map) {
        return this.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<FormField> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<FormField> mySelectList(Wrapper<FormField> wrapper) {
        return this.selectList(wrapper);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsert(FormField formField) {
        formField.setUuid(ToolUtil.getUUID());
        return this.insert(formField);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsertBatch(List<FormField> formFieldList) {
        for (FormField formField : formFieldList){
            formField.setUuid(ToolUtil.getUUID());
        }
        return this.insertBatch(formFieldList);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsertOrUpdate(FormField formField) {
        //没有uuid的话要加上去
        if (formField.getUuid().equals(null)){
            formField.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(formField);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsertOrUpdateBatch(List<FormField> formFieldList) {
        for (FormField formField : formFieldList){
            //没有uuid的话要加上去
            if (formField.getUuid().equals(null)){
                formField.setUuid(ToolUtil.getUUID());
            }
        }
        return this.insertOrUpdateBatch(formFieldList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<FormField> mySelectBatchIds(Collection<? extends Serializable> formFieldIds) {
        return this.selectBatchIds(formFieldIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public FormField mySelectById(Serializable formFieldId) {
        return this.selectById(formFieldId);
    }

    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<FormField> wrapper) {
        return this.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public FormField mySelectOne(Wrapper<FormField> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myUpdate(FormField formField, Wrapper<FormField> wrapper) {
        return this.update(formField, wrapper);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myUpdateBatchById(List<FormField> formFieldList) {
        return this.updateBatchById(formFieldList);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myUpdateById(FormField formField) {
        return this.updateById(formField);
    }
}
