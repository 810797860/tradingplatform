package com.secondhand.tradingplatformgeccocontroller.sportsSpecial;

import com.secondhand.tradingplatformgeccocontroller.annotation.*;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

//@Gecco(matchUrl = "http://product.dangdang.com/{id}.html{suffix}", pipelines = {"consolePipeline", "sportsSpecialDetailPipeline"})
public class DangDetail implements HtmlBean{

    private static final long serialVersionUID = -1L;

    @RequestParameter
    private String id;

    @RequestParameter
    private String suffix;

    @Text
    @HtmlField(cssPath = "#product_info > div.name_info > h1")
    private String title;

    @Text
    @HtmlField(cssPath = "#dd-price")
    private String price;

    @Image(download = "G:/data/file/tradingplatform/2019-03-26")
    @HtmlField(cssPath = "#largePic")
    private String cover;

    @HtmlField(cssPath = "#product_info > div.messbox_info > div > span")
    private String star;

    @Text
    @HtmlField(cssPath = "#comm_num_down")
    private String commentNum;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(1) > a")
    private String brand;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(2)")
    private String pattern;

    @Text
    @HtmlField(cssPath = "#detail-category-path > span > a:nth-child(3)")
    private String classification;

    @Ajax(url = "http://product.dangdang.com/index.php?r=callback%2Fdetail&productId={id}&templateType=cloth&describeMap=700003885%3A0&shopId=0&categoryPath=58.61.01.01.00.00")
    private DangAjaxDetail details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
        this.price = price.indexOf("-") == -1 ? price : price.substring(0, price.indexOf("-"));
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern.substring(3, pattern.length());
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
        String detail = details.getDetail();
        detail = detail.replaceAll("images/loading.gif\" data-original=\"", "");
        details.setDetail(detail);
        this.details = details;
    }
}
