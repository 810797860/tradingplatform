package com.secondhand.tradingplatformadminentity.entity.shiro;

import com.baomidou.mybatisplus.enums.IdType;
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
 *   @description : Role 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-21
 */
@TableName("s_base_role")
public class Role extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
            @TableField("roleDesc")
												
			private String roleDesc;

            /**
             * 序号
             */
            @ApiModelProperty("序号")
            @TableField("num")
												
			private Integer num;

            /**
             * 父角色id
             */
            @ApiModelProperty("父角色id")
            @TableField("pid")
												
			private Long pid;

            /**
             * 角色名称
             */
            @ApiModelProperty("角色名称")
            @TableField("name")
												
			private String name;

            /**
             * 部门名称
             */
            @ApiModelProperty("部门名称")
            @TableField("deptid")
												
			private Long deptid;

            /**
             * 提示
             */
            @ApiModelProperty("提示")
            @TableField("tips")
												
			private String tips;

            /**
             * 保留字段(暂时没用）
             */
            @ApiModelProperty("保留字段(暂时没用）")
            @TableField("version")
												
			private Integer version;

				
				
				
				
				
				
				

        	public String getRoleDesc() {
                return roleDesc;
                }

            public void setRoleDesc(String roleDesc) {
                this.roleDesc = roleDesc;
                }

        	public Integer getNum() {
                return num;
                }

            public void setNum(Integer num) {
                this.num = num;
                }

        	public Long getPid() {
                return pid;
                }

            public void setPid(Long pid) {
                this.pid = pid;
                }

        	public String getName() {
                return name;
                }

            public void setName(String name) {
                this.name = name;
                }

        	public Long getDeptid() {
                return deptid;
                }

            public void setDeptid(Long deptid) {
                this.deptid = deptid;
                }

        	public String getTips() {
                return tips;
                }

            public void setTips(String tips) {
                this.tips = tips;
                }

        	public Integer getVersion() {
                return version;
                }

            public void setVersion(Integer version) {
                this.version = version;
                }



	@Override
	public String toString() {
		return "Role{" +
			", roleDesc=" + roleDesc +
			", num=" + num +
			", pid=" + pid +
			", name=" + name +
			", deptid=" + deptid +
			", tips=" + tips +
			", version=" + version +
			"}";
	}
}
