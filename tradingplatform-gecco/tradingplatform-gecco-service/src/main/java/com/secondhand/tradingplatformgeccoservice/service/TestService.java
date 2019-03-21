package com.secondhand.tradingplatformgeccoservice.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformgeccoentity.entity.Test;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : Test 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-21
 */
public interface TestService extends BaseService<Test> {

        /**
         * 根据id进行假删除
         * @param testId
         * @return
         */
        Integer myFakeDeleteById(Long testId);

        /**
         * 根据ids进行批量假删除
         * @param testIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> testIds);

        /**
         * 获取Map数据（Obj）
         * @param testId
         * @return
         */
        Map<String, Object> mySelectMapById(Long testId);

        /**
         * 新增或修改test
         * @param test
         * @return
         */
        Test myTestCreateUpdate(Test test);

        /**
         * 分页获取Test列表数据（实体类）
         * @param page
         * @param test
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, Test test);

        /**
         * 获取Test列表数据（Map）
         * @param map
         * @return
         */
        List<Test> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<Test> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<Test> mySelectList(Wrapper<Test> wrapper);

        /**
         * 插入Test
         * @param test
         * @return
         */
        boolean myInsert(Test test);

        /**
         * 批量插入List<Test>
         * @param testList
         * @return
         */
        boolean myInsertBatch(List<Test> testList);

        /**
         * 插入或更新test
         * @param test
         * @return
         */
        boolean myInsertOrUpdate(Test test);

        /**
         * 批量插入或更新List<Test>
         * @param testList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<Test> testList);

        /**
         * 根据testIds获取List
         * @param testIds
         * @return
         */
        List<Test> mySelectBatchIds(Collection<? extends Serializable> testIds);

        /**
         * 根据testId获取Test
         * @param testId
         * @return
         */
        Test mySelectById(Serializable testId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<Test> wrapper);

        /**
         * 根据wrapper获取Test
         * @param wrapper
         * @return
         */
        Test mySelectOne(Wrapper<Test> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<Test> wrapper);

        /**
         * 根据test和wrapper更新test
         * @param test
         * @param wrapper
         * @return
         */
        boolean myUpdate(Test test, Wrapper<Test> wrapper);

        /**
         * 根据testList更新test
         * @param testList
         * @return
         */
        boolean myUpdateBatchById(List<Test> testList);

        /**
         * 根据testId修改test
         * @param test
         * @return
         */
        boolean myUpdateById(Test test);

}
