package com.secondhand.tradingplatformadminentity.entity.business;

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
 *   @description : OperationLog 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-10-28
 */
@TableName("c_business_operation_log")
public class OperationLog extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
            /**
             * 日志类型
             */
            @ApiModelProperty("日志类型")
            @TableField("logtype")
												
			private String logtype;

            /**
             * 日志名称
             */
            @ApiModelProperty("日志名称")
            @TableField("logname")
												
			private String logname;

            /**
             * 用户id
             */
            @ApiModelProperty("用户id")
            @TableField("userid")
												
			private Long userid;

            /**
             * 类名称
             */
            @ApiModelProperty("类名称")
            @TableField("classname")
												
			private String classname;

            /**
             * 方法名称
             */
            @ApiModelProperty("方法名称")
            @TableField("method")
												
			private String method;

            /**
             * 创建时间
             */
            @ApiModelProperty("创建时间")
            @TableField("createtime")
												
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

			private Date createtime;

            /**
             * 是否成功
             */
            @ApiModelProperty("是否成功")
            @TableField("succeed")
												
			private String succeed;

            /**
             * 备注
             */
            @ApiModelProperty("备注")
            @TableField("message")
												
			private String message;

				
				
				
				
				
				
				

        	public String getLogtype() {
                return logtype;
                }

            public void setLogtype(String logtype) {
                this.logtype = logtype;
                }

        	public String getLogname() {
                return logname;
                }

            public void setLogname(String logname) {
                this.logname = logname;
                }

        	public Long getUserid() {
                return userid;
                }

            public void setUserid(Long userid) {
                this.userid = userid;
                }

        	public String getClassname() {
                return classname;
                }

            public void setClassname(String classname) {
                this.classname = classname;
                }

        	public String getMethod() {
                return method;
                }

            public void setMethod(String method) {
                this.method = method;
                }

        	public Date getCreatetime() {
                return createtime;
                }

            public void setCreatetime(Date createtime) {
                this.createtime = createtime;
                }

        	public String getSucceed() {
                return succeed;
                }

            public void setSucceed(String succeed) {
                this.succeed = succeed;
                }

        	public String getMessage() {
                return message;
                }

            public void setMessage(String message) {
                this.message = message;
                }



	@Override
	public String toString() {
		return "OperationLog{" +
			", logtype=" + logtype +
			", logname=" + logname +
			", userid=" + userid +
			", classname=" + classname +
			", method=" + method +
			", createtime=" + createtime +
			", succeed=" + succeed +
			", message=" + message +
			"}";
	}
}
