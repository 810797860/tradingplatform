package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : MenuButton 实体类
 * ---------------------------------
 * @since 2018-12-06
 */
@TableName("s_base_menu_button")
public class MenuButton extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    @TableId("menu_id")

    private Long menuId;

    /**
     * 按钮id
     */
    @ApiModelProperty("按钮id")
    @TableField("button_id")

    private Long buttonId;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getButtonId() {
        return buttonId;
    }

    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }


    @Override
    public String toString() {
        return "MenuButton{" +
                ", menuId=" + menuId +
                ", buttonId=" + buttonId +
                "}";
    }
}
