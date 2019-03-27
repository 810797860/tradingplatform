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
 *   @description : ElectricApplianceCollection 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_electric_appliance_collection")
public class ElectricApplianceCollection extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;



				
				
            /**
             * 所属电器
             */
            @ApiModelProperty("所属电器")
            @TableField("electric_id")
												
			private Long electricId;

            /**
             * 收藏人
             */
            @ApiModelProperty("收藏人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

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
		return "ElectricApplianceCollection{" +
			", electricId=" + electricId +
			", userId=" + userId +
			"}";
	}
}
