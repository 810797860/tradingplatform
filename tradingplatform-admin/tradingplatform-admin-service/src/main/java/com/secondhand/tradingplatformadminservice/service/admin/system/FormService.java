package com.secondhand.tradingplatformadminservice.service.admin.system;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.system.Form;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : Form 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-11
 */
public interface FormService extends BaseService<Form> {

        /**
         * 根据id进行假删除
         * @param formId
         * @return
         */
        Integer myFakeDeleteById(Long formId);

        /**
         * 根据ids进行批量假删除
         * @param formIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> formIds);

        /**
         * 获取Map数据（Obj）
         * @param formId
         * @return
         */
        Map<String, Object> mySelectMapById(Long formId);

        /**
         * 新增或修改form
         * @param form
         * @return
         */
        Form myFormCreateUpdate(Form form);

        /**
         * 新增或修改form同是加权限（后台列表开发用）
         * @param form
         * @return
         */
        Form myFormCreateUpdateWithResources(Form form);

        /**
         * 分页获取Form列表数据（实体类）
         * @param page
         * @param form
         * @return
         */
        Page<Form> mySelectPageWithParam(Page<Form> page, Form form);

        /**
         * 获取Form列表数据（Map）
         * @param map
         * @return
         */
        List<Form> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<Form> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<Form> mySelectList(Wrapper<Form> wrapper);

        /**
         * 插入Form
         * @param form
         * @return
         */
        boolean myInsert(Form form);

        /**
         * 批量插入List<Form>
         * @param formList
         * @return
         */
        boolean myInsertBatch(List<Form> formList);

        /**
         * 插入或更新form
         * @param form
         * @return
         */
        boolean myInsertOrUpdate(Form form);

        /**
         * 批量插入或更新List<Form>
         * @param formList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<Form> formList);

        /**
         * 根据formIds获取List
         * @param formIds
         * @return
         */
        List<Form> mySelectBatchIds(Collection<? extends Serializable> formIds);

        /**
         * 根据formId获取Form
         * @param formId
         * @return
         */
        Form mySelectById(Serializable formId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<Form> wrapper);

        /**
         * 根据wrapper获取Form
         * @param wrapper
         * @return
         */
        Form mySelectOne(Wrapper<Form> wrapper);

        /**
         * 根据form和wrapper更新form
         * @param form
         * @param wrapper
         * @return
         */
        boolean myUpdate(Form form, Wrapper<Form> wrapper);

        /**
         * 根据formList更新form
         * @param formList
         * @return
         */
        boolean myUpdateBatchById(List<Form> formList);

        /**
         * 根据formId修改form
         * @param form
         * @return
         */
        boolean myUpdateById(Form form);

}
