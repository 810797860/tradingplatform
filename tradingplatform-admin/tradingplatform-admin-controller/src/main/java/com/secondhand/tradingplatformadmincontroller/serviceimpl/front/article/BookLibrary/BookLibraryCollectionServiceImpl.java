package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.BookLibrary;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibrary;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryCollection;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary.BookLibraryCollectionMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary.BookLibraryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryCollectionService;
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
 *   @description : BookLibraryCollection 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "bookLibraryCollection")
public class BookLibraryCollectionServiceImpl extends BaseServiceImpl<BookLibraryCollectionMapper, BookLibraryCollection> implements BookLibraryCollectionService {

    @Autowired
    private BookLibraryCollectionMapper bookLibraryCollectionMapper;

    @Autowired
    private BookLibraryMapper bookLibraryMapper;

    @Override
    @CacheEvict(cacheNames = {"bookLibraryCollection", "myCollection"}, allEntries = true)
    public Integer myFakeDeleteById(Long bookLibraryId, Long userId) {
        //这里传的是商品id，要把收藏id找出来
        BookLibrary bookLibrary = bookLibraryMapper.selectById(bookLibraryId);
        //找出那一条收藏记录
        BookLibraryCollection bookLibraryCollection = new BookLibraryCollection();
        bookLibraryCollection.setUserId(userId);
        bookLibraryCollection.setBookId(bookLibrary.getId());
        bookLibraryCollection.setDeleted(false);
        bookLibraryCollection = bookLibraryCollectionMapper.selectOne(bookLibraryCollection);
        //假删除
        bookLibraryCollection.setDeleted(true);
        return bookLibraryCollectionMapper.updateById(bookLibraryCollection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> bookLibraryCollectionIds) {
        bookLibraryCollectionIds.forEach(bookLibraryCollectionId->{
            myFakeDeleteById(bookLibraryCollectionId, 48L);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long bookLibraryCollectionId) {
        return bookLibraryCollectionMapper.selectMapById(bookLibraryCollectionId);
    }

    @Override
    @CacheEvict(cacheNames = {"bookLibraryCollection", "myCollection"}, allEntries = true)
    public BookLibraryCollection myBookLibraryCollectionCreateUpdate(BookLibraryCollection bookLibraryCollection) {
        Long bookLibraryCollectionId = bookLibraryCollection.getId();
        if (bookLibraryCollectionId == null){
            bookLibraryCollection.setUuid(ToolUtil.getUUID());
            bookLibraryCollectionMapper.insert(bookLibraryCollection);
        } else {
            bookLibraryCollectionMapper.updateById(bookLibraryCollection);
        }
        return bookLibraryCollection;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryCollection bookLibraryCollection) {

        //判空
        bookLibraryCollection.setDeleted(false);
        Wrapper<BookLibraryCollection> wrapper = new EntityWrapper<>(bookLibraryCollection);
        //自动生成sql来回显
        wrapper.setSqlSelect("c_business_book_library_collection.id as id, c_business_book_library_collection.deleted as deleted, c_business_book_library_collection.description as description, c_business_book_library_collection.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_book_library_collection.user_id) ) AS user_id, c_business_book_library_collection.updated_by as updated_by, ( SELECT concat( '{\"id\":\"', cbbl.id, '\",\"title\":\"', cbbl.title, '\",\"cover\":\"', cbbl.cover, '\",\"price\":\"', cbbl.price, '\"}' ) FROM c_business_book_library cbbl WHERE (cbbl.id = c_business_book_library_collection.book_id) ) AS book_id, c_business_book_library_collection.created_by as created_by, c_business_book_library_collection.created_at as created_at");
        //遍历排序
        List<Sort> sorts = bookLibraryCollection.getSorts();
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
    public List<BookLibraryCollection> mySelectListWithMap(Map<String, Object> map) {
        return bookLibraryCollectionMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<BookLibraryCollection> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<BookLibraryCollection> mySelectList(Wrapper<BookLibraryCollection> wrapper) {
        return bookLibraryCollectionMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(key = "'mySelectCollectionList : ' + #p0")
    public List<Object> mySelectCollectionList(Long userId) {
        Wrapper<BookLibraryCollection> wrapper = new EntityWrapper<>();
        //没被删除的
        wrapper.setSqlSelect("book_id");
        wrapper.where("user_id = {0}", userId)
                .and("deleted = {0}", false);
        return bookLibraryCollectionMapper.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(BookLibraryCollection bookLibraryCollection) {
        bookLibraryCollection.setUuid(ToolUtil.getUUID());
        return this.insert(bookLibraryCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<BookLibraryCollection> bookLibraryCollectionList) {
        bookLibraryCollectionList.forEach(bookLibraryCollection -> {
            bookLibraryCollection.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(bookLibraryCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(BookLibraryCollection bookLibraryCollection) {
        //没有uuid的话要加上去
        if (bookLibraryCollection.getUuid().equals(null)){
            bookLibraryCollection.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(bookLibraryCollection);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<BookLibraryCollection> bookLibraryCollectionList) {
        bookLibraryCollectionList.forEach(bookLibraryCollection -> {
            //没有uuid的话要加上去
            if (bookLibraryCollection.getUuid().equals(null)){
                bookLibraryCollection.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(bookLibraryCollectionList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<BookLibraryCollection> mySelectBatchIds(Collection<? extends Serializable> bookLibraryCollectionIds) {
        return bookLibraryCollectionMapper.selectBatchIds(bookLibraryCollectionIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public BookLibraryCollection mySelectById(Serializable bookLibraryCollectionId) {
        return bookLibraryCollectionMapper.selectById(bookLibraryCollectionId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<BookLibraryCollection> wrapper) {
        return bookLibraryCollectionMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public BookLibraryCollection mySelectOne(Wrapper<BookLibraryCollection> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<BookLibraryCollection> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(BookLibraryCollection bookLibraryCollection, Wrapper<BookLibraryCollection> wrapper) {
        return this.update(bookLibraryCollection, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<BookLibraryCollection> bookLibraryCollectionList) {
        return this.updateBatchById(bookLibraryCollectionList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(BookLibraryCollection bookLibraryCollection) {
        return this.updateById(bookLibraryCollection);
    }
}
