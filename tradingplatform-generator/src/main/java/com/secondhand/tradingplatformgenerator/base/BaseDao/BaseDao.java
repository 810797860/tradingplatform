package com.secondhand.tradingplatformgenerator.base.BaseDao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

public interface BaseDao<T> extends BaseMapper<T> {

    /**
     * 分页获取列表数据（实体类）
     * @param page
     * @param param
     * @return
     */
    List<T> selectPageWithParam(Page<T> page, T param);

    /**
     * 分页获取列表数据 （Map）
     * @param page
     * @param map
     * @return
     */
    List<T> selectPageWithMap(Page<T> page, Map<String, Object> map);

    /**
     * 获取列表数据（实体类）
     * @param param
     * @return
     */
    List<T> selectListWithParam(T param);

    /**
     * 获取列表数据 （Map）
     * @param map
     * @return
     */
    List<T> selectListWithMap(Map<String, Object> map);

    /**
     * 获取List<Map>数据（实体类）
     * @param param
     * @return
     */
    List<Map<String, Object>> selectListMapWithParam(T param);

    /**
     * 获取List<Map>数据（Map）
     * @param map
     * @return
     */
    List<Map<String, Object>> selectListMapWithMap(Map<String, Object> map);

    /**
     * 获取List<Object>数据（实体类）
     * @param param
     * @return
     */
    List<Object> selectListObjWithParam(T param);

    /**
     * 获取List<Object>数据（Map）
     * @param map
     * @return
     */
    List<Object> selectListObjWithMap(Map<String, Object> map);

    /**
     * 获取Map数据（实体类）
     * @param param
     * @return
     */
    Map<String, Object> selectMapWithParam(T param);

    /**
     * 获取Map数据（Map）
     * @param map
     * @return
     */
    Map<String, Object> selectMapWithMap(Map<String, Object> map);

    /**
     * 获取Object数据（实体类）
     * @param param
     * @return
     */
    Object selectObjWithParam(T param);

    /**
     * 获取Object数据（Map）
     * @param map
     * @return
     */
    Object selectObjWithMap(Map<String, Object> map);

    /**
     * 获取指定数据（实体类）
     * @param param
     * @return
     */
    T selectOneWithParam(T param);

    /**
     * 获取指定数据（Map）
     * @param map
     * @return
     */
    T selectOneWithMap(Map<String, Object> map);

    /**
     * 获取指定数据（Obj）
     * @param obj
     * @return
     */
    T selectOneByObj(Serializable obj);
}
