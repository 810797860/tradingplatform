package com.secondhand.tradingplatformadminentity.entity.front.personal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : 我的收藏 临时表
 * ---------------------------------
 * @since 2019-04-09
 */
@TableName("dual")
public class MyCollection extends BaseEntity{

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 收藏标题
     */
    @ApiModelProperty("收藏标题")
    @TableField("title")
    private String title;

    /**
     * 所属用户
     */
    @ApiModelProperty("所属用户")
    @TableField("user_id")
    private Long userId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MyCollection{" +
                "title='" + title + '\'' +
                ", userId=" + userId +
                '}';
    }
}
