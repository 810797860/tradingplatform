package com.secondhand.tradingplatformgenerator.base.BaseServiceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.secondhand.tradingplatformgenerator.base.BaseDao.BaseDao;
import com.secondhand.tradingplatformgenerator.base.BaseService.BaseService;
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
        page.setRecords(this.baseDao.selectPageWithParam(page, param));
        return page;
    }

    @Override
    public Page<T> selectPageWithMap(Page<T> page, Map<String, Object> map) {
        page.setRecords(this.baseDao.selectPageWithMap(page, map));
        return page;
    }

    @Override
    public List<T> selectListWithParam(T param) {
        return this.baseDao.selectListWithParam(param);
    }

    @Override
    public List<T> selectListWithMap(Map<String, Object> map) {
        return this.baseDao.selectListWithMap(map);
    }

    @Override
    public List<Map<String, Object>> selectListMapWithParam(T param) {
        return this.baseDao.selectListMapWithParam(param);
    }

    @Override
    public List<Map<String, Object>> selectListMapWithMap(Map<String, Object> map) {
        return this.baseDao.selectListMapWithMap(map);
    }

    @Override
    public List<Object> selectListObjWithParam(T param) {
        return this.baseDao.selectListObjWithParam(param);
    }

    @Override
    public List<Object> selectListObjWithMap(Map<String, Object> map) {
        return this.baseDao.selectListObjWithMap(map);
    }

    @Override
    public Map<String, Object> selectMapWithParam(T param) {
        return this.baseDao.selectMapWithParam(param);
    }

    @Override
    public Map<String, Object> selectMapWithMap(Map<String, Object> map) {
        return this.baseDao.selectMapWithMap(map);
    }

    @Override
    public Object selectObjWithParam(T param) {
        return this.baseDao.selectObjWithParam(param);
    }

    @Override
    public Object selectObjWithMap(Map<String, Object> map) {
        return this.baseDao.selectObjWithMap(map);
    }

    @Override
    public T selectOneWithParam(T param) {
        return this.baseDao.selectOneWithParam(param);
    }

    @Override
    public T selectOneWithMap(Map<String, Object> map) {
        return this.baseDao.selectOneWithMap(map);
    }

    @Override
    public T selectOneByObj(Serializable obj) {
        return this.baseDao.selectOneByObj(obj);
    }
}
