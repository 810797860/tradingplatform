package com.secondhand.tradingplatformcommon.base.BaseService;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

public interface BaseService<T> extends IService<T> {

    /**
     * 分页获取列表数据（实体类）
     * @param page
     * @param param
     * @return
     */
    Page<T> selectPageWithParam(Page<T> page, T param);

    /**
     * 获取列表数据 （Map）
     * @param map
     * @return
     */
    List<T> selectListWithMap(Map<String, Object> map);

    /**
     * 获取指定数据（Obj）
     * @param obj
     * @return
     */
    T selectOneByObj(Serializable obj);
}
