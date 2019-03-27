package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibraryEvaluation;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibraryEvaluationMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibraryEvaluationService;
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
 *   @description : BookLibraryEvaluation 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */

@Service
@CacheConfig(cacheNames = "bookLibraryEvaluation")
public class BookLibraryEvaluationServiceImpl extends BaseServiceImpl<BookLibraryEvaluationMapper, BookLibraryEvaluation> implements BookLibraryEvaluationService {

    @Autowired
    private BookLibraryEvaluationMapper bookLibraryEvaluationMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long bookLibraryEvaluationId) {
        BookLibraryEvaluation bookLibraryEvaluation = new BookLibraryEvaluation();
        bookLibraryEvaluation.setId(bookLibraryEvaluationId);
        bookLibraryEvaluation.setDeleted(true);
        return bookLibraryEvaluationMapper.updateById(bookLibraryEvaluation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> bookLibraryEvaluationIds) {
        bookLibraryEvaluationIds.forEach(bookLibraryEvaluationId->{
            myFakeDeleteById(bookLibraryEvaluationId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long bookLibraryEvaluationId) {
        return bookLibraryEvaluationMapper.selectMapById(bookLibraryEvaluationId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public BookLibraryEvaluation myBookLibraryEvaluationCreateUpdate(BookLibraryEvaluation bookLibraryEvaluation) {
        Long bookLibraryEvaluationId = bookLibraryEvaluation.getId();
        if (bookLibraryEvaluationId == null){
            bookLibraryEvaluation.setUuid(ToolUtil.getUUID());
            bookLibraryEvaluationMapper.insert(bookLibraryEvaluation);
        } else {
            bookLibraryEvaluationMapper.updateById(bookLibraryEvaluation);
        }
        return bookLibraryEvaluation;
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, BookLibraryEvaluation bookLibraryEvaluation) {

        //判空
        bookLibraryEvaluation.setDeleted(false);
        Wrapper<BookLibraryEvaluation> wrapper = new EntityWrapper<>(bookLibraryEvaluation);
        //遍历排序
        List<Sort> sorts = bookLibraryEvaluation.getSorts();
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
    public List<BookLibraryEvaluation> mySelectListWithMap(Map<String, Object> map) {
        return bookLibraryEvaluationMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<BookLibraryEvaluation> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<BookLibraryEvaluation> mySelectList(Wrapper<BookLibraryEvaluation> wrapper) {
        return bookLibraryEvaluationMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(BookLibraryEvaluation bookLibraryEvaluation) {
        bookLibraryEvaluation.setUuid(ToolUtil.getUUID());
        return this.insert(bookLibraryEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<BookLibraryEvaluation> bookLibraryEvaluationList) {
        bookLibraryEvaluationList.forEach(bookLibraryEvaluation -> {
            bookLibraryEvaluation.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(bookLibraryEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(BookLibraryEvaluation bookLibraryEvaluation) {
        //没有uuid的话要加上去
        if (bookLibraryEvaluation.getUuid().equals(null)){
            bookLibraryEvaluation.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(bookLibraryEvaluation);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<BookLibraryEvaluation> bookLibraryEvaluationList) {
        bookLibraryEvaluationList.forEach(bookLibraryEvaluation -> {
            //没有uuid的话要加上去
            if (bookLibraryEvaluation.getUuid().equals(null)){
                bookLibraryEvaluation.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(bookLibraryEvaluationList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<BookLibraryEvaluation> mySelectBatchIds(Collection<? extends Serializable> bookLibraryEvaluationIds) {
        return bookLibraryEvaluationMapper.selectBatchIds(bookLibraryEvaluationIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public BookLibraryEvaluation mySelectById(Serializable bookLibraryEvaluationId) {
        return bookLibraryEvaluationMapper.selectById(bookLibraryEvaluationId);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<BookLibraryEvaluation> wrapper) {
        return bookLibraryEvaluationMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public BookLibraryEvaluation mySelectOne(Wrapper<BookLibraryEvaluation> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<BookLibraryEvaluation> wrapper) {
        return this.selectObjs(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(BookLibraryEvaluation bookLibraryEvaluation, Wrapper<BookLibraryEvaluation> wrapper) {
        return this.update(bookLibraryEvaluation, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<BookLibraryEvaluation> bookLibraryEvaluationList) {
        return this.updateBatchById(bookLibraryEvaluationList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(BookLibraryEvaluation bookLibraryEvaluation) {
        return this.updateById(bookLibraryEvaluation);
    }
}
