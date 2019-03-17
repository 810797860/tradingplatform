package com.secondhand.tradingplatformcommon.base.BaseEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 81079
 */

public class BaseEntity extends BaseDTO {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 3799814342008101004L;

    /**
     * 主键id(自动自增非空)
     */

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 全局id
     */
    @ApiModelProperty("全局id")
    @TableField(value = "uuid")
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
     * 创建起始时间，用于query搜索时间传参
     */
    @TableField(exist = false)
    @ApiModelProperty("创建开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAtStart;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    /**
     * 更新起始时间，用于query搜索时间传参
     */
    @TableField(exist = false)
    @ApiModelProperty("更新开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAtStart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAtStart() {
        return createdAtStart;
    }

    public void setCreatedAtStart(Date createdAtStart) {
        this.createdAtStart = createdAtStart;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAtStart() {
        return updatedAtStart;
    }

    public void setUpdatedAtStart(Date updatedAtStart) {
        this.updatedAtStart = updatedAtStart;
    }
}
