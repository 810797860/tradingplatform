package com.secondhand.tradingplatformadminentity.entity.admin.business;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangjk
 * @description : SocketMessage 实体类
 * ---------------------------------
 * @since 2018-12-25
 */
@TableName("c_business_socket_message")
public class SocketMessage extends BaseEntity {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 消息标题
     */
    @ApiModelProperty("消息标题")
    @TableField("title")

    private String title;

    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    @TableField("content")

    private String content;

    /**
     * 发送消息者
     */
    @ApiModelProperty("发送消息者")
    @TableField("sender")

    private Long sender;

    /**
     * 接受消息者
     */
    @ApiModelProperty("接受消息者")
    @TableField("recipient")

    private Long recipient;

    /**
     * 消息详情
     */
    @ApiModelProperty("消息详情")
    @TableField("details")

    private String details;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    @Override
    public String toString() {
        return "SocketMessage{" +
                ", title=" + title +
                ", content=" + content +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", details=" + details +
                "}";
    }
}
