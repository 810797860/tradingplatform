package com.secondhand.tradingplatformadminservice.service.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyComment;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : MyComment 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface MyCommentService extends BaseService<MyComment>{

    /**
     * 获取数据总量
     * @param myComment
     * @return
     */
    Long mySelectTotalWithParam(MyComment myComment);

    /**
     * 分页获取MyComment列表数据（实体类）
     * @param myComment
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectSaleListWithParam(MyComment myComment, int current, int size);

    /**
     * 获取数据总量
     * @param myComment
     * @return
     */
    Long mySelectSaleTotalWithParam(MyComment myComment);

    /**
     * 分页获取MyComment列表数据（实体类）
     * @param myComment
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(MyComment myComment, int current, int size);
}