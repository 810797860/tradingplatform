package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : User 实体类
 * ---------------------------------
 * @since 2018-11-13
 */
@TableName("s_base_user")
public class User extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @TableField("avatar")

    private Long avatar;

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
     * 名字
     */
    @ApiModelProperty("名字")
    @TableField("user_name")

    private String userName;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    @TableField("sex")

    private Long sex;

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
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    @TableField("type")
    private Long type;


    /**
     * 学校住址
     */
    @ApiModelProperty("学校住址")
    @TableField("school_address")
    private Long schoolAddress;

    /**
     * 余额
     */
    @ApiModelProperty("余额")
    @TableField("balance")
    private Float balance;


    public Long getAvatar() {
        return avatar;
    }

    public void setAvatar(Long avatar) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(Long schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "avatar=" + avatar +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", schoolAddress=" + schoolAddress +
                ", balance=" + balance +
                '}';
    }
}
