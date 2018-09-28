package com.secondhand.tradingplatformgenerator.base.BaseMapper;

import com.secondhand.tradingplatformgenerator.base.BaseDao.BaseDao;
import com.secondhand.tradingplatformgenerator.example.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author 81079
 */

@Repository
public interface BaseEntityMapper extends BaseDao<User> {

}