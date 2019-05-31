package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.BookLibrary;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibrary;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryOrder;
import com.secondhand.tradingplatformadminmapper.mapper.admin.shiro.UserMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary.BookLibraryMapper;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.BookLibrary.BookLibraryOrderMapper;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.BusinessSelectItem;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.CustomizeStatus;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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

    @Autowired
    private BookLibraryOrderMapper bookLibraryOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShortMessageService shortMessageService;

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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myExaminationBatchPass(List<Long> bookLibraryIds) {
        bookLibraryIds.forEach(bookLibraryId -> {
            BookLibrary bookLibrary = new BookLibrary();
            bookLibrary.setId(bookLibraryId);
            bookLibrary.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            bookLibrary.setNotPassReason("");
            myUpdateById(bookLibrary);
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
        //自定义sql回显
        wrapper.setSqlSelect("c_business_book_library.id as id, c_business_book_library.comment_num as comment_num, c_business_book_library.details as details, c_business_book_library.description as description, c_business_book_library.paper as paper, c_business_book_library.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_book_library.user_id) ) AS user_id, c_business_book_library.back_check_time as back_check_time, c_business_book_library.title as title, c_business_book_library.enfold as enfold, c_business_book_library.updated_by as updated_by, c_business_book_library.author as author, c_business_book_library.not_pass_reason as not_pass_reason, c_business_book_library.cover as cover, c_business_book_library.suited as suited, c_business_book_library.publishing_house as publishing_house, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_book_library.back_check_status)) AS back_check_status, c_business_book_library.price as price, c_business_book_library.isbn as isbn, c_business_book_library.created_by as created_by, c_business_book_library.published_time as published_time, c_business_book_library.star as star, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_book_library.classification)) AS classification, c_business_book_library.deleted as deleted, c_business_book_library.format as format, c_business_book_library.created_at as created_at")
        //字符串模糊匹配
                .like("title", bookLibrary.getTitle(), SqlLike.DEFAULT)
                .like("price", bookLibrary.getPrice() == null ? null : (bookLibrary.getPrice() % 1 == 0 ? new Integer(bookLibrary.getPrice().intValue()).toString() : bookLibrary.getPrice().toString()), SqlLike.DEFAULT);
        bookLibrary.setTitle(null);
        bookLibrary.setPrice(null);
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

    @Override
    @CacheEvict(cacheNames = "bookLibraryOrder", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Float mySettlementById(Long bookLibraryId, Float balance, Long userId) throws CustomizeException, ClientException {

        //先增加一条订单到购物车
        //先找价格
        BookLibrary bookLibrary = bookLibraryMapper.selectById(bookLibraryId);
        Float price = bookLibrary.getPrice();
        BookLibraryOrder bookLibraryOrder = new BookLibraryOrder();
        bookLibraryOrder.setOrderStatus(BusinessSelectItem.ORDER_STATUS_PAID);
        bookLibraryOrder.setUserId(userId);
        bookLibraryOrder.setBookId(bookLibraryId);
        bookLibraryOrder.setPrice(price);
        bookLibraryOrder.setQuantity(1);
        bookLibraryOrder.setUuid(ToolUtil.getUUID());
        bookLibraryOrderMapper.insert(bookLibraryOrder);

        //相减
        balance = balance - price;
        if (balance < 0){
            throw new CustomizeException(CustomizeStatus.BOOK_LIBRARY_INSUFFICIENT_BALANCE, this.getClass());
        }

        //给卖家短信
        //找phone
        User user = userMapper.selectById(bookLibrary.getUserId());
        String phone = user.getPhone();
        //如果该用户有验证手机号码
        if (!ToolUtil.strIsEmpty(phone)){
            shortMessageService.notifyPurchaseSuccess(phone);
        }
        return balance;
    }
}
