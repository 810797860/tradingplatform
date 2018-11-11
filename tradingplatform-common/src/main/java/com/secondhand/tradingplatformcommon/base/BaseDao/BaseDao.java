package com.secondhand.tradingplatformcommon.base.BaseDao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 81079
 */

@Mapper
public interface BaseDao<T> extends BaseMapper<T> {

}
