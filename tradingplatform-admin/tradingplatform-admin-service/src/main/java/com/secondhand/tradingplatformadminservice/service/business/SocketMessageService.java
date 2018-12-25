package com.secondhand.tradingplatformadminservice.service.business;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.business.SocketMessage;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : SocketMessage 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-25
 */
public interface SocketMessageService extends BaseService<SocketMessage> {

        /**
         * 根据id进行假删除
         * @param socketMessageId
         * @return
         */
        Integer myFakeDeleteById(Long socketMessageId);

        /**
         * 根据ids进行批量假删除
         * @param socketMessageIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> socketMessageIds);

        /**
         * 获取Map数据（Obj）
         * @param socketMessageId
         * @return
         */
        Map<String, Object> mySelectMapById(Long socketMessageId);

        /**
         * 新增或修改socketMessage
         * @param socketMessage
         * @return
         */
        SocketMessage mySocketMessageCreateUpdate(SocketMessage socketMessage);

        /**
         * 分页获取SocketMessage列表数据（实体类）
         * @param page
         * @param socketMessage
         * @return
         */
        Page<SocketMessage> mySelectPageWithParam(Page<SocketMessage> page, SocketMessage socketMessage);

        /**
         * 获取SocketMessage列表数据（Map）
         * @param map
         * @return
         */
        List<SocketMessage> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<SocketMessage> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<SocketMessage> mySelectList(Wrapper<SocketMessage> wrapper);

        /**
         * 插入SocketMessage
         * @param socketMessage
         * @return
         */
        boolean myInsert(SocketMessage socketMessage);

        /**
         * 批量插入List<SocketMessage>
         * @param socketMessageList
         * @return
         */
        boolean myInsertBatch(List<SocketMessage> socketMessageList);

        /**
         * 插入或更新socketMessage
         * @param socketMessage
         * @return
         */
        boolean myInsertOrUpdate(SocketMessage socketMessage);

        /**
         * 批量插入或更新List<SocketMessage>
         * @param socketMessageList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<SocketMessage> socketMessageList);

        /**
         * 根据socketMessageIds获取List
         * @param socketMessageIds
         * @return
         */
        List<SocketMessage> mySelectBatchIds(Collection<? extends Serializable> socketMessageIds);

        /**
         * 根据socketMessageId获取SocketMessage
         * @param socketMessageId
         * @return
         */
        SocketMessage mySelectById(Serializable socketMessageId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<SocketMessage> wrapper);

        /**
         * 根据wrapper获取SocketMessage
         * @param wrapper
         * @return
         */
        SocketMessage mySelectOne(Wrapper<SocketMessage> wrapper);

        /**
         * 根据socketMessage和wrapper更新socketMessage
         * @param socketMessage
         * @param wrapper
         * @return
         */
        boolean myUpdate(SocketMessage socketMessage, Wrapper<SocketMessage> wrapper);

        /**
         * 根据socketMessageList更新socketMessage
         * @param socketMessageList
         * @return
         */
        boolean myUpdateBatchById(List<SocketMessage> socketMessageList);

        /**
         * 根据socketMessageId修改socketMessage
         * @param socketMessage
         * @return
         */
        boolean myUpdateById(SocketMessage socketMessage);

}
