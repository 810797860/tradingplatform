package com.secondhand.tradingplatformadminmapper.mapper.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyComment;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *   @description : MyCommentMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-04-09
 */
@Repository
public interface MyCommentMapper extends BaseDao<MyComment>{

    /**
     * 计算总数
     * @param myComment
     * @return
     */
    Long mySelectTotalWithParam(@Param("myComment") MyComment myComment);

    /**
     * 整合个人中心MyComment
     * @param myComment
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(@Param("myComment") MyComment myComment,
                                                    @Param("lowerLimit") int lowerLimit, @Param("upperLimit") int upperLimit);

    /**
     * 计算总数
     * @param myComment
     * @return
     */
    Long mySelectSaleTotalWithParam(@Param("myComment") MyComment myComment);

    /**
     * 整合个人中心MyComment
     * @param myComment
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    List<Map<String, Object>> mySelectSaleListWithParam(@Param("myComment") MyComment myComment,
                                                        @Param("lowerLimit") int lowerLimit, @Param("upperLimit") int upperLimit);
}
