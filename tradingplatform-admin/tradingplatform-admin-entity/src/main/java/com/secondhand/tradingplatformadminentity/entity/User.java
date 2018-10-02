package com.secondhand.tradingplatformadminentity.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 *   @description : User 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-02
 */
@TableName("s_base_user")
public class User extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
	@ApiModelProperty("主键id")
	@TableId(value="id", type= IdType.AUTO)
	private Long id;

    /**
     * 头像
     */
	@ApiModelProperty("头像")
	@TableField("avatar")
	private String avatar;

    /**
     * 账号
     */
	@ApiModelProperty("账号")
	@TableField("account")
	private String account;

    /**
     * 密码
     */
	@ApiModelProperty("密码")
	@TableField("password")
	private String password;

    /**
     * md5密码盐
     */
	@ApiModelProperty("md5密码盐")
	@TableField("salt")
	private String salt;

    /**
     * 名字
     */
	@ApiModelProperty("名字")
	@TableField("name")
	private String name;

    /**
     * 生日
     */
	@ApiModelProperty("生日")
	@TableField("birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
	@ApiModelProperty("性别（1：男 2：女）")
	@TableField("sex")
	private Integer sex;

    /**
     * 电子邮件
     */
	@ApiModelProperty("电子邮件")
	@TableField("email")
	private String email;

    /**
     * 电话
     */
	@ApiModelProperty("电话")
	@TableField("phone")
	private String phone;

    /**
     * 角色id
     */
	@ApiModelProperty("角色id")
	@TableField("roleId")
	private String roleId;

    /**
     * 部门id
     */
	@ApiModelProperty("部门id")
	@TableField("deptId")
	private Long deptId;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
	@ApiModelProperty("状态(1：启用  2：冻结  3：删除）")
	@TableField("status")
	private Integer status;

    /**
     * 创建时间
     */
	@ApiModelProperty("创建时间")
	@TableField("createTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

    /**
     * 保留字段
     */
	@ApiModelProperty("保留字段")
	@TableField("version")
	private Integer version;

    /**
     * 全局id
     */
	@ApiModelProperty("全局id")
	@TableField("uuid")
	private String uuid;

    /**
     * 备注
     */
	@ApiModelProperty("备注")
	@TableField("description")
	private String description;

    /**
     * 是否已删除
     */
	@ApiModelProperty("是否已删除")
	@TableField("deleted")
	private Boolean deleted;

    /**
     * 创建人
     */
	@ApiModelProperty("创建人")
	private Long createdBy;

    /**
     * 创建时间
     */
	@ApiModelProperty("创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdAt;

    /**
     * 更新人
     */
	@ApiModelProperty("更新人")
	private Long updatedBy;

    /**
     * 新时间
     */
	@ApiModelProperty("新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatedAt;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public Long getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public Long getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	@Override
	public String toString() {
		return "User{" +
			", id=" + id +
			", avatar=" + avatar +
			", account=" + account +
			", password=" + password +
			", salt=" + salt +
			", name=" + name +
			", birthday=" + birthday +
			", sex=" + sex +
			", email=" + email +
			", phone=" + phone +
			", roleId=" + roleId +
			", deptId=" + deptId +
			", status=" + status +
			", createTime=" + createTime +
			", version=" + version +
			", uuid=" + uuid +
			", description=" + description +
			", deleted=" + deleted +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			"}";
	}
}
