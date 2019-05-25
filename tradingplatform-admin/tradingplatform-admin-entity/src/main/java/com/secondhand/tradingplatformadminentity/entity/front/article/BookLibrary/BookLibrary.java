package com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary;

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
 * @description : BookLibrary 实体类
 * ---------------------------------
 * @since 2019-03-16
 */
@TableName("c_business_book_library")
public class BookLibrary extends BaseEntity {

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
     * 图书国际标准书号ISBN
     */
    @ApiModelProperty("图书国际标准书号ISBN")
    @TableField("isbn")
    private String isbn;

    /**
     * 图书是否套装
     */
    @ApiModelProperty("图书是否套装")
    @TableField("suited")

    private Boolean suited;

    /**
     * 图书包装
     */
    @ApiModelProperty("图书包装")
    @TableField("enfold")

    private String enfold;

    /**
     * 图书纸张
     */
    @ApiModelProperty("图书纸张")
    @TableField("paper")

    private String paper;

    /**
     * 图书开本
     */
    @ApiModelProperty("图书开本")
    @TableField("format")

    private String format;

    /**
     * 出版时间
     */
    @ApiModelProperty("出版时间")
    @TableField("published_time")

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date publishedTime;

    /**
     * 图书出版社
     */
    @ApiModelProperty("图书出版社")
    @TableField("publishing_house")

    private String publishingHouse;

    /**
     * 图书作者
     */
    @ApiModelProperty("图书作者")
    @TableField("author")

    private String author;

    /**
     * 所属用户
     */
    @ApiModelProperty("所属用户")
    @TableField("user_id")

    private Long userId;

    /**
     * 图书评论数
     */
    @ApiModelProperty("图书评论数")
    @TableField("comment_num")

    private Integer commentNum;

    /**
     * 图书星级
     */
    @ApiModelProperty("图书星级")
    @TableField("star")

    private Float star;

    /**
     * 图书价格
     */
    @ApiModelProperty("图书价格")
    @TableField("price")
    private Float price;

    /**
     * 图书封面
     */
    @ApiModelProperty("图书封面")
    @TableField("cover")

    private String cover;

    /**
     * 图书标题
     */
    @ApiModelProperty("图书标题")
    @TableField("title")
    private String title;

    /**
     * 图书售后保障
     */
    @ApiModelProperty("图书售后保障")
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Boolean getSuited() {
        return suited;
    }

    public void setSuited(Boolean suited) {
        this.suited = suited;
    }

    public String getEnfold() {
        return enfold;
    }

    public void setEnfold(String enfold) {
        this.enfold = enfold;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "BookLibrary{" +
                "backCheckStatus=" + backCheckStatus +
                ", notPassReason='" + notPassReason + '\'' +
                ", backCheckTime=" + backCheckTime +
                ", details='" + details + '\'' +
                ", classification=" + classification +
                ", isbn='" + isbn + '\'' +
                ", suited=" + suited +
                ", enfold='" + enfold + '\'' +
                ", paper='" + paper + '\'' +
                ", format='" + format + '\'' +
                ", publishedTime=" + publishedTime +
                ", publishingHouse='" + publishingHouse + '\'' +
                ", author='" + author + '\'' +
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
