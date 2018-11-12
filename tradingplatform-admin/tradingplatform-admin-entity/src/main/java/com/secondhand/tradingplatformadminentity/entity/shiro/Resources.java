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
 *   @description : Resources 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-12
 */
@TableName("s_base_resources")
public class Resources extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
            /**
             * 资源标题
             */
            @ApiModelProperty("资源标题")
            @TableField("title")
												
			private String title;

            /**
             * 资源url
             */
            @ApiModelProperty("资源url")
            @TableField("url")
												
			private String url;

				
				
				
				
				
				
				

        	public String getTitle() {
                return title;
                }

            public void setTitle(String title) {
                this.title = title;
                }

        	public String getUrl() {
                return url;
                }

            public void setUrl(String url) {
                this.url = url;
                }



	@Override
	public String toString() {
		return "Resources{" +
			", title=" + title +
			", url=" + url +
			"}";
	}
}
