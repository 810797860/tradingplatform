package com.secondhand.tradingplatformadminmapper.mapper.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyCollection;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : MyCollectionMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Repository
public interface MyCollectionMapper extends BaseDao<MyCollection>{

    Long mySelectTotalWithParam(@Param("myCollection") MyCollection myCollection);

    /**
     * 整合个人中心我的收藏
     * @param myCollection
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(@Param("myCollection") MyCollection myCollection,
                                                    @Param("lowerLimit") int lowerLimit, @Param("upperLimit") int upperLimit);
}
