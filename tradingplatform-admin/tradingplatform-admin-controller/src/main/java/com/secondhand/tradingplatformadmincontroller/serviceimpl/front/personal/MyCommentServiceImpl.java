package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyComment;
import com.secondhand.tradingplatformadminmapper.mapper.front.personal.MyCommentMapper;
import com.secondhand.tradingplatformadminservice.service.front.personal.MyCommentService;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *   @description : MyComment 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Service
@CacheConfig(cacheNames = "myComment")
public class MyCommentServiceImpl extends BaseServiceImpl<MyCommentMapper, MyComment> implements MyCommentService{

    @Autowired
    private MyCommentMapper myCommentMapper;

    @Override
    @Cacheable(key = "'mySelectTotalWithParam:' + #p0")
    public Long mySelectTotalWithParam(MyComment myComment) {
        return myCommentMapper.mySelectTotalWithParam(myComment);
    }

    @Override
    @Cacheable(key = "'mySelectListWithParam:' + #p0 + ',' + #p1 + ',' + #p2")
    public List<Map<String, Object>> mySelectListWithParam(MyComment myComment, int current, int size) {
        //计算分页
        int lowerLimit = (current - 1) * size;
        int upperLimit = lowerLimit + size;
        List<Map<String, Object>> resList = myCommentMapper.mySelectListWithParam(myComment, lowerLimit, upperLimit);
        return resList;
    }

    @Override
    @Cacheable(key = "'mySelectSaleTotalWithParam' + #p0")
    public Long mySelectSaleTotalWithParam(MyComment myComment) {
        return myCommentMapper.mySelectSaleTotalWithParam(myComment);
    }

    @Override
    @Cacheable(key = "'mySelectSaleListWithParam:' + #p0 + ',' + #p1 + ',' + #p2")
    public List<Map<String, Object>> mySelectSaleListWithParam(MyComment myComment, int current, int size) {
        //计算分页
        int lowerLimit = (current - 1) * size;
        int upperLimit = lowerLimit + size;
        List<Map<String, Object>> resList = myCommentMapper.mySelectSaleListWithParam(myComment, lowerLimit, upperLimit);
        return resList;
    }
}
