package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.DigitalSquare;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.DigitalSquare.DigitalSquare;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.DigitalSquare.DigitalSquareMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.SystemSelectItem;
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
 * @description : DigitalSquare 服务实现类
 * ---------------------------------
 * @since 2019-03-17
 */

@Service
@CacheConfig(cacheNames = "digitalSquare")
public class DigitalSquareServiceImpl extends BaseServiceImpl<DigitalSquareMapper, DigitalSquare> implements DigitalSquareService {

    @Autowired
    private DigitalSquareMapper digitalSquareMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long digitalSquareId) {
        DigitalSquare digitalSquare = new DigitalSquare();
        digitalSquare.setId(digitalSquareId);
        digitalSquare.setDeleted(true);
        return digitalSquareMapper.updateById(digitalSquare);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> digitalSquareIds) {
        digitalSquareIds.forEach(digitalSquareId -> {
            myFakeDeleteById(digitalSquareId);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myExaminationBatchPass(List<Long> digitalSquareIds) {
        digitalSquareIds.forEach(digitalSquareId -> {
            DigitalSquare digitalSquare = new DigitalSquare();
            digitalSquare.setId(digitalSquareId);
            digitalSquare.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            digitalSquare.setNotPassReason("");
            myUpdateById(digitalSquare);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long digitalSquareId) {
        return digitalSquareMapper.selectMapById(digitalSquareId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public DigitalSquare myDigitalSquareCreateUpdate(DigitalSquare digitalSquare) {
        Long digitalSquareId = digitalSquare.getId();
        if (digitalSquareId == null) {
            digitalSquare.setUuid(ToolUtil.getUUID());
            digitalSquareMapper.insert(digitalSquare);
        } else {
            digitalSquareMapper.updateById(digitalSquare);
        }
        return digitalSquare;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, DigitalSquare digitalSquare) {

        //判空
        digitalSquare.setDeleted(false);
        Wrapper<DigitalSquare> wrapper = new EntityWrapper<>(digitalSquare);
        //自动生成sql回显
        wrapper.setSqlSelect("c_business_digital_square.id as id, c_business_digital_square.details as details, c_business_digital_square.comment_num as comment_num, c_business_digital_square.description as description, c_business_digital_square.back_check_time as back_check_time, c_business_digital_square.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_digital_square.user_id) ) AS user_id, c_business_digital_square.title as title, c_business_digital_square.not_pass_reason as not_pass_reason, c_business_digital_square.updated_by as updated_by, c_business_digital_square.brand as brand, c_business_digital_square.cover as cover, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_digital_square.back_check_status)) AS back_check_status, c_business_digital_square.pattern as pattern, c_business_digital_square.price as price, c_business_digital_square.created_by as created_by, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_digital_square.classification)) AS classification, c_business_digital_square.star as star, c_business_digital_square.deleted as deleted, c_business_digital_square.created_at as created_at")
                //字符串模糊匹配
                .like("title", digitalSquare.getTitle(), SqlLike.DEFAULT)
                .like("price", digitalSquare.getPrice() == null ? null : (digitalSquare.getPrice() % 1 == 0 ? new Integer(digitalSquare.getPrice().intValue()).toString() : digitalSquare.getPrice().toString()), SqlLike.DEFAULT);
        digitalSquare.setTitle(null);
        digitalSquare.setPrice(null);
        //遍历排序
        List<Sort> sorts = digitalSquare.getSorts();
        if (sorts == null) {
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach(sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectMapsPage(page, wrapper);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<DigitalSquare> mySelectListWithMap(Map<String, Object> map) {
        return digitalSquareMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<DigitalSquare> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<DigitalSquare> mySelectList(Wrapper<DigitalSquare> wrapper) {
        return digitalSquareMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(DigitalSquare digitalSquare) {
        digitalSquare.setUuid(ToolUtil.getUUID());
        return this.insert(digitalSquare);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<DigitalSquare> digitalSquareList) {
        digitalSquareList.forEach(digitalSquare -> {
            digitalSquare.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(digitalSquareList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(DigitalSquare digitalSquare) {
        //没有uuid的话要加上去
        if (digitalSquare.getUuid().equals(null)) {
            digitalSquare.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(digitalSquare);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<DigitalSquare> digitalSquareList) {
        digitalSquareList.forEach(digitalSquare -> {
            //没有uuid的话要加上去
            if (digitalSquare.getUuid().equals(null)) {
                digitalSquare.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(digitalSquareList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<DigitalSquare> mySelectBatchIds(Collection<? extends Serializable> digitalSquareIds) {
        return digitalSquareMapper.selectBatchIds(digitalSquareIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public DigitalSquare mySelectById(Serializable digitalSquareId) {
        return digitalSquareMapper.selectById(digitalSquareId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<DigitalSquare> wrapper) {
        return digitalSquareMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public DigitalSquare mySelectOne(Wrapper<DigitalSquare> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<DigitalSquare> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(DigitalSquare digitalSquare, Wrapper<DigitalSquare> wrapper) {
        return this.update(digitalSquare, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<DigitalSquare> digitalSquareList) {
        return this.updateBatchById(digitalSquareList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(DigitalSquare digitalSquare) {
        return this.updateById(digitalSquare);
    }
}
