package com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse;

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
 * @description : RentingHouse 实体类
 * ---------------------------------
 * @since 2019-03-17
 */
@TableName("c_business_renting_house")
public class RentingHouse extends BaseEntity {

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
     * 租房详情
     */
    @ApiModelProperty("租房详情")
    @TableField("details")

    private String details;

    /**
     * 租房分类
     */
    @ApiModelProperty("租房分类")
    @TableField("classification")

    private Long classification;

    /**
     * 租房配套
     */
    @ApiModelProperty("租房配套")
    @TableField("matching")

    private Long matching;

    /**
     * 租房小区
     */
    @ApiModelProperty("租房小区")
    @TableField("community")

    private String community;

    /**
     * 租房类型
     */
    @ApiModelProperty("租房类型")
    @TableField("genre")

    private String genre;

    /**
     * 租房装修
     */
    @ApiModelProperty("租房装修")
    @TableField("decoration")

    private String decoration;

    /**
     * 租房楼层
     */
    @ApiModelProperty("租房楼层")
    @TableField("floor_layer")

    private String floorLayer;

    /**
     * 租房朝向
     */
    @ApiModelProperty("租房朝向")
    @TableField("oriented")

    private String oriented;

    /**
     * 租房面积
     */
    @ApiModelProperty("租房面积")
    @TableField("area")

    private Integer area;

    /**
     * 租房户型
     */
    @ApiModelProperty("租房户型")
    @TableField("house_type")

    private String houseType;

    /**
     * 所属用户
     */
    @ApiModelProperty("所属用户")
    @TableField("user_id")

    private Long userId;

    /**
     * 租房评论数
     */
    @ApiModelProperty("租房评论数")
    @TableField("comment_num")

    private Integer commentNum;

    /**
     * 租房星级
     */
    @ApiModelProperty("租房星级")
    @TableField("star")

    private Float star;

    /**
     * 租房价格
     */
    @ApiModelProperty("租房价格")
    @TableField("price")

    private Float price;

    /**
     * 租房封面
     */
    @ApiModelProperty("租房封面")
    @TableField("cover")

    private String cover;

    /**
     * 租房标题
     */
    @ApiModelProperty("租房标题")
    @TableField("title")
    private String title;

    /**
     * 租房售后保障
     */
    @ApiModelProperty("租房售后保障")
    @TableField("warranty")
    private String warranty;

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

    public Long getMatching() {
        return matching;
    }

    public void setMatching(Long matching) {
        this.matching = matching;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getFloorLayer() {
        return floorLayer;
    }

    public void setFloorLayer(String floorLayer) {
        this.floorLayer = floorLayer;
    }

    public String getOriented() {
        return oriented;
    }

    public void setOriented(String oriented) {
        this.oriented = oriented;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
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

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    @Override
    public String toString() {
        return "RentingHouse{" +
                "backCheckStatus=" + backCheckStatus +
                ", notPassReason='" + notPassReason + '\'' +
                ", backCheckTime=" + backCheckTime +
                ", details='" + details + '\'' +
                ", classification=" + classification +
                ", matching=" + matching +
                ", community='" + community + '\'' +
                ", genre='" + genre + '\'' +
                ", decoration='" + decoration + '\'' +
                ", floorLayer='" + floorLayer + '\'' +
                ", oriented='" + oriented + '\'' +
                ", area=" + area +
                ", houseType='" + houseType + '\'' +
                ", userId=" + userId +
                ", commentNum=" + commentNum +
                ", star=" + star +
                ", price=" + price +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", warranty='" + warranty + '\'' +
                '}';
    }
}
