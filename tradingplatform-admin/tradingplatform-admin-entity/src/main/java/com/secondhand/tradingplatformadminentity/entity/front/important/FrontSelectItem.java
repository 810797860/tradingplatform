package com.secondhand.tradingplatformadminentity.entity.front.important;

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
 * @description : FrontSelectItem 实体类
 * ---------------------------------
 * @since 2019-03-15
 */
@TableName("c_business_front_select_item")
public class FrontSelectItem extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    @TableField("sort")

    private Integer sort;

    /**
     * 选择项值
     */
    @ApiModelProperty("选择项值")
    @TableField("item_value")

    private String itemValue;

    /**
     * 父级
     */
    @ApiModelProperty("父级")
    @TableField("pid")

    private Long pid;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    @TableField("title")

    private String title;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "FrontSelectItem{" +
                ", sort=" + sort +
                ", itemValue=" + itemValue +
                ", pid=" + pid +
                ", title=" + title +
                "}";
    }
}
