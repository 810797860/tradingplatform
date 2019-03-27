package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibraryOrder;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibraryOrderMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibraryOrderService;
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
 *   @description : BookLibraryOrder 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "bookLibraryOrder")
public class BookLibraryOrderServiceImpl extends BaseServiceImpl<BookLibraryOrderMapper, BookLibraryOrder> implements BookLibraryOrderService {

    @Autowired
    private BookLibraryOrderMapper bookLibraryOrderMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long bookLibraryOrderId) {
        BookLibraryOrder bookLibraryOrder = new BookLibraryOrder();
        bookLibraryOrder.setId(bookLibraryOrderId);
        bookLibraryOrder.setDeleted(true);
        return bookLibraryOrderMapper.updateById(bookLibraryOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> bookLibraryOrderIds) {
        bookLibraryOrderIds.forEach(bookLibraryOrderId->{
            myFakeDeleteById(bookLibraryOrderId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long bookLibraryOrderId) {
        return bookLibraryOrderMapper.selectMapById(bookLibraryOrderId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public BookLibraryOrder myBookLibraryOrderCreateUpdate(BookLibraryOrder bookLibraryOrder) {
        Long bookLibraryOrderId = bookLibraryOrder.getId();
        if (bookLibraryOrderId == null){
            bookLibraryOrder.setUuid(ToolUtil.getUUID());
            bookLibraryOrderMapper.insert(bookLibraryOrder);
        } else {
            bookLibraryOrderMapper.updateById(bookLibraryOrder);
        }
        return bookLibraryOrder;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryOrder bookLibraryOrder) {

        //判空
        bookLibraryOrder.setDeleted(false);
        Wrapper<BookLibraryOrder> wrapper = new EntityWrapper<>(bookLibraryOrder);
        //遍历排序
        List<Sort> sorts = bookLibraryOrder.getSorts();
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
    public List<BookLibraryOrder> mySelectListWithMap(Map<String, Object> map) {
        return bookLibraryOrderMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<BookLibraryOrder> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<BookLibraryOrder> mySelectList(Wrapper<BookLibraryOrder> wrapper) {
        return bookLibraryOrderMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(BookLibraryOrder bookLibraryOrder) {
        bookLibraryOrder.setUuid(ToolUtil.getUUID());
        return this.insert(bookLibraryOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<BookLibraryOrder> bookLibraryOrderList) {
        bookLibraryOrderList.forEach(bookLibraryOrder -> {
            bookLibraryOrder.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(bookLibraryOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(BookLibraryOrder bookLibraryOrder) {
        //没有uuid的话要加上去
        if (bookLibraryOrder.getUuid().equals(null)){
            bookLibraryOrder.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(bookLibraryOrder);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<BookLibraryOrder> bookLibraryOrderList) {
        bookLibraryOrderList.forEach(bookLibraryOrder -> {
            //没有uuid的话要加上去
            if (bookLibraryOrder.getUuid().equals(null)){
                bookLibraryOrder.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(bookLibraryOrderList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<BookLibraryOrder> mySelectBatchIds(Collection<? extends Serializable> bookLibraryOrderIds) {
        return bookLibraryOrderMapper.selectBatchIds(bookLibraryOrderIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public BookLibraryOrder mySelectById(Serializable bookLibraryOrderId) {
        return bookLibraryOrderMapper.selectById(bookLibraryOrderId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<BookLibraryOrder> wrapper) {
        return bookLibraryOrderMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public BookLibraryOrder mySelectOne(Wrapper<BookLibraryOrder> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<BookLibraryOrder> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(BookLibraryOrder bookLibraryOrder, Wrapper<BookLibraryOrder> wrapper) {
        return this.update(bookLibraryOrder, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<BookLibraryOrder> bookLibraryOrderList) {
        return this.updateBatchById(bookLibraryOrderList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(BookLibraryOrder bookLibraryOrder) {
        return this.updateById(bookLibraryOrder);
    }
}
