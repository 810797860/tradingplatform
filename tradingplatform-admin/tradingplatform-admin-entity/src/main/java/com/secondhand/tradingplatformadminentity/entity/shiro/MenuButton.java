package com.secondhand.tradingplatformadminentity.entity.shiro;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 *   @description : MenuButton 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-06
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
