package com.secondhand.tradingplatformgenerator.base.BaseEntity;

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
    private Long id;

    /**
     * 全局id
     */
    private String uuid;

    /**
     * 备注
     */
    private String description;

    /**
     * 是否已删除
     */
    private Boolean deleted;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建起始时间，用于query搜索时间传参
     */
    private Date createdAtStart;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 更新起始时间，用于query搜索时间传参
     */
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
