package com.secondhand.tradingplatformadminentity.entity.shiro;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 *   @description : UserRole 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-22
 */
@TableName("s_base_user_role")
public class UserRole extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



            @TableField("user_id")
												
			private Long userId;

            @TableField("role_id")
												
			private Long roleId;

				
				
				
				
				
				
				

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }

        	public Long getRoleId() {
                return roleId;
                }

            public void setRoleId(Long roleId) {
                this.roleId = roleId;
                }



	@Override
	public String toString() {
		return "UserRole{" +
			", userId=" + userId +
			", roleId=" + roleId +
			"}";
	}
}
