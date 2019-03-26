package com.secondhand.tradingplatformgeccocontroller.bookLibrary;

import com.secondhand.tradingplatformcommon.util.ToolUtil;
import com.secondhand.tradingplatformgeccocontroller.annotation.*;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

@Gecco(matchUrl = "http://product.dangdang.com/{id}.html", pipelines = {"consolePipeline", "bookLibraryDetailPipeline"})
public class DangDetail implements HtmlBean{

    private static final long serialVersionUID = -1L;

    @RequestParameter
    private String id;

    @Text
    @HtmlField(cssPath = "#product_info > div.name_info > h1")
    private String title;

    @Text
    @HtmlField(cssPath = "#dd-price")
    private String price;

    @Image(download = "G:/data/file/tradingplatform/2019-03-25")
    @HtmlField(cssPath = "#largePic")
    private String cover;

    @HtmlField(cssPath = "#product_info > div.messbox_info > div > span > span")
    private String star;

    @Text
    @HtmlField(cssPath = "#comm_num_down")
    private String commentNum;

    @Text
    @HtmlField(cssPath = "#author > a:nth-child(1)")
    private String authorHref;

    @Text
    @HtmlField(cssPath = "#author")
    private String author;

    @Text
    @HtmlField(cssPath = "#product_info > div.messbox_info > span:nth-child(2) > a")
    private String publishingHouse;

    @Text
    @HtmlField(cssPath = "#product_info > div.messbox_info > span:nth-child(3)")
    private String publishedTime;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(1)")
    private String format;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(2)")
    private String paper;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(3)")
    private String enfold;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(4)")
    private String suited;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(5)")
    private String isbn;

    @Text
    @HtmlField(cssPath = "#detail-category-path > span > a:nth-child(3)")
    private String classification;

    @Ajax(url = "http://product.dangdang.com/index.php?r=callback%2Fdetail&productId={id}&templateType=publish&describeMap=0100003066%3A1&shopId=0&categoryPath=01.01.02.00.00.00")
    private DangAjaxDetail details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star.substring(star.indexOf("width:") + 6, star.indexOf("%\">"));
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getAuthorHref() {
        return authorHref;
    }

    public void setAuthorHref(String authorHref) {
        this.authorHref = authorHref;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
            this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        String chPublishedTime = publishedTime.substring(5, publishedTime.length());
        chPublishedTime = chPublishedTime.replace("年", "-");
        chPublishedTime = chPublishedTime.replace("月", "-");
        chPublishedTime += "00 00:00:00";
        this.publishedTime = chPublishedTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format.substring(4, format.length());
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper.substring(4, paper.length());
    }

    public String getEnfold() {
        return enfold;
    }

    public void setEnfold(String enfold) {
        this.enfold = enfold.substring(4, enfold.length());
    }

    public String getSuited() {
        return suited;
    }

    public void setSuited(String suited) {
        this.suited = suited.substring(5, suited.length());
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn.substring(11, isbn.length());
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public DangAjaxDetail getDetails() {
        return details;
    }

    public void setDetails(DangAjaxDetail details) {
        this.details = details;
    }
}
