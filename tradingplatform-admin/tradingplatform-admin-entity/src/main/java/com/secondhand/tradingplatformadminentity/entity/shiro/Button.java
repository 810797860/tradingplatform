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
 *   @description : Button 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-04
 */
@TableName("s_base_button")
public class Button extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
            /**
             * 按钮名称
             */
            @ApiModelProperty("按钮名称")
            @TableField("title")
												
			private String title;

            /**
             * 按钮图标
             */
            @ApiModelProperty("按钮图标")
            @TableField("icon")
												
			private String icon;

            /**
             * 按钮类名
             */
            @ApiModelProperty("按钮类名")
            @TableField("class_name")
												
			private String className;

            /**
             * 按钮脚本
             */
            @ApiModelProperty("按钮脚本")
            @TableField("script")
												
			private String script;

            /**
             * 按钮排序号
             */
            @ApiModelProperty("按钮排序号")
            @TableField("num")
												
			private Integer num;

				
				
				
				
				
				
				

        	public String getTitle() {
                return title;
                }

            public void setTitle(String title) {
                this.title = title;
                }

        	public String getIcon() {
                return icon;
                }

            public void setIcon(String icon) {
                this.icon = icon;
                }

        	public String getClassName() {
                return className;
                }

            public void setClassName(String className) {
                this.className = className;
                }

        	public String getScript() {
                return script;
                }

            public void setScript(String script) {
                this.script = script;
                }

        	public Integer getNum() {
                return num;
                }

            public void setNum(Integer num) {
                this.num = num;
                }



	@Override
	public String toString() {
		return "Button{" +
			", title=" + title +
			", icon=" + icon +
			", className=" + className +
			", script=" + script +
			", num=" + num +
			"}";
	}
}
