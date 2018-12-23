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
 *   @description : Annex 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-14
 */
@TableName("c_business_annex")
public class Annex extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;

            /**
             * 文件名
             */
            @ApiModelProperty("文件名")
            @TableField("name")
												
			private String name;

            /**
             * 文件类型
             */
            @ApiModelProperty("文件类型")
            @TableField("type")
												
			private String type;

            /**
             * 文件后缀
             */
            @ApiModelProperty("文件后缀")
            @TableField("extension")
												
			private String extension;

            /**
             * 文件大小
             */
            @ApiModelProperty("文件大小")
            @TableField("size")
												
			private Float size;

            /**
             * 文件地址
             */
            @ApiModelProperty("文件地址")
            @TableField("path")
												
			private String path;

            /**
             * 文件md5
             */
            @ApiModelProperty("文件md5")
            @TableField("md5")
												
			private String md5;

            /**
             * 消息头
             */
            @ApiModelProperty("消息头")
            @TableField("content_type")
												
			private String contentType;

        	public String getName() {
                return name;
                }

            public void setName(String name) {
                this.name = name;
                }

        	public String getType() {
                return type;
                }

            public void setType(String type) {
                this.type = type;
                }

        	public String getExtension() {
                return extension;
                }

            public void setExtension(String extension) {
                this.extension = extension;
                }

        	public Float getSize() {
                return size;
                }

            public void setSize(Float size) {
                this.size = size;
                }

        	public String getPath() {
                return path;
                }

            public void setPath(String path) {
                this.path = path;
                }

        	public String getMd5() {
                return md5;
                }

            public void setMd5(String md5) {
                this.md5 = md5;
                }

        	public String getContentType() {
                return contentType;
                }

            public void setContentType(String contentType) {
                this.contentType = contentType;
                }

	@Override
	public String toString() {
		return "Annex{" +
			", name=" + name +
			", type=" + type +
			", extension=" + extension +
			", size=" + size +
			", path=" + path +
			", md5=" + md5 +
			", contentType=" + contentType +
			"}";
	}
}
