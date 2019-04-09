package com.secondhand.tradingplatformadminentity.entity.front.article.ElectricAppliance;

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
 *   @description : ElectricApplianceOrder 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_electric_appliance_order")
public class ElectricApplianceOrder extends BaseEntity {

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
             * 所属电器
             */
            @ApiModelProperty("所属电器")
            @TableField("electric_id")
												
			private Long electricId;

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

        	public Long getElectricId() {
                return electricId;
                }

            public void setElectricId(Long electricId) {
                this.electricId = electricId;
                }

        	public Long getUserId() {
                return userId;
                }

            public void setUserId(Long userId) {
                this.userId = userId;
                }



	@Override
	public String toString() {
		return "ElectricApplianceOrder{" +
			", orderStatus=" + orderStatus +
			", quantity=" + quantity +
			", price=" + price +
			", electricId=" + electricId +
			", userId=" + userId +
			"}";
	}
}
