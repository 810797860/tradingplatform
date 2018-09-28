package com.secondhand.tradingplatformgenerator.example.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.secondhand.tradingplatformgenerator.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User实体类
 * @author 81079
 */

@TableName("s_base_user")
public class User extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 2438904321286252600L;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("账号")
    @TableField("account")
    private String account;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("md5密码盐")
    @TableField("salt")
    private String salt;

    @ApiModelProperty("名字")
    @TableField("name")
    private String name;

    @ApiModelProperty("生日")
    @TableField("birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    @ApiModelProperty("性别（1：男 2：女）")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("电子邮件")
    @TableField("email")
    private String email;

    @ApiModelProperty("电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("角色id")
    @TableField("roleId")
    private String roleId;

    @ApiModelProperty("部门id")
    @TableField("deptId")
    private Long deptId;

    @ApiModelProperty("状态(1：启用  2：冻结  3：删除）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField("createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("保留字段")
    @TableField("version")
    private Integer version;

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
    public String toString() {
        return "User{" +
                ", avatar='" + avatar + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roleId='" + roleId + '\'' +
                ", deptId=" + deptId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", version=" + version +
                '}';
    }
}
