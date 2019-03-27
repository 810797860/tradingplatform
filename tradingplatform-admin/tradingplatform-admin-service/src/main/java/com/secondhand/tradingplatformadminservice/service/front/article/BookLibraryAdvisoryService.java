package com.secondhand.tradingplatformadminservice.service.front.article;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibraryAdvisory;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : BookLibraryAdvisory 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface BookLibraryAdvisoryService extends BaseService<BookLibraryAdvisory> {

        /**
         * 根据id进行假删除
         * @param bookLibraryAdvisoryId
         * @return
         */
        Integer myFakeDeleteById(Long bookLibraryAdvisoryId);

        /**
         * 根据ids进行批量假删除
         * @param bookLibraryAdvisoryIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> bookLibraryAdvisoryIds);

        /**
         * 获取Map数据（Obj）
         * @param bookLibraryAdvisoryId
         * @return
         */
        Map<String, Object> mySelectMapById(Long bookLibraryAdvisoryId);

        /**
         * 新增或修改bookLibraryAdvisory
         * @param bookLibraryAdvisory
         * @return
         */
        BookLibraryAdvisory myBookLibraryAdvisoryCreateUpdate(BookLibraryAdvisory bookLibraryAdvisory);

        /**
         * 分页获取BookLibraryAdvisory列表数据（实体类）
         * @param page
         * @param bookLibraryAdvisory
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryAdvisory bookLibraryAdvisory);

        /**
         * 获取BookLibraryAdvisory列表数据（Map）
         * @param map
         * @return
         */
        List<BookLibraryAdvisory> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<BookLibraryAdvisory> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<BookLibraryAdvisory> mySelectList(Wrapper<BookLibraryAdvisory> wrapper);

        /**
         * 插入BookLibraryAdvisory
         * @param bookLibraryAdvisory
         * @return
         */
        boolean myInsert(BookLibraryAdvisory bookLibraryAdvisory);

        /**
         * 批量插入List<BookLibraryAdvisory>
         * @param bookLibraryAdvisoryList
         * @return
         */
        boolean myInsertBatch(List<BookLibraryAdvisory> bookLibraryAdvisoryList);

        /**
         * 插入或更新bookLibraryAdvisory
         * @param bookLibraryAdvisory
         * @return
         */
        boolean myInsertOrUpdate(BookLibraryAdvisory bookLibraryAdvisory);

        /**
         * 批量插入或更新List<BookLibraryAdvisory>
         * @param bookLibraryAdvisoryList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<BookLibraryAdvisory> bookLibraryAdvisoryList);

        /**
         * 根据bookLibraryAdvisoryIds获取List
         * @param bookLibraryAdvisoryIds
         * @return
         */
        List<BookLibraryAdvisory> mySelectBatchIds(Collection<? extends Serializable> bookLibraryAdvisoryIds);

        /**
         * 根据bookLibraryAdvisoryId获取BookLibraryAdvisory
         * @param bookLibraryAdvisoryId
         * @return
         */
        BookLibraryAdvisory mySelectById(Serializable bookLibraryAdvisoryId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<BookLibraryAdvisory> wrapper);

        /**
         * 根据wrapper获取BookLibraryAdvisory
         * @param wrapper
         * @return
         */
        BookLibraryAdvisory mySelectOne(Wrapper<BookLibraryAdvisory> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<BookLibraryAdvisory> wrapper);

        /**
         * 根据bookLibraryAdvisory和wrapper更新bookLibraryAdvisory
         * @param bookLibraryAdvisory
         * @param wrapper
         * @return
         */
        boolean myUpdate(BookLibraryAdvisory bookLibraryAdvisory, Wrapper<BookLibraryAdvisory> wrapper);

        /**
         * 根据bookLibraryAdvisoryList更新bookLibraryAdvisory
         * @param bookLibraryAdvisoryList
         * @return
         */
        boolean myUpdateBatchById(List<BookLibraryAdvisory> bookLibraryAdvisoryList);

        /**
         * 根据bookLibraryAdvisoryId修改bookLibraryAdvisory
         * @param bookLibraryAdvisory
         * @return
         */
        boolean myUpdateById(BookLibraryAdvisory bookLibraryAdvisory);

}
