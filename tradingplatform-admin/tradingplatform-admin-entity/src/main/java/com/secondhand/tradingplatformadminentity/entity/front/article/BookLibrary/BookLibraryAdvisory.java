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
 *   @description : BookLibraryAdvisory 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_book_library_advisory")
public class BookLibraryAdvisory extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 不通过理由
             */
            @ApiModelProperty("不通过理由")
            @TableField("not_pass_reason")
												
			private String notPassReason;

            /**
             * 后台审核状态
             */
            @ApiModelProperty("后台审核状态")
            @TableField("back_check_status")
												
			private Long backCheckStatus;

            /**
             * 咨询内容
             */
            @ApiModelProperty("咨询内容")
            @TableField("content")
												
			private String content;

            /**
             * 所属图书
             */
            @ApiModelProperty("所属图书")
            @TableField("book_id")
												
			private Long bookId;

            /**
             * 回复者
             */
            @ApiModelProperty("回复者")
            @TableField("reply_id")
												
			private Long replyId;

            /**
             * 咨询人
             */
            @ApiModelProperty("咨询人")
            @TableField("user_id")
												
			private Long userId;

            /**
             * pid
             */
            @ApiModelProperty("pid")
            @TableField("pid")
												
			private Long pid;

				
				
				
				
				
				

        	public String getNotPassReason() {
                return notPassReason;
                }

            public void setNotPassReason(String notPassReason) {
                this.notPassReason = notPassReason;
                }

        	public Long getBackCheckStatus() {
                return backCheckStatus;
                }

            public void setBackCheckStatus(Long backCheckStatus) {
                this.backCheckStatus = backCheckStatus;
                }

        	public String getContent() {
                return content;
                }

            public void setContent(String content) {
                this.content = content;
                }

        	public Long getBookId() {
                return bookId;
                }

            public void setBookId(Long bookId) {
                this.bookId = bookId;
                }

        	public Long getReplyId() {
                return replyId;
                }

            public void setReplyId(Long replyId) {
                this.replyId = replyId;
                }

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }

        	public Long getPid() {
                return pid;
                }

            public void setPid(Long pid) {
                this.pid = pid;
                }



	@Override
	public String toString() {
		return "BookLibraryAdvisory{" +
			", notPassReason=" + notPassReason +
			", backCheckStatus=" + backCheckStatus +
			", content=" + content +
			", bookId=" + bookId +
			", replyId=" + replyId +
			", userId=" + userId +
			", pid=" + pid +
			"}";
	}
}
