package com.secondhand.tradingplatformadmincontroller.serviceimpl.admin.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.business.SocketMessage;
import com.secondhand.tradingplatformadminmapper.mapper.admin.business.SocketMessageMapper;
import com.secondhand.tradingplatformadminservice.service.admin.business.SocketMessageService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : SocketMessage 服务实现类
 * ---------------------------------
 * @since 2018-12-25
 */

@Service
@CacheConfig(cacheNames = "socketMessage")
public class SocketMessageServiceImpl extends BaseServiceImpl<SocketMessageMapper, SocketMessage> implements SocketMessageService {

    @Autowired
    private SocketMessageMapper socketMessageMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long socketMessageId) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setId(socketMessageId);
        socketMessage.setDeleted(true);
        return socketMessageMapper.updateById(socketMessage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> socketMessageIds) {
        socketMessageIds.forEach(socketMessageId -> {
            myFakeDeleteById(socketMessageId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long socketMessageId) {
        return socketMessageMapper.selectMapById(socketMessageId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SocketMessage mySocketMessageCreateUpdate(SocketMessage socketMessage) {
        Long socketMessageId = socketMessage.getId();
        if (socketMessageId == null) {
            socketMessage.setUuid(ToolUtil.getUUID());
            socketMessageMapper.insert(socketMessage);
        } else {
            socketMessageMapper.updateById(socketMessage);
        }
        return socketMessage;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<SocketMessage> mySelectPageWithParam(Page<SocketMessage> page, SocketMessage socketMessage) {

        //判空
        socketMessage.setDeleted(false);
        Wrapper<SocketMessage> wrapper = new EntityWrapper<>(socketMessage);
        //遍历排序
        List<Sort> sorts = socketMessage.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<SocketMessage> mySelectListWithMap(Map<String, Object> map) {
        return socketMessageMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<SocketMessage> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<SocketMessage> mySelectList(Wrapper<SocketMessage> wrapper) {
        return socketMessageMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SocketMessage socketMessage) {
        socketMessage.setUuid(ToolUtil.getUUID());
        return this.insert(socketMessage);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SocketMessage> socketMessageList) {
        socketMessageList.forEach(socketMessage -> {
            socketMessage.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(socketMessageList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SocketMessage socketMessage) {
        //没有uuid的话要加上去
        if (socketMessage.getUuid().equals(null)) {
            socketMessage.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(socketMessage);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SocketMessage> socketMessageList) {
        socketMessageList.forEach(socketMessage -> {
            //没有uuid的话要加上去
            if (socketMessage.getUuid().equals(null)) {
                socketMessage.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(socketMessageList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<SocketMessage> mySelectBatchIds(Collection<? extends Serializable> socketMessageIds) {
        return socketMessageMapper.selectBatchIds(socketMessageIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public SocketMessage mySelectById(Serializable socketMessageId) {
        return socketMessageMapper.selectById(socketMessageId);
    }

    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<SocketMessage> wrapper) {
        return socketMessageMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public SocketMessage mySelectOne(Wrapper<SocketMessage> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SocketMessage socketMessage, Wrapper<SocketMessage> wrapper) {
        return this.update(socketMessage, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SocketMessage> socketMessageList) {
        return this.updateBatchById(socketMessageList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SocketMessage socketMessage) {
        return this.updateById(socketMessage);
    }
}
