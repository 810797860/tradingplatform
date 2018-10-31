
package com.secondhand.tradingplatformadminmapper.mapper.system;

import com.secondhand.tradingplatformadminentity.entity.system.FormField;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 *   @description : FormFieldMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-30
 */
@Repository
public interface FormFieldMapper extends BaseDao<FormField> {

    /**
     * 根据id进行假删除
     * @param formFieldId
     * @return
     */
    boolean fakeDeleteById(@Param("formFieldId") Long formFieldId);

    /**
     * 批量假删除
     * @param formFieldIds
     * @return
     */
    boolean fakeBatchDelete(@Param("formFieldIds") List<Long> formFieldIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param formFieldId
     * @return
     */
    @Select("select * from s_base_form_field where id = #{formFieldId}")
    Map<String, Object> selectMapById(@Param("formFieldId") Long formFieldId);
}