package com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryEvaluation;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : BookLibraryEvaluation 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface BookLibraryEvaluationService extends BaseService<BookLibraryEvaluation> {

        /**
         * 根据id进行假删除
         * @param bookLibraryEvaluationId
         * @return
         */
        Integer myFakeDeleteById(Long bookLibraryEvaluationId);

        /**
         * 根据ids进行批量假删除
         * @param bookLibraryEvaluationIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> bookLibraryEvaluationIds);

        /**
         * 获取Map数据（Obj）
         * @param bookLibraryEvaluationId
         * @return
         */
        Map<String, Object> mySelectMapById(Long bookLibraryEvaluationId);

        /**
         * 新增或修改bookLibraryEvaluation
         * @param bookLibraryEvaluation
         * @return
         */
        BookLibraryEvaluation myBookLibraryEvaluationCreateUpdate(BookLibraryEvaluation bookLibraryEvaluation);

        /**
         * 分页获取BookLibraryEvaluation列表数据（实体类）
         * @param page
         * @param bookLibraryEvaluation
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryEvaluation bookLibraryEvaluation);

        /**
         * 获取BookLibraryEvaluation列表数据（Map）
         * @param map
         * @return
         */
        List<BookLibraryEvaluation> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<BookLibraryEvaluation> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<BookLibraryEvaluation> mySelectList(Wrapper<BookLibraryEvaluation> wrapper);

        /**
         * 插入BookLibraryEvaluation
         * @param bookLibraryEvaluation
         * @return
         */
        boolean myInsert(BookLibraryEvaluation bookLibraryEvaluation);

        /**
         * 批量插入List<BookLibraryEvaluation>
         * @param bookLibraryEvaluationList
         * @return
         */
        boolean myInsertBatch(List<BookLibraryEvaluation> bookLibraryEvaluationList);

        /**
         * 插入或更新bookLibraryEvaluation
         * @param bookLibraryEvaluation
         * @return
         */
        boolean myInsertOrUpdate(BookLibraryEvaluation bookLibraryEvaluation);

        /**
         * 批量插入或更新List<BookLibraryEvaluation>
         * @param bookLibraryEvaluationList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<BookLibraryEvaluation> bookLibraryEvaluationList);

        /**
         * 根据bookLibraryEvaluationIds获取List
         * @param bookLibraryEvaluationIds
         * @return
         */
        List<BookLibraryEvaluation> mySelectBatchIds(Collection<? extends Serializable> bookLibraryEvaluationIds);

        /**
         * 根据bookLibraryEvaluationId获取BookLibraryEvaluation
         * @param bookLibraryEvaluationId
         * @return
         */
        BookLibraryEvaluation mySelectById(Serializable bookLibraryEvaluationId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<BookLibraryEvaluation> wrapper);

        /**
         * 根据wrapper获取BookLibraryEvaluation
         * @param wrapper
         * @return
         */
        BookLibraryEvaluation mySelectOne(Wrapper<BookLibraryEvaluation> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<BookLibraryEvaluation> wrapper);

        /**
         * 根据bookLibraryEvaluation和wrapper更新bookLibraryEvaluation
         * @param bookLibraryEvaluation
         * @param wrapper
         * @return
         */
        boolean myUpdate(BookLibraryEvaluation bookLibraryEvaluation, Wrapper<BookLibraryEvaluation> wrapper);

        /**
         * 根据bookLibraryEvaluationList更新bookLibraryEvaluation
         * @param bookLibraryEvaluationList
         * @return
         */
        boolean myUpdateBatchById(List<BookLibraryEvaluation> bookLibraryEvaluationList);

        /**
         * 根据bookLibraryEvaluationId修改bookLibraryEvaluation
         * @param bookLibraryEvaluation
         * @return
         */
        boolean myUpdateById(BookLibraryEvaluation bookLibraryEvaluation);

}
