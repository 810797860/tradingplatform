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
 *   @description : RoleButton 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-04
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
