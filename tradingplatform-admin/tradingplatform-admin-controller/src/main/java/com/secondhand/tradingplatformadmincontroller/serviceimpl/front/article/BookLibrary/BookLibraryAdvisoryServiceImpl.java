package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.BookLibrary;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryAdvisory;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary.BookLibraryAdvisoryMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryAdvisoryService;
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
 *   @description : BookLibraryAdvisory 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "bookLibraryAdvisory")
public class BookLibraryAdvisoryServiceImpl extends BaseServiceImpl<BookLibraryAdvisoryMapper, BookLibraryAdvisory> implements BookLibraryAdvisoryService {

    @Autowired
    private BookLibraryAdvisoryMapper bookLibraryAdvisoryMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long bookLibraryAdvisoryId) {
        BookLibraryAdvisory bookLibraryAdvisory = new BookLibraryAdvisory();
        bookLibraryAdvisory.setId(bookLibraryAdvisoryId);
        bookLibraryAdvisory.setDeleted(true);
        return bookLibraryAdvisoryMapper.updateById(bookLibraryAdvisory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> bookLibraryAdvisoryIds) {
        bookLibraryAdvisoryIds.forEach(bookLibraryAdvisoryId->{
            myFakeDeleteById(bookLibraryAdvisoryId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long bookLibraryAdvisoryId) {
        return bookLibraryAdvisoryMapper.selectMapById(bookLibraryAdvisoryId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public BookLibraryAdvisory myBookLibraryAdvisoryCreateUpdate(BookLibraryAdvisory bookLibraryAdvisory) {
        Long bookLibraryAdvisoryId = bookLibraryAdvisory.getId();
        if (bookLibraryAdvisoryId == null){
            bookLibraryAdvisory.setUuid(ToolUtil.getUUID());
            bookLibraryAdvisoryMapper.insert(bookLibraryAdvisory);
        } else {
            bookLibraryAdvisoryMapper.updateById(bookLibraryAdvisory);
        }
        return bookLibraryAdvisory;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryAdvisory bookLibraryAdvisory) {

        //判空
        bookLibraryAdvisory.setDeleted(false);
        Wrapper<BookLibraryAdvisory> wrapper = new EntityWrapper<>(bookLibraryAdvisory);
        //用自动生成的sql设置回显
        wrapper.setSqlSelect("c_business_book_library_advisory.id as id, c_business_book_library_advisory.description as description, c_business_book_library_advisory.updated_at as updated_at, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_book_library_advisory.back_check_status)) AS back_check_status, c_business_book_library_advisory.pid as pid, c_business_book_library_advisory.updated_by as updated_by, c_business_book_library_advisory.not_pass_reason as not_pass_reason, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_book_library_advisory.user_id) ) AS user_id, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_book_library_advisory.reply_id) ) AS reply_id, c_business_book_library_advisory.created_by as created_by, ( SELECT concat( '{\"id\":\"', cbbl.id, '\",\"title\":\"', cbbl.title, '\",\"cover\":\"', cbbl.cover, '\",\"price\":\"', cbbl.price, '\"}' ) FROM c_business_book_library cbbl WHERE (cbbl.id = c_business_book_library_advisory.book_id) ) AS book_id, c_business_book_library_advisory.deleted as deleted, c_business_book_library_advisory.content as content, c_business_book_library_advisory.created_at as created_at");
        //遍历排序
        List<Sort> sorts = bookLibraryAdvisory.getSorts();
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
    public List<BookLibraryAdvisory> mySelectListWithMap(Map<String, Object> map) {
        return bookLibraryAdvisoryMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<BookLibraryAdvisory> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<BookLibraryAdvisory> mySelectList(Wrapper<BookLibraryAdvisory> wrapper) {
        return bookLibraryAdvisoryMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(BookLibraryAdvisory bookLibraryAdvisory) {
        bookLibraryAdvisory.setUuid(ToolUtil.getUUID());
        return this.insert(bookLibraryAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<BookLibraryAdvisory> bookLibraryAdvisoryList) {
        bookLibraryAdvisoryList.forEach(bookLibraryAdvisory -> {
            bookLibraryAdvisory.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(bookLibraryAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(BookLibraryAdvisory bookLibraryAdvisory) {
        //没有uuid的话要加上去
        if (bookLibraryAdvisory.getUuid().equals(null)){
            bookLibraryAdvisory.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(bookLibraryAdvisory);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<BookLibraryAdvisory> bookLibraryAdvisoryList) {
        bookLibraryAdvisoryList.forEach(bookLibraryAdvisory -> {
            //没有uuid的话要加上去
            if (bookLibraryAdvisory.getUuid().equals(null)){
                bookLibraryAdvisory.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(bookLibraryAdvisoryList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<BookLibraryAdvisory> mySelectBatchIds(Collection<? extends Serializable> bookLibraryAdvisoryIds) {
        return bookLibraryAdvisoryMapper.selectBatchIds(bookLibraryAdvisoryIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public BookLibraryAdvisory mySelectById(Serializable bookLibraryAdvisoryId) {
        return bookLibraryAdvisoryMapper.selectById(bookLibraryAdvisoryId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<BookLibraryAdvisory> wrapper) {
        return bookLibraryAdvisoryMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public BookLibraryAdvisory mySelectOne(Wrapper<BookLibraryAdvisory> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<BookLibraryAdvisory> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(BookLibraryAdvisory bookLibraryAdvisory, Wrapper<BookLibraryAdvisory> wrapper) {
        return this.update(bookLibraryAdvisory, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<BookLibraryAdvisory> bookLibraryAdvisoryList) {
        return this.updateBatchById(bookLibraryAdvisoryList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(BookLibraryAdvisory bookLibraryAdvisory) {
        return this.updateById(bookLibraryAdvisory);
    }
}
