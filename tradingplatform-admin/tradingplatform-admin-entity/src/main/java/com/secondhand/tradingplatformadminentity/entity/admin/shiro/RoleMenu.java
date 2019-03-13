package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 *   @description : RoleMenu 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-02
 */
@TableName("s_base_role_menu")
public class RoleMenu extends BaseEntity {

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
             * 菜单id
             */
            @ApiModelProperty("菜单id")
            @TableField("menu_id")
												
			private Long menuId;

				
				
				
				
				
				
				

        	public Long getRoleId() {
                return roleId;
                }

            public void setRoleId(Long roleId) {
                this.roleId = roleId;
                }

        	public Long getMenuId() {
                return menuId;
                }

            public void setMenuId(Long menuId) {
                this.menuId = menuId;
                }



	@Override
	public String toString() {
		return "RoleMenu{" +
			", roleId=" + roleId +
			", menuId=" + menuId +
			"}";
	}
}
