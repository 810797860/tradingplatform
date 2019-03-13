package com.secondhand.tradingplatformadminentity.entity.admin.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 *   @description : FormField 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-11-09
 */
@TableName("s_base_form_field")
public class FormField extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;

            /**
             * 所属表单id
             */
            @ApiModelProperty("所属表单id")
            @TableField("form_id")
			private Long formId;

            /**
             * 标题
             */
            @ApiModelProperty("标题")
            @TableField("title")
			private String title;

            /**
             * 字段名--数据库字段名
             */
            @ApiModelProperty("字段名--数据库字段名")
            @TableField("field_name")
			private String fieldName;

            /**
             * 字段类型
             */
            @ApiModelProperty("字段类型")
            @TableField("field_type")
			private Long fieldType;

            /**
             * 展示类型
             */
            @ApiModelProperty("展示类型")
            @TableField("show_type")
			private Long showType;

            /**
             * 是否必填
             */
            @ApiModelProperty("是否必填")
            @TableField("required")
			private Boolean required;

            /**
             * 默认值
             */
            @ApiModelProperty("默认值")
            @TableField("default_value")
			private String defaultValue;

        	public Long getFormId() {
                return formId;
                }

            public void setFormId(Long formId) {
                this.formId = formId;
                }

        	public String getTitle() {
                return title;
                }

            public void setTitle(String title) {
                this.title = title;
                }

        	public String getFieldName() {
                return fieldName;
                }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
                }

        	public Long getFieldType() {
                return fieldType;
                }

            public void setFieldType(Long fieldType) {
                this.fieldType = fieldType;
                }

        	public Long getShowType() {
                return showType;
                }

            public void setShowType(Long showType) {
                this.showType = showType;
                }

        	public Boolean getRequired() {
                return required;
                }

            public void setRequired(Boolean required) {
                this.required = required;
                }

        	public String getDefaultValue() {
                return defaultValue;
                }

            public void setDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
                }



	@Override
	public String toString() {
		return "FormField{" +
			", formId=" + formId +
			", title=" + title +
			", fieldName=" + fieldName +
			", fieldType=" + fieldType +
			", showType=" + showType +
			", required=" + required +
			", defaultValue=" + defaultValue +
			"}";
	}
}
