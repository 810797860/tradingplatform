package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : Role 实体类
 * ---------------------------------
 * @since 2018-11-13
 */
@TableName("s_base_role")
public class Role extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    @TableField("role_desc")

    private String roleDesc;

    /**
     * 父角色id
     */
    @ApiModelProperty("父角色id")
    @TableField("pid")

    private Long pid;

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Role{" +
                ", roleDesc=" + roleDesc +
                ", pid=" + pid +
                "}";
    }
}
