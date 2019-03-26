package com.secondhand.tradingplatformcommon.pojo;

public class BusinessSelectItem {

    /**
     * 后台审核状态
     */
    public static final Long BACK_CHECK_STATUS = 200001L;

    /**
     * 待审核
     */
    public static final Long BACK_STATUS_PENDING_REVIEW = 200101L;

    /**
     * 审核通过
     */
    public static final Long BACK_STATUS_EXAMINATION_PASSED = 200102L;

    /**
     * 审核不通过
     */
    public static final Long BACK_STATUS_AUDIT_NOT_PASSED = 200103L;
}
