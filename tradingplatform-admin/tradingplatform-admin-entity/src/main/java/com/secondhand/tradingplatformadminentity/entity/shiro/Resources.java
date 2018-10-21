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
 *   @since 2018-10-20
 */
@TableName("s_base_resources")
public class Resources extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
            /**
             * 资源名称
             */
            @ApiModelProperty("资源名称")
            @TableField("name")
												
			private String name;

            /**
             * 资源url
             */
            @ApiModelProperty("资源url")
            @TableField("resUrl")
												
			private String resUrl;

            /**
             * 资源类型   1:菜单    2：按钮
             */
            @ApiModelProperty("资源类型   1:菜单    2：按钮")
            @TableField("type")
												
			private Integer type;

            /**
             * 父资源
             */
            @ApiModelProperty("父资源")
            @TableField("parentId")
												
			private Integer parentId;

            /**
             * 排序
             */
            @ApiModelProperty("排序")
            @TableField("sort")
												
			private Integer sort;

				
				
				
				
				
				
				

        	public String getName() {
                return name;
                }

            public void setName(String name) {
                this.name = name;
                }

        	public String getResUrl() {
                return resUrl;
                }

            public void setResUrl(String resUrl) {
                this.resUrl = resUrl;
                }

        	public Integer getType() {
                return type;
                }

            public void setType(Integer type) {
                this.type = type;
                }

        	public Integer getParentId() {
                return parentId;
                }

            public void setParentId(Integer parentId) {
                this.parentId = parentId;
                }

        	public Integer getSort() {
                return sort;
                }

            public void setSort(Integer sort) {
                this.sort = sort;
                }



	@Override
	public String toString() {
		return "Resources{" +
			", name=" + name +
			", resUrl=" + resUrl +
			", type=" + type +
			", parentId=" + parentId +
			", sort=" + sort +
			"}";
	}
}
