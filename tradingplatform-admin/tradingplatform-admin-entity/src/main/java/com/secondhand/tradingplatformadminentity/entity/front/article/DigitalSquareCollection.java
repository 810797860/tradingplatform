package com.secondhand.tradingplatformadminentity.entity.front.article;

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
 *   @description : DigitalSquareCollection 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_digital_square_collection")
public class DigitalSquareCollection extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 所属数码
             */
            @ApiModelProperty("所属数码")
            @TableField("digital_id")
												
			private Long digitalId;

            /**
             * 收藏人
             */
            @ApiModelProperty("收藏人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

        	public Long getDigitalId() {
                return digitalId;
                }

            public void setDigitalId(Long digitalId) {
                this.digitalId = digitalId;
                }

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }



	@Override
	public String toString() {
		return "DigitalSquareCollection{" +
			", digitalId=" + digitalId +
			", userId=" + userId +
			"}";
	}
}
