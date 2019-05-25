package com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryCollection;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : BookLibraryCollection 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
public interface BookLibraryCollectionService extends BaseService<BookLibraryCollection> {

        /**
         * 根据id进行假删除
         * @param bookLibraryCollectionId
         * @param userId
         * @return
         */
        Integer myFakeDeleteById(Long bookLibraryId, Long userId);

        /**
         * 根据ids进行批量假删除
         * @param bookLibraryCollectionIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> bookLibraryCollectionIds);

        /**
         * 获取Map数据（Obj）
         * @param bookLibraryCollectionId
         * @return
         */
        Map<String, Object> mySelectMapById(Long bookLibraryCollectionId);

        /**
         * 新增或修改bookLibraryCollection
         * @param bookLibraryCollection
         * @return
         */
        BookLibraryCollection myBookLibraryCollectionCreateUpdate(BookLibraryCollection bookLibraryCollection);

        /**
         * 分页获取BookLibraryCollection列表数据（实体类）
         * @param page
         * @param bookLibraryCollection
         * @return
         */
        Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryCollection bookLibraryCollection);

        /**
         * 获取BookLibraryCollection列表数据（Map）
         * @param map
         * @return
         */
        List<BookLibraryCollection> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<BookLibraryCollection> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<BookLibraryCollection> mySelectList(Wrapper<BookLibraryCollection> wrapper);

        /**
         * 根据userId获取收藏的ids
         * @param userId
         * @return
         */
        List<Object> mySelectCollectionList(Long userId);

        /**
         * 插入BookLibraryCollection
         * @param bookLibraryCollection
         * @return
         */
        boolean myInsert(BookLibraryCollection bookLibraryCollection);

        /**
         * 批量插入List<BookLibraryCollection>
         * @param bookLibraryCollectionList
         * @return
         */
        boolean myInsertBatch(List<BookLibraryCollection> bookLibraryCollectionList);

        /**
         * 插入或更新bookLibraryCollection
         * @param bookLibraryCollection
         * @return
         */
        boolean myInsertOrUpdate(BookLibraryCollection bookLibraryCollection);

        /**
         * 批量插入或更新List<BookLibraryCollection>
         * @param bookLibraryCollectionList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<BookLibraryCollection> bookLibraryCollectionList);

        /**
         * 根据bookLibraryCollectionIds获取List
         * @param bookLibraryCollectionIds
         * @return
         */
        List<BookLibraryCollection> mySelectBatchIds(Collection<? extends Serializable> bookLibraryCollectionIds);

        /**
         * 根据bookLibraryCollectionId获取BookLibraryCollection
         * @param bookLibraryCollectionId
         * @return
         */
        BookLibraryCollection mySelectById(Serializable bookLibraryCollectionId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<BookLibraryCollection> wrapper);

        /**
         * 根据wrapper获取BookLibraryCollection
         * @param wrapper
         * @return
         */
        BookLibraryCollection mySelectOne(Wrapper<BookLibraryCollection> wrapper);

        /**
         * 根据wrapper获取List<Object>
         * @param wrapper
         * @return
         */
        List<Object> mySelectObjs(Wrapper<BookLibraryCollection> wrapper);

        /**
         * 根据bookLibraryCollection和wrapper更新bookLibraryCollection
         * @param bookLibraryCollection
         * @param wrapper
         * @return
         */
        boolean myUpdate(BookLibraryCollection bookLibraryCollection, Wrapper<BookLibraryCollection> wrapper);

        /**
         * 根据bookLibraryCollectionList更新bookLibraryCollection
         * @param bookLibraryCollectionList
         * @return
         */
        boolean myUpdateBatchById(List<BookLibraryCollection> bookLibraryCollectionList);

        /**
         * 根据bookLibraryCollectionId修改bookLibraryCollection
         * @param bookLibraryCollection
         * @return
         */
        boolean myUpdateById(BookLibraryCollection bookLibraryCollection);

}
