
package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.Form;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 *   @description : FormMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-30
 */
@Repository
public interface FormMapper extends BaseDao<Form> {

    /**
     * 根据id进行假删除
     * @param formId
     * @return
     */
    boolean fakeDeleteById(@Param("formId") Long formId);

    /**
     * 批量假删除
     * @param formIds
     * @return
     */
    boolean fakeBatchDelete(@Param("formIds") List<Long> formIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param formId
     * @return
     */
    @Select("select * from s_base_form where id = #{formId}")
    Map<String, Object> selectMapById(@Param("formId") Long formId);
}