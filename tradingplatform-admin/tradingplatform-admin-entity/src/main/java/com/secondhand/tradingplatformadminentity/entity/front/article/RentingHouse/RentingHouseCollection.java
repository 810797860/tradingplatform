package com.secondhand.tradingplatformadminentity.entity.front.article.RentingHouse;

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
 *   @description : RentingHouseCollection 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_renting_house_collection")
public class RentingHouseCollection extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 所属租房
             */
            @ApiModelProperty("所属租房")
            @TableField("renting_id")
												
			private Long rentingId;

            /**
             * 收藏人
             */
            @ApiModelProperty("收藏人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

        	public Long getRentingId() {
                return rentingId;
                }

            public void setRentingId(Long rentingId) {
                this.rentingId = rentingId;
                }

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }



	@Override
	public String toString() {
		return "RentingHouseCollection{" +
			", rentingId=" + rentingId +
			", userId=" + userId +
			"}";
	}
}
