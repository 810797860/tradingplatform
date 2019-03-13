package com.secondhand.tradingplatformadmincontroller.serviceimpl.admin.system;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.Resources;
import com.secondhand.tradingplatformadminentity.entity.admin.system.Form;
import com.secondhand.tradingplatformadminmapper.mapper.admin.system.FormFieldMapper;
import com.secondhand.tradingplatformadminmapper.mapper.admin.system.FormMapper;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.ResourcesService;
import com.secondhand.tradingplatformadminservice.service.admin.system.FormService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Autowired
    private ResourcesService resourcesService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {@CacheEvict(cacheNames = "formField", allEntries = true), @CacheEvict(cacheNames = "form", allEntries = true)})
    public Integer myFakeDeleteById(Long formId) {

//        //先假删除删掉该表的字段
//        //先找出该表的字段
//        Wrapper<FormField> wrapper = new EntityWrapper<>();
//        wrapper.where("form_id = {0}", formId);
//        wrapper.where("deleted = {0}", false);
//        List<FormField> formFieldList = formFieldService.mySelectList(wrapper);
//        //判空
//        if (formFieldList.size() > 0){
//            formFieldList.forEach(myFormField -> {
//                //假删除
//                myFormField.setDeleted(true);
//                formFieldService.myUpdateById(myFormField);
//            });
//        }

        //由于用mybatisplus写的话太长（上面）还是自己写sql吧
        //假删除掉该表的字段
        formFieldMapper.fakeDeleteByFormId(formId);

        Form form = new Form();
        form.setId(formId);
        form.setDeleted(true);
        return formMapper.updateById(form);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Form myFormCreateUpdate(Form form) {
        Long formId = form.getId();
        if (formId == null){
            form.setUuid(ToolUtil.getUUID());
            formMapper.createTable(form);
            formMapper.insert(form);

            //加上默认的权限
            //根据表名获取SimpleName
            String collectionName = "";
            String[] collections = form.getCollection().split(MagicalValue.UNDERLINE);
            if (collections.length > MagicalValue.SIMPLE_NAME_INDEX) {
                for (int i = MagicalValue.SIMPLE_NAME_INDEX; i < collections.length; i++) {
                    //首字母变为大写
                    collections[i] = collections[i].substring(0, 1).toUpperCase() + collections[i].substring(1);
                    collectionName += collections[i];
                }
                //再把第一个转换成小写，实现驼峰写法
                collectionName = collectionName.substring(0, 1).toLowerCase() + collectionName.substring(1);

                Resources resources = new Resources();
                resources.setTitle("新增或修改" + collectionName);
                resources.setUrl("/admin/" + collectionName + "/create_update");
                resources.setDescription("新增或修改" + collectionName);
                resourcesService.myResourcesCreateUpdate(resources);

                resources.setId(null);
                resources.setTitle("根据id假删除" + collectionName);
                resources.setUrl("/admin/" + collectionName + "/delete");
                resources.setDescription("根据id假删除" + collectionName);
                resourcesService.myResourcesCreateUpdate(resources);

                resources.setId(null);
                resources.setTitle("批量假删除" + collectionName);
                resources.setUrl("/admin/" + collectionName + "/batch_delete");
                resources.setDescription("批量假删除" + collectionName);
                resourcesService.myResourcesCreateUpdate(resources);
            }
        } else {
            formMapper.updateById(form);
        }
        return form;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Form myFormCreateUpdateWithResources(Form form) {
        //更新表单表
        //主要只用于新增方面
        Long formId = form.getId();
        if (formId == null){
            form.setUuid(ToolUtil.getUUID());
            formMapper.insert(form);

            //然后就加权限
            //其实就是少了一步createTable(相对于上面的)
            //加上默认的权限
            //根据表名获取SimpleName
            String collectionName = "";
            String[] collections = form.getCollection().split(MagicalValue.UNDERLINE);
            if (collections.length > MagicalValue.SIMPLE_NAME_INDEX) {
                for (int i = MagicalValue.SIMPLE_NAME_INDEX; i < collections.length; i++) {
                    //首字母变为大写
                    collections[i] = collections[i].substring(0, 1).toUpperCase() + collections[i].substring(1);
                    collectionName += collections[i];
                }
                //再把第一个转换成小写，实现驼峰写法
                collectionName = collectionName.substring(0, 1).toLowerCase() + collectionName.substring(1);

                Resources resources = new Resources();
                resources.setTitle("新增或修改" + collectionName);
                resources.setUrl("/admin/" + collectionName + "/create_update");
                resources.setDescription("新增或修改" + collectionName);
                resourcesService.myResourcesCreateUpdate(resources);

                resources.setId(null);
                resources.setTitle("根据id假删除" + collectionName);
                resources.setUrl("/admin/" + collectionName + "/delete");
                resources.setDescription("根据id假删除" + collectionName);
                resourcesService.myResourcesCreateUpdate(resources);

                resources.setId(null);
                resources.setTitle("批量假删除" + collectionName);
                resources.setUrl("/admin/" + collectionName + "/batch_delete");
                resources.setDescription("批量假删除" + collectionName);
                resourcesService.myResourcesCreateUpdate(resources);
            }

        } else {
            formMapper.updateById(form);
        }
        return form;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + '' + #p1")
    public Page<Form> mySelectPageWithParam(Page<Form> page, Form form) {
        Wrapper<Form> wrapper = new EntityWrapper<>(form);
        //字符串模糊匹配
        wrapper.like("title", form.getTitle(), SqlLike.DEFAULT);
        form.setTitle(null);
        wrapper.like("collection", form.getCollection(), SqlLike.DEFAULT);
        form.setCollection(null);
        //遍历排序
        List<Sort> sorts = form.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
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
    @CacheEvict(allEntries = true)
    public boolean myInsert(Form form) {
        form.setUuid(ToolUtil.getUUID());
        return this.insert(form);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Form> formList) {
        for (Form form : formList){
            form.setUuid(ToolUtil.getUUID());
        }
        return this.insertBatch(formList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Form form) {
        //没有uuid的话要加上去
        if (form.getUuid().equals(null)){
            form.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(form);
    }

    @Override
    @CacheEvict(allEntries = true)
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
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Form form, Wrapper<Form> wrapper) {
        return this.update(form, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Form> formList) {
        return this.updateBatchById(formList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Form form) {
        return this.updateById(form);
    }
}
