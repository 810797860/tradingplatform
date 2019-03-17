package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : RoleButton 实体类
 * ---------------------------------
 * @since 2018-12-04
 */
@TableName("s_base_role_button")
public class RoleButton extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @TableId("role_id")

    private Long roleId;

    /**
     * 按钮id
     */
    @ApiModelProperty("按钮id")
    @TableField("button_id")

    private Long buttonId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getButtonId() {
        return buttonId;
    }

    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }

    @Override
    public String toString() {
        return "RoleButton{" +
                ", roleId=" + roleId +
                ", buttonId=" + buttonId +
                "}";
    }
}
