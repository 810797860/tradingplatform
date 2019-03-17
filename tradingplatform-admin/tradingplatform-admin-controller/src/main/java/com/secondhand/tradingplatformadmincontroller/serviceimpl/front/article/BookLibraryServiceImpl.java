package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibraryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibraryService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
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
 * @author zhangjk
 * @description : BookLibrary 服务实现类
 * ---------------------------------
 * @since 2019-03-16
 */

@Service
@CacheConfig(cacheNames = "bookLibrary")
public class BookLibraryServiceImpl extends BaseServiceImpl<BookLibraryMapper, BookLibrary> implements BookLibraryService {

    @Autowired
    private BookLibraryMapper bookLibraryMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long bookLibraryId) {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.setId(bookLibraryId);
        bookLibrary.setDeleted(true);
        return bookLibraryMapper.updateById(bookLibrary);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> bookLibraryIds) {
        bookLibraryIds.forEach(bookLibraryId -> {
            myFakeDeleteById(bookLibraryId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long bookLibraryId) {
        return bookLibraryMapper.selectMapById(bookLibraryId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public BookLibrary myBookLibraryCreateUpdate(BookLibrary bookLibrary) {
        Long bookLibraryId = bookLibrary.getId();
        if (bookLibraryId == null) {
            bookLibrary.setUuid(ToolUtil.getUUID());
            bookLibraryMapper.insert(bookLibrary);
        } else {
            bookLibraryMapper.updateById(bookLibrary);
        }
        return bookLibrary;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibrary bookLibrary) {

        //判空
        bookLibrary.setDeleted(false);
        Wrapper<BookLibrary> wrapper = new EntityWrapper<>(bookLibrary);
        //遍历排序
        List<Sort> sorts = bookLibrary.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectMapsPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<BookLibrary> mySelectListWithMap(Map<String, Object> map) {
        return bookLibraryMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<BookLibrary> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<BookLibrary> mySelectList(Wrapper<BookLibrary> wrapper) {
        return bookLibraryMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(BookLibrary bookLibrary) {
        bookLibrary.setUuid(ToolUtil.getUUID());
        return this.insert(bookLibrary);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<BookLibrary> bookLibraryList) {
        bookLibraryList.forEach(bookLibrary -> {
            bookLibrary.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(bookLibraryList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(BookLibrary bookLibrary) {
        //没有uuid的话要加上去
        if (bookLibrary.getUuid().equals(null)) {
            bookLibrary.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(bookLibrary);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<BookLibrary> bookLibraryList) {
        bookLibraryList.forEach(bookLibrary -> {
            //没有uuid的话要加上去
            if (bookLibrary.getUuid().equals(null)) {
                bookLibrary.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(bookLibraryList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<BookLibrary> mySelectBatchIds(Collection<? extends Serializable> bookLibraryIds) {
        return bookLibraryMapper.selectBatchIds(bookLibraryIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public BookLibrary mySelectById(Serializable bookLibraryId) {
        return bookLibraryMapper.selectById(bookLibraryId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<BookLibrary> wrapper) {
        return bookLibraryMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public BookLibrary mySelectOne(Wrapper<BookLibrary> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<BookLibrary> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(BookLibrary bookLibrary, Wrapper<BookLibrary> wrapper) {
        return this.update(bookLibrary, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<BookLibrary> bookLibraryList) {
        return this.updateBatchById(bookLibraryList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(BookLibrary bookLibrary) {
        return this.updateById(bookLibrary);
    }
}
