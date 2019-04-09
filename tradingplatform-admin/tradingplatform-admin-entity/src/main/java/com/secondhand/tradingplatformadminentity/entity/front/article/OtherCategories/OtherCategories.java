package com.secondhand.tradingplatformadminentity.entity.front.article.OtherCategories;

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
 * @author zhangjk
 * @description : OtherCategories 实体类
 * ---------------------------------
 * @since 2019-03-17
 */
@TableName("c_business_other_categories")
public class OtherCategories extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 后台审核状态
     */
    @ApiModelProperty("后台审核状态")
    @TableField("back_check_status")

    private Long backCheckStatus;

    /**
     * 不通过理由
     */
    @ApiModelProperty("不通过理由")
    @TableField("not_pass_reason")

    private String notPassReason;

    /**
     * 后台审核时间
     */
    @ApiModelProperty("后台审核时间")
    @TableField("back_check_time")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date backCheckTime;

    /**
     * 其他详情
     */
    @ApiModelProperty("其他详情")
    @TableField("details")

    private String details;

    /**
     * 其他型号
     */
    @ApiModelProperty("其他型号")
    @TableField("pattern")

    private String pattern;

    /**
     * 其他品牌
     */
    @ApiModelProperty("其他品牌")
    @TableField("brand")

    private String brand;

    /**
     * 所属用户
     */
    @ApiModelProperty("所属用户")
    @TableField("user_id")

    private Long userId;

    /**
     * 其他评论数
     */
    @ApiModelProperty("其他评论数")
    @TableField("comment_num")

    private Integer commentNum;

    /**
     * 其他星级
     */
    @ApiModelProperty("其他星级")
    @TableField("star")

    private Float star;

    /**
     * 其他价格
     */
    @ApiModelProperty("其他价格")
    @TableField("price")

    private Float price;

    /**
     * 其他封面
     */
    @ApiModelProperty("其他封面")
    @TableField("cover")

    private String cover;

    /**
     * 其他标题
     */
    @ApiModelProperty("其他标题")
    @TableField("title")

    private String title;

    public Long getBackCheckStatus() {
        return backCheckStatus;
    }

    public void setBackCheckStatus(Long backCheckStatus) {
        this.backCheckStatus = backCheckStatus;
    }

    public String getNotPassReason() {
        return notPassReason;
    }

    public void setNotPassReason(String notPassReason) {
        this.notPassReason = notPassReason;
    }

    public Date getBackCheckTime() {
        return backCheckTime;
    }

    public void setBackCheckTime(Date backCheckTime) {
        this.backCheckTime = backCheckTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        this.star = star;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "OtherCategories{" +
                ", backCheckStatus=" + backCheckStatus +
                ", notPassReason=" + notPassReason +
                ", backCheckTime=" + backCheckTime +
                ", details=" + details +
                ", pattern=" + pattern +
                ", brand=" + brand +
                ", userId=" + userId +
                ", commentNum=" + commentNum +
                ", star=" + star +
                ", price=" + price +
                ", cover=" + cover +
                ", title=" + title +
                "}";
    }
}
