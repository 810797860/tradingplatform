package com.secondhand.tradingplatformadminentity.entity.admin.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : SelectItem 实体类
 * ---------------------------------
 * @since 2019-02-05
 */
@TableName("s_base_select_item")
public class SelectItem extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;


    /**
     * 标题
     */
    @ApiModelProperty("标题")
    @TableField("title")

    private String title;

    /**
     * 父级
     */
    @ApiModelProperty("父级")
    @TableField("pid")

    private Long pid;

    /**
     * 选择项值
     */
    @ApiModelProperty("选择项值")
    @TableField("item_value")

    private String itemValue;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    @TableField("sort")

    private Integer sort;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    @Override
    public String toString() {
        return "SelectItem{" +
                ", title=" + title +
                ", pid=" + pid +
                ", itemValue=" + itemValue +
                ", sort=" + sort +
                "}";
    }
}
