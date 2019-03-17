package com.secondhand.tradingplatformadminentity.entity.admin.shiro;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : Menu 实体类
 * ---------------------------------
 * @since 2018-11-29
 */
@TableName("s_base_menu")
public class Menu extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;


    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    @TableField("name")

    private String name;

    /**
     * 按钮样式
     */
    @ApiModelProperty("按钮样式")
    @TableField("style")

    private String style;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    @TableField("icon")

    private String icon;

    /**
     * url地址
     */
    @ApiModelProperty("url地址")
    @TableField("url")

    private String url;

    /**
     * 菜单排序号
     */
    @ApiModelProperty("菜单排序号")
    @TableField("num")

    private Integer num;

    /**
     * 菜单父级id
     */
    @ApiModelProperty("菜单父级id")
    @TableField("pid")

    private Long pid;

    /**
     * 打开方式
     */
    @ApiModelProperty("打开方式")
    @TableField("open_mode")

    private Long openMode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Long openMode) {
        this.openMode = openMode;
    }


    @Override
    public String toString() {
        return "Menu{" +
                ", name=" + name +
                ", style=" + style +
                ", icon=" + icon +
                ", url=" + url +
                ", num=" + num +
                ", pid=" + pid +
                ", openMode=" + openMode +
                "}";
    }
}
