package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : RoleResources 实体类
 * ---------------------------------
 * @since 2018-11-12
 */
@TableName("s_base_role_resources")
public class RoleResources extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @TableField("role_id")
    private Long roleId;

    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    @TableField("resources_id")
    private Long resourcesId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    @Override
    public String toString() {
        return "RoleResources{" +
                ", roleId=" + roleId +
                ", resourcesId=" + resourcesId +
                "}";
    }
}
