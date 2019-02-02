package com.secondhand.tradingplatformadmincontroller.serviceimpl.system;

import com.baomidou.mybatisplus.enums.SqlLike;
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
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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
    @CacheEvict(allEntries = true)
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
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> formFieldIds) {
        formFieldIds.forEach(formFieldId -> {
            myFakeDeleteById(formFieldId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long formFieldId) {
        return formFieldMapper.selectMapById(formFieldId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean formFieldUpdateByFormId(Long formId) {
        //获取表名
        Form form = formMapper.selectById(formId);
        String formName = form.getCollection();

        //查询字典表，获取该表物理字段
        List<Map<String, Object>> myFormFieldList = formFieldMapper.selectByInformationSchema(formName);
        myFormFieldList.forEach(myFormField -> {
            FormField formField = new FormField();
            formField.setFormId(formId);
            formField.setTitle(myFormField.get("COLUMN_COMMENT").toString());
            formField.setDescription(formField.getTitle());
            formField.setFieldName(myFormField.get("COLUMN_NAME").toString());

            //bigint默认了关联选择（整型？）
            //longtext默认了HTML编辑器（文本？）
            //text默认了文本（附件？）
            //判断字段类型与展示类型
            String dataType = myFormField.get("DATA_TYPE").toString();
            if (dataType.equals(MagicalValue.DISPLAY_TYPE_BIGINT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_BIGINT_ASSOCIATED);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_ASSOCIATED_CHOICE);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_LONGTEXT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_LONGTEXT_EDITOR);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_HTML_EDITOR);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_TEXT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_TEXT_TEXT);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_TEXT);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_DATETIME)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_DATETIME);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_DATE);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_VARCHAR)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_VARCHAR);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_TEXT);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_MEDIUMTEXT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_MEDIUMTEXT);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_TEXT);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_BIT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_BIT);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_BIT);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_FLOAT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_FLOAT);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_FLOATING_POINT);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_DOUBLE)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_DOUBLE);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_FLOATING_POINT);

            } else if (dataType.equals(MagicalValue.DISPLAY_TYPE_INT)){
                formField.setFieldType(SystemSelectItem.FIELD_TYPE_INT);
                formField.setShowType(SystemSelectItem.SHOW_TYPE_INTEGER);
            }

            //判断是否为必填项
            String isNullable = myFormField.get("IS_NULLABLE").toString();
            if (isNullable.equals(MagicalValue.BOOLEAN_YES)){
                formField.setRequired(false);
            } else if (isNullable.equals(MagicalValue.BOOLEAN_NO)){
                formField.setRequired(true);
            }

            //默认值
            //判空
            if (myFormField.containsKey(MagicalValue.INFORMATION_SCHEMA_COLUMN_DEFAULT)){
                String columnDefault = myFormField.get("COLUMN_DEFAULT").toString();
                formField.setDefaultValue(columnDefault);
            }

            formField.setUuid(ToolUtil.getUUID());
            formFieldMapper.insert(formField);
        });
        return true;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + '' + #p1")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, FormField formField) {
        Wrapper<FormField> wrapper = new EntityWrapper<>(formField);
        wrapper.setSqlSelect("s_base_form_field.id as id, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = s_base_form_field.show_type)) AS show_type, s_base_form_field.show_type as show_type, s_base_form_field.created_by as created_by, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = s_base_form_field.field_type)) AS field_type, s_base_form_field.deleted as deleted, s_base_form_field.field_name as field_name, s_base_form_field.description as description, s_base_form_field.updated_at as updated_at, s_base_form_field.title as title, s_base_form_field.default_value as default_value, s_base_form_field.updated_by as updated_by, s_base_form_field.form_id as form_id, s_base_form_field.required as required, s_base_form_field.created_at as created_at");
        //字符串模糊匹配
        wrapper.like("title", formField.getTitle(), SqlLike.DEFAULT);
        formField.setTitle(null);
        wrapper.like("field_name", formField.getFieldName(), SqlLike.DEFAULT);
        formField.setFieldName(null);
        wrapper.like("default_value", formField.getDefaultValue(), SqlLike.DEFAULT);
        formField.setDefaultValue(null);
        //遍历排序
        List<Sort> sorts = formField.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectMapsPage(page, wrapper);
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
    @CacheEvict(allEntries = true)
    public boolean myInsert(FormField formField) {
        formField.setUuid(ToolUtil.getUUID());
        return this.insert(formField);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<FormField> formFieldList) {
        formFieldList.forEach(formField -> {
            formField.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(formFieldList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(FormField formField) {
        //没有uuid的话要加上去
        if (formField.getUuid().equals(null)){
            formField.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(formField);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<FormField> formFieldList) {
        formFieldList.forEach(formField -> {
            //没有uuid的话要加上去
            if (formField.getUuid().equals(null)){
                formField.setUuid(ToolUtil.getUUID());
            }
        });
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
    @CacheEvict(allEntries = true)
    public boolean myUpdate(FormField formField, Wrapper<FormField> wrapper) {
        return this.update(formField, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<FormField> formFieldList) {
        return this.updateBatchById(formFieldList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(FormField formField) {
        return this.updateById(formField);
    }
}
