package com.secondhand.tradingplatformgeccocontroller.electricAppliance;

import com.secondhand.tradingplatformgeccocontroller.annotation.*;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

@Gecco(matchUrl = "http://product.dangdang.com/{id}.html", pipelines = {"consolePipeline", "electricApplianceDetailPipeline"})
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

    @Image(download = "G:/data/file/tradingplatform/2019-03-24")
    @HtmlField(cssPath = "#largePic")
    private String cover;

    @Attr(value = "style")
    @HtmlField(cssPath = "#product_info > div.messbox_info > div > span > span")
    private String star;

    @Text
    @HtmlField(cssPath = "#comm_num_down")
    private String commentNum;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(1) > a")
    private String brand;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(2)")
    private String model;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(8) > a")
    private String power;

    @Text
    @HtmlField(cssPath = "#detail_describe > ul > li:nth-child(5) > a")
    private String type;

    @Text
    @HtmlField(cssPath = "#detail-category-path > span > a:nth-child(3)")
    private String classification;

    @Ajax(url = "http://product.dangdang.com/index.php?r=callback%2Fdetail&productId={id}&templateType=mall&describeMap=100003999%3A5001%2C100004092%3A2&shopId=0&categoryPath=58.01.42.01.00.00")
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
        this.star = star.substring(star.indexOf(":") + 1, star.length() - 1);
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model.substring(3, model.length());
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
