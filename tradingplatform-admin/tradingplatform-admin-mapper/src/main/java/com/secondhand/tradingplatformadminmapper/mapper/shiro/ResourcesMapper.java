
package com.secondhand.tradingplatformadminmapper.mapper.shiro;

import com.secondhand.tradingplatformadminentity.entity.shiro.Resources;
import com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 *   @description : ResourcesMapper 接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-20
 */
@Repository
public interface ResourcesMapper extends BaseDao<Resources> {

    /**
     * 根据id进行假删除
     * @param resourcesId
     * @return
     */
    boolean fakeDeleteById(@Param("resourcesId") Long resourcesId);

    /**
     * 批量假删除
     * @param resourcesIds
     * @return
     */
    boolean fakeBatchDelete(@Param("resourcesIds") List<Long> resourcesIds);

    /**
     * 获取Map数据（Obj）
     * 自定化MapSql到MysqlGenerator生成
     * @param resourcesId
     * @return
     */
    @Select("select * from s_base_resources where id = #{resourcesId}")
    Map<String, Object> selectMapById(@Param("resourcesId") Long resourcesId);

    /**
     * 查询所有的Resources(ShiroConfig)
     * @return
     */
    @Select("SELECT id,name,parentId,resUrl,type,sort FROM s_base_resources ORDER BY sort ASC")
    public List<Resources> queryAll();

    /**
     * 加载所有的user_resources(MyShiroRealm)
     * @param map
     * @return
     */
    List<Resources> loadUserResources(Map<String,Object> map);
}