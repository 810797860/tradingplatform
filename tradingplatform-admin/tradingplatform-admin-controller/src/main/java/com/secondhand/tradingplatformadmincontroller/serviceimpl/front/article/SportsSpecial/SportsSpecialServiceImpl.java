package com.secondhand.tradingplatformadmincontroller.serviceimpl.front.article.SportsSpecial;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial.SportsSpecial;
import com.secondhand.tradingplatformadminmapper.mapper.front.article.SportsSpecial.SportsSpecialMapper;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialService;
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
 * @description : SportsSpecial 服务实现类
 * ---------------------------------
 * @since 2019-03-16
 */

@Service
@CacheConfig(cacheNames = "sportsSpecial")
public class SportsSpecialServiceImpl extends BaseServiceImpl<SportsSpecialMapper, SportsSpecial> implements SportsSpecialService {

    @Autowired
    private SportsSpecialMapper sportsSpecialMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long sportsSpecialId) {
        SportsSpecial sportsSpecial = new SportsSpecial();
        sportsSpecial.setId(sportsSpecialId);
        sportsSpecial.setDeleted(true);
        return sportsSpecialMapper.updateById(sportsSpecial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> sportsSpecialIds) {
        sportsSpecialIds.forEach(sportsSpecialId -> {
            myFakeDeleteById(sportsSpecialId);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myExaminationBatchPass(List<Long> sportsSpecialIds) {
        sportsSpecialIds.forEach(sportsSpecialId -> {
            SportsSpecial sportsSpecial = new SportsSpecial();
            sportsSpecial.setId(sportsSpecialId);
            sportsSpecial.setBackCheckStatus(SystemSelectItem.BACK_STATUS_EXAMINATION_PASSED);
            sportsSpecial.setNotPassReason("");
            myUpdateById(sportsSpecial);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long sportsSpecialId) {
        return sportsSpecialMapper.selectMapById(sportsSpecialId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public SportsSpecial mySportsSpecialCreateUpdate(SportsSpecial sportsSpecial) {
        Long sportsSpecialId = sportsSpecial.getId();
        if (sportsSpecialId == null) {
            sportsSpecial.setUuid(ToolUtil.getUUID());
            sportsSpecialMapper.insert(sportsSpecial);
        } else {
            sportsSpecialMapper.updateById(sportsSpecial);
        }
        return sportsSpecial;
    }

    //以下是继承BaseServiceImpl

    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Map<String, Object>> mySelectPageWithParam(Page page, SportsSpecial sportsSpecial) {

        //判空
        sportsSpecial.setDeleted(false);
        Wrapper<SportsSpecial> wrapper = new EntityWrapper<>(sportsSpecial);
        //自定义sql回显
        wrapper.setSqlSelect("c_business_sports_special.id as id, c_business_sports_special.not_pass_reason as not_pass_reason, c_business_sports_special.star as star, c_business_sports_special.description as description, (select concat('{\"id\":\"', sbsi.id, '\",\"pid\":\"', sbsi.pid, '\",\"title\":\"', sbsi.title, '\"}') from s_base_select_item sbsi where (sbsi.id = c_business_sports_special.back_check_status)) AS back_check_status, c_business_sports_special.updated_at as updated_at, ( SELECT concat( '{\"id\":\"', sbu.id, '\",\"user_name\":\"', sbu.user_name, '\",\"phone\":\"', IFNULL(sbu.phone, ''), '\"}' ) FROM s_base_user sbu WHERE (sbu.id = c_business_sports_special.user_id) ) AS user_id, c_business_sports_special.title as title, (select concat('{\"id\":\"', cbfsi.id, '\",\"pid\":\"', cbfsi.pid, '\",\"title\":\"', cbfsi.title, '\"}') from c_business_front_select_item cbfsi where (cbfsi.id = c_business_sports_special.classification)) AS classification, c_business_sports_special.updated_by as updated_by, c_business_sports_special.brand as brand, c_business_sports_special.cover as cover, c_business_sports_special.details as details, c_business_sports_special.pattern as pattern, c_business_sports_special.price as price, c_business_sports_special.created_by as created_by, c_business_sports_special.back_check_time as back_check_time, c_business_sports_special.comment_num as comment_num, c_business_sports_special.deleted as deleted, c_business_sports_special.created_at as created_at")
                //字符串模糊匹配
                .like("title", sportsSpecial.getTitle(), SqlLike.DEFAULT)
                .like("price", sportsSpecial.getPrice() == null ? null : (sportsSpecial.getPrice() % 1 == 0 ? new Integer(sportsSpecial.getPrice().intValue()).toString() : sportsSpecial.getPrice().toString()), SqlLike.DEFAULT);
        sportsSpecial.setTitle(null);
        sportsSpecial.setPrice(null);
        //遍历排序
        List<Sort> sorts = sportsSpecial.getSorts();
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
    public List<SportsSpecial> mySelectListWithMap(Map<String, Object> map) {
        return sportsSpecialMapper.selectByMap(map);
    }

    //以下是继承BaseMapper

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public Map<String, Object> mySelectMap(Wrapper<SportsSpecial> wrapper) {
        return this.selectMap(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<SportsSpecial> mySelectList(Wrapper<SportsSpecial> wrapper) {
        return sportsSpecialMapper.selectList(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(SportsSpecial sportsSpecial) {
        sportsSpecial.setUuid(ToolUtil.getUUID());
        return this.insert(sportsSpecial);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<SportsSpecial> sportsSpecialList) {
        sportsSpecialList.forEach(sportsSpecial -> {
            sportsSpecial.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(sportsSpecialList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(SportsSpecial sportsSpecial) {
        //没有uuid的话要加上去
        if (sportsSpecial.getUuid().equals(null)) {
            sportsSpecial.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(sportsSpecial);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<SportsSpecial> sportsSpecialList) {
        sportsSpecialList.forEach(sportsSpecial -> {
            //没有uuid的话要加上去
            if (sportsSpecial.getUuid().equals(null)) {
                sportsSpecial.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(sportsSpecialList);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<SportsSpecial> mySelectBatchIds(Collection<? extends Serializable> sportsSpecialIds) {
        return sportsSpecialMapper.selectBatchIds(sportsSpecialIds);
    }

    @Override
    @Cacheable(key = "#p0")
    public SportsSpecial mySelectById(Serializable sportsSpecialId) {
        return sportsSpecialMapper.selectById(sportsSpecialId);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public int mySelectCount(Wrapper<SportsSpecial> wrapper) {
        return sportsSpecialMapper.selectCount(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public SportsSpecial mySelectOne(Wrapper<SportsSpecial> wrapper) {
        return this.selectOne(wrapper);
    }

    @Override
    @Cacheable(key = "#p0.paramNameValuePairs")
    public List<Object> mySelectObjs(Wrapper<SportsSpecial> wrapper) {
        return this.selectObjs(wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(SportsSpecial sportsSpecial, Wrapper<SportsSpecial> wrapper) {
        return this.update(sportsSpecial, wrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<SportsSpecial> sportsSpecialList) {
        return this.updateBatchById(sportsSpecialList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(SportsSpecial sportsSpecial) {
        return this.updateById(sportsSpecial);
    }
}
