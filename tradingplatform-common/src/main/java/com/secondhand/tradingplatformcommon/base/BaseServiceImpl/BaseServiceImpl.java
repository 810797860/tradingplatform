package com.secondhand.tradingplatformcommon.base.BaseServiceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

public class BaseServiceImpl<M extends BaseDao<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Autowired
    private M baseDao;

    @Override
    public Page<T> selectPageWithParam(Page<T> page, T param) {
        Wrapper<T> wrapper = new EntityWrapper<>(param);
        return this.selectPage(page, wrapper);
    }

    @Override
    public List<T> selectListWithMap(Map<String, Object> map) {
        return this.selectByMap(map);
    }
}
