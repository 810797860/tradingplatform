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
 *   @description : BookLibraryCollection 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_book_library_collection")
public class BookLibraryCollection extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 所属图书
             */
            @ApiModelProperty("所属图书")
            @TableField("book_id")
												
			private Long bookId;

            /**
             * 收藏人
             */
            @ApiModelProperty("收藏人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

        	public Long getBookId() {
                return bookId;
                }

            public void setBookId(Long bookId) {
                this.bookId = bookId;
                }

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }



	@Override
	public String toString() {
		return "BookLibraryCollection{" +
			", bookId=" + bookId +
			", userId=" + userId +
			"}";
	}
}
