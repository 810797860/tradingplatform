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
 *   @description : RentingHouseEvaluation 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-27
 */
@TableName("c_business_renting_house_evaluation")
public class RentingHouseEvaluation extends BaseEntity {

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
             * 评价内容
             */
            @ApiModelProperty("评价内容")
            @TableField("content")
												
			private String content;

            /**
             * 评价星级
             */
            @ApiModelProperty("评价星级")
            @TableField("star")
												
			private Float star;

            /**
             * 所属租房
             */
            @ApiModelProperty("所属租房")
            @TableField("renting_id")
												
			private Long rentingId;

            /**
             * 评价人
             */
            @ApiModelProperty("评价人")
            @TableField("user_id")
												
			private Long userId;

				
				
				
				
				
				

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

        	public Float getStar() {
                return star;
                }

            public void setStar(Float star) {
                this.star = star;
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
		return "RentingHouseEvaluation{" +
			", notPassReason=" + notPassReason +
			", backCheckStatus=" + backCheckStatus +
			", content=" + content +
			", star=" + star +
			", rentingId=" + rentingId +
			", userId=" + userId +
			"}";
	}
}
