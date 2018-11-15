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
 *   @description : User 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-13
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
            @TableField("user_name")
												
			private String userName;

            /**
             * 生日
             */
            @ApiModelProperty("生日")
            @TableField("birthday")
												
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

			private Date birthday;

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

        	public String getUserName() {
                return userName;
                }

            public void setUserName(String userName) {
                this.userName = userName;
                }

        	public Date getBirthday() {
                return birthday;
                }

            public void setBirthday(Date birthday) {
                this.birthday = birthday;
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



	@Override
	public String toString() {
		return "User{" +
			", avatar=" + avatar +
			", account=" + account +
			", password=" + password +
			", salt=" + salt +
			", userName=" + userName +
			", birthday=" + birthday +
			", sex=" + sex +
			", email=" + email +
			", phone=" + phone +
			"}";
	}
}
