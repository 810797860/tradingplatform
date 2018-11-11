package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormFieldMapper;
import com.secondhand.tradingplatformadminmapper.mapper.system.FormMapper;
import com.secondhand.tradingplatformadminservice.service.system.FormService;
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
 *   @description : Form 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-11
 */

@Service
@CacheConfig(cacheNames = "form")
public class FormServiceImpl extends BaseServiceImpl<FormMapper, Form> implements FormService {

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private FormFieldMapper formFieldMapper;

    @Override
    @CacheEvict(key = "#p0")
    public Integer myFakeDeleteById(Long formId) {

        //先假删除删掉该表的字段
        //先找出该表的字段
        FormField formField = new FormField();
        Wrapper<FormField> wrapper = new EntityWrapper<>(formField);
        wrapper.where("form_id = {0}", formId);
        wrapper.where("deleted = {0}", false);
        List<FormField> formFieldList = formFieldMapper.selectList(wrapper);
        //拼接formFieldIds
        //判空
        if (formFieldList.size() > 0){
            formFieldList.forEach(myFormField ->
            {
                //假删除
                myFormField.setDeleted(true);
                formFieldMapper.updateById(myFormField);
            });
        }

        Form form = new Form();
        form.setId(formId);
        form.setDeleted(true);
        return formMapper.updateById(form);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#p0")
    public boolean myFakeBatchDelete(List<Long> formIds) {
        for (Long formId : formIds){
            myFakeDeleteById(formId);
        }
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long formId) {
        return formMapper.selectMapById(formId);
    }

    @Override
    @CacheEvict(key = "#p0")
    public Form myFormCreateUpdate(Form form) {
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

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p1")
    public Page<Form> mySelectPageWithParam(Page<Form> page, Form form) {
        Wrapper<Form> wrapper = new EntityWrapper<>(form);
        return this.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Form> mySelectListWithMap(Map<String, Object> map) {
        return this.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Form> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Form> mySelectList(Wrapper<Form> wrapper) {
        return this.selectList(wrapper);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsert(Form form) {
        form.setUuid(ToolUtil.getUUID());
        return this.insert(form);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsertBatch(List<Form> formList) {
        for (Form form : formList){
            form.setUuid(ToolUtil.getUUID());
        }
        return this.insertBatch(formList);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsertOrUpdate(Form form) {
        //没有uuid的话要加上去
        if (form.getUuid().equals(null)){
            form.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(form);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myInsertOrUpdateBatch(List<Form> formList) {
        for (Form form : formList){
            //没有uuid的话要加上去
            if (form.getUuid().equals(null)){
                form.setUuid(ToolUtil.getUUID());
            }
        }
        return this.insertOrUpdateBatch(formList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<Form> mySelectBatchIds(Collection<? extends Serializable> formIds) {
        return this.selectBatchIds(formIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public Form mySelectById(Serializable formId) {
        return this.selectById(formId);
    }

    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Form> wrapper) {
        return this.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public Form mySelectOne(Wrapper<Form> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myUpdate(Form form, Wrapper<Form> wrapper) {
        return this.update(form, wrapper);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myUpdateBatchById(List<Form> formList) {
        return this.updateBatchById(formList);
    }

    @Override
    @CacheEvict(key = "#p0")
    public boolean myUpdateById(Form form) {
        return this.updateById(form);
    }
}
