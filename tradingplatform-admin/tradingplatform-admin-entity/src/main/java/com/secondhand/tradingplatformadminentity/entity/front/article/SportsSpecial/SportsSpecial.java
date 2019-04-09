package com.secondhand.tradingplatformadminentity.entity.front.article.SportsSpecial;

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
 * @description : SportsSpecial 实体类
 * ---------------------------------
 * @since 2019-03-16
 */
@TableName("c_business_sports_special")
public class SportsSpecial extends BaseEntity {

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
     * 图书详情
     */
    @ApiModelProperty("图书详情")
    @TableField("details")

    private String details;

    /**
     * 图书分类
     */
    @ApiModelProperty("图书分类")
    @TableField("classification")

    private Long classification;

    /**
     * 运动型号
     */
    @ApiModelProperty("运动型号")
    @TableField("pattern")

    private String pattern;

    /**
     * 运动品牌
     */
    @ApiModelProperty("运动品牌")
    @TableField("brand")

    private String brand;

    /**
     * user_id
     */
    @ApiModelProperty("user_id")
    @TableField("user_id")

    private Long userId;

    /**
     * 运动星级
     */
    @ApiModelProperty("运动星级")
    @TableField("star")

    private Float star;

    /**
     * 运动评论数
     */
    @ApiModelProperty("运动评论数")
    @TableField("comment_num")

    private Integer commentNum;

    /**
     * 运动价格
     */
    @ApiModelProperty("运动价格")
    @TableField("price")

    private Float price;

    /**
     * 运动封面
     */
    @ApiModelProperty("运动封面")
    @TableField("cover")

    private String cover;

    /**
     * 运动标题
     */
    @ApiModelProperty("运动标题")
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

    public Long getClassification() {
        return classification;
    }

    public void setClassification(Long classification) {
        this.classification = classification;
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

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        this.star = star;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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
        return "SportsSpecial{" +
                ", backCheckStatus=" + backCheckStatus +
                ", notPassReason=" + notPassReason +
                ", backCheckTime=" + backCheckTime +
                ", details=" + details +
                ", classification=" + classification +
                ", pattern=" + pattern +
                ", brand=" + brand +
                ", userId=" + userId +
                ", star=" + star +
                ", commentNum=" + commentNum +
                ", price=" + price +
                ", cover=" + cover +
                ", title=" + title +
                "}";
    }
}
