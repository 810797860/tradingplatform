package com.secondhand.tradingplatformcommon.base.BaseDao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 81079
 */

@Mapper
public interface BaseDao<T> extends BaseMapper<T> {

}
