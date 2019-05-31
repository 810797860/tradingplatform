package com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibrary;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : BookLibrary 服务接口
 * ---------------------------------
 * @since 2019-03-16
 */
public interface BookLibraryService extends BaseService<BookLibrary> {

    /**
     * 根据id进行假删除
     *
     * @param bookLibraryId
     * @return
     */
    Integer myFakeDeleteById(Long bookLibraryId);

    /**
     * 根据ids进行批量假删除
     *
     * @param bookLibraryIds
     * @return
     */
    boolean myFakeBatchDelete(List<Long> bookLibraryIds);

    /**
     * 根据ids进行批量审核通过
     *
     * @param bookLibraryIds
     * @return
     */
    boolean myExaminationBatchPass(List<Long> bookLibraryIds);

    /**
     * 获取Map数据（Obj）
     *
     * @param bookLibraryId
     * @return
     */
    Map<String, Object> mySelectMapById(Long bookLibraryId);

    /**
     * 新增或修改bookLibrary
     *
     * @param bookLibrary
     * @return
     */
    BookLibrary myBookLibraryCreateUpdate(BookLibrary bookLibrary);

    /**
     * 分页获取BookLibrary列表数据（实体类）
     *
     * @param page
     * @param bookLibrary
     * @return
     */
    Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibrary bookLibrary);

    /**
     * 获取BookLibrary列表数据（Map）
     *
     * @param map
     * @return
     */
    List<BookLibrary> mySelectListWithMap(Map<String, Object> map);

    /**
     * 根据wrapper获取map
     *
     * @param wrapper
     * @return
     */
    Map<String, Object> mySelectMap(Wrapper<BookLibrary> wrapper);

    /**
     * 根据wrapper获取List
     *
     * @param wrapper
     * @return
     */
    List<BookLibrary> mySelectList(Wrapper<BookLibrary> wrapper);

    /**
     * 插入BookLibrary
     *
     * @param bookLibrary
     * @return
     */
    boolean myInsert(BookLibrary bookLibrary);

    /**
     * 批量插入List<BookLibrary>
     *
     * @param bookLibraryList
     * @return
     */
    boolean myInsertBatch(List<BookLibrary> bookLibraryList);

    /**
     * 插入或更新bookLibrary
     *
     * @param bookLibrary
     * @return
     */
    boolean myInsertOrUpdate(BookLibrary bookLibrary);

    /**
     * 批量插入或更新List<BookLibrary>
     *
     * @param bookLibraryList
     * @return
     */
    boolean myInsertOrUpdateBatch(List<BookLibrary> bookLibraryList);

    /**
     * 根据bookLibraryIds获取List
     *
     * @param bookLibraryIds
     * @return
     */
    List<BookLibrary> mySelectBatchIds(Collection<? extends Serializable> bookLibraryIds);

    /**
     * 根据bookLibraryId获取BookLibrary
     *
     * @param bookLibraryId
     * @return
     */
    BookLibrary mySelectById(Serializable bookLibraryId);

    /**
     * 根据wrapper查找Count
     *
     * @param wrapper
     * @return
     */
    int mySelectCount(Wrapper<BookLibrary> wrapper);

    /**
     * 根据wrapper获取BookLibrary
     *
     * @param wrapper
     * @return
     */
    BookLibrary mySelectOne(Wrapper<BookLibrary> wrapper);

    /**
     * 根据wrapper获取List<Object>
     *
     * @param wrapper
     * @return
     */
    List<Object> mySelectObjs(Wrapper<BookLibrary> wrapper);

    /**
     * 根据bookLibrary和wrapper更新bookLibrary
     *
     * @param bookLibrary
     * @param wrapper
     * @return
     */
    boolean myUpdate(BookLibrary bookLibrary, Wrapper<BookLibrary> wrapper);

    /**
     * 根据bookLibraryList更新bookLibrary
     *
     * @param bookLibraryList
     * @return
     */
    boolean myUpdateBatchById(List<BookLibrary> bookLibraryList);

    /**
     * 根据bookLibraryId修改bookLibrary
     *
     * @param bookLibrary
     * @return
     */
    boolean myUpdateById(BookLibrary bookLibrary);

    /**
     * 立即购买bookLibrary
     * @param bookLibraryId
     * @param balance
     * @param userId
     * @return
     * @throws CustomizeException
     * @throws ClientException
     */
    Float mySettlementById(Long bookLibraryId, Float balance, Long userId) throws CustomizeException, ClientException;
}
