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
 *   @description : RentingHouseOrder 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_renting_house_order")
public class RentingHouseOrder extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 订单状态
             */
            @ApiModelProperty("订单状态")
            @TableField("order_status")
												
			private Long orderStatus;

            /**
             * 购买数量
             */
            @ApiModelProperty("购买数量")
            @TableField("quantity")
												
			private Integer quantity;

            /**
             * 购买单价
             */
            @ApiModelProperty("购买单价")
            @TableField("price")
												
			private Float price;

            /**
             * 所属租房
             */
            @ApiModelProperty("所属租房")
            @TableField("renting_id")
												
			private Long rentingId;

            /**
             * 购买人
             */
            @ApiModelProperty("购买人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

        	public Long getOrderStatus() {
                return orderStatus;
                }

            public void setOrderStatus(Long orderStatus) {
                this.orderStatus = orderStatus;
                }

        	public Integer getQuantity() {
                return quantity;
                }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
                }

        	public Float getPrice() {
                return price;
                }

            public void setPrice(Float price) {
                this.price = price;
                }

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
		return "RentingHouseOrder{" +
			", orderStatus=" + orderStatus +
			", quantity=" + quantity +
			", price=" + price +
			", rentingId=" + rentingId +
			", userId=" + userId +
			"}";
	}
}
