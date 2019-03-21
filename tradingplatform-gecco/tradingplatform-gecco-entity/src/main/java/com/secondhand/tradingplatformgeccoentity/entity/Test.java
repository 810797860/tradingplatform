package com.secondhand.tradingplatformgeccoentity.entity;

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
 *   @description : Test 实体类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2019-03-21
 */
@TableName("c_business_test")
public class Test extends BaseEntity {

	/**
	 * 序列化标志
	 */
    private static final long serialVersionUID = 1L;

            /**
             * 测试字段233
             */
            @ApiModelProperty("测试字段233")
            @TableField("test12")
												
			private String test12;


        	public String getTest12() {
                return test12;
                }

            public void setTest12(String test12) {
                this.test12 = test12;
                }


	@Override
	public String toString() {
		return "Test{" +
			", test12=" + test12 +
			"}";
	}
}
