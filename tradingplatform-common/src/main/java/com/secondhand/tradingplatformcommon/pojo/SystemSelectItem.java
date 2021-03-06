package com.secondhand.tradingplatformcommon.pojo;

/**
 * 系统枚举值
 *
 * @author 81079
 */

public class SystemSelectItem {

    /**
     * 展示类型
     */
    public static final Long DISPLAY_TYPE = 100101L;

    /**
     * 关联选择
     */
    public static final Long SHOW_TYPE_ASSOCIATED_CHOICE = 100201L;

    /**
     * HTML编辑器
     */
    public static final Long SHOW_TYPE_HTML_EDITOR = 100202L;

    /**
     * 附件
     */
    public static final Long SHOW_TYPE_ANNEX = 100203L;

    /**
     * 日期
     */
    public static final Long SHOW_TYPE_DATE = 100204L;

    /**
     * 文本
     */
    public static final Long SHOW_TYPE_TEXT = 100205L;

    /**
     * 比特
     */
    public static final Long SHOW_TYPE_BIT = 100206L;

    /**
     * 浮点型
     */
    public static final Long SHOW_TYPE_FLOATING_POINT = 100207L;

    /**
     * 整型
     */
    public static final Long SHOW_TYPE_INTEGER = 100208L;

    /**
     * bigint(20)
     */
    public static final Long FIELD_TYPE_BIGINT_ASSOCIATED = 100301L;

    /**
     * longtext
     */
    public static final Long FIELD_TYPE_LONGTEXT_EDITOR = 100302L;

    /**
     * text
     */
    public static final Long FIELD_TYPE_TEXT_ANNEX = 100303L;

    /**
     * datetime
     */
    public static final Long FIELD_TYPE_DATETIME = 100304L;

    /**
     * varchar(1024)
     */
    public static final Long FIELD_TYPE_VARCHAR = 100305L;

    /**
     * text
     */
    public static final Long FIELD_TYPE_TEXT_TEXT = 100306L;

    /**
     * mediumtext
     */
    public static final Long FIELD_TYPE_MEDIUMTEXT = 100307L;

    /**
     * longtext
     */
    public static final Long FIELD_TYPE_LONGTEXT_TEXT = 100308L;

    /**
     * bit(1)
     */
    public static final Long FIELD_TYPE_BIT = 100309L;

    /**
     * float
     */
    public static final Long FIELD_TYPE_FLOAT = 100310L;

    /**
     * double
     */
    public static final Long FIELD_TYPE_DOUBLE = 100311L;

    /**
     * int(11)
     */
    public static final Long FIELD_TYPE_INT = 100312L;

    /**
     * bigint(20)
     */
    public static final Long FIELD_TYPE_BIGINT_INTEGER = 100313L;

    /**
     * 用户类型
     */
    public static final Long USER_TYPE = 100403L;

    /**
     * 后台用户
     */
    public static final Long USER_TYPE_BACK_DESK = 100505L;

    /**
     * 前台用户
     */
    public static final Long USER_TYPE_FRONT_DESK = 100506L;

    /**
     * 微信用户
     */
    public static final Long USER_TYPE_WECHAT_DESK = 100507L;

    /**
     * 后台审核状态
     */
    public static final Long BACK_CHECK_STATUS = 100404L;

    /**
     * 待审核
     */
    public static final Long BACK_STATUS_PENDING_REVIEW = 100508L;

    /**
     * 审核通过
     */
    public static final Long BACK_STATUS_EXAMINATION_PASSED = 100509L;

    /**
     * 审核不通过
     */
    public static final Long BACK_STATUS_AUDIT_NOT_PASSED = 100510L;
}
