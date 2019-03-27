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
 *   @description : SportsSpecialCollection 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_sports_special_collection")
public class SportsSpecialCollection extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 所属运动
             */
            @ApiModelProperty("所属运动")
            @TableField("sports_id")
												
			private Long sportsId;

            /**
             * 收藏人
             */
            @ApiModelProperty("收藏人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

        	public Long getSportsId() {
                return sportsId;
                }

            public void setSportsId(Long sportsId) {
                this.sportsId = sportsId;
                }

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }



	@Override
	public String toString() {
		return "SportsSpecialCollection{" +
			", sportsId=" + sportsId +
			", userId=" + userId +
			"}";
	}
}
