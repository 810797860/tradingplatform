package com.secondhand.tradingplatformcommon.base.BaseMapper;

import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author 81079
 */

@Repository
public interface BaseEntityMapper<T> extends BaseDao<T> {
}