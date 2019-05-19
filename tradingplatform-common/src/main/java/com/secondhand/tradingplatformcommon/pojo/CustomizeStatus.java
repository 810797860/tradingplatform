package com.secondhand.tradingplatformcommon.pojo;

/**
 * 自定义异常的状态码&信息
 *
 * @author 81079
 */

public enum CustomizeStatus {

    SUCCESSFUL_OPERATION(200, "操作成功"),
    LOGIN_LOG_IN_SUCCESSFUL(208, "登录成功"),

    LOGIN_VERIFICATION_CODE_ERROR(405, "验证码错误或已过期"),
    LOGIN_WRONG_PASSWORD(406, "密码错误或账号错误"),
    LOGIN_LOG_IN_ERROR(407, "登录出错"),
    LOGIN_USER_IS_LOCKED(408, "用户已经被锁定不能登录，请与管理员联系！"),
    LOGIN_CAN_ONLY_REGISTER(409, "只能注册新用户，不能修改用户信息"),
    LOGIN_HAS_EXPIRED(410, "登录已过期，请重新登录"),


    MD5_VALUE_IS_EMPTY(601, "Md5值为空"),
    IMAGE_IS_EMPTY(602, "资源文件不存在"),

    UPLOADING_IMAGE_FAILED(701, "上传图片失败"),
    FILE_READ_FAILED(702, "文件读取失败"),
    FILE_DOWNLOAD_FAILED(703, "文件下载失败"),
    FILE_CONTINUES_TO_DOWNLOAD_FAILED(704, "文件继续下载失败"),

    ADMIN_USER_ACCOUNT_ALREADY_EXISTS(801, "后台用户该账号已存在"),

    PAYMENT_VERIFICATION_FAILED(901, "支付验证失败"),
    ELECTRIC_APPLIANCE_INSUFFICIENT_BALANCE(902, "电器商城余额不足"),
    BOOK_LIBRARY_INSUFFICIENT_BALANCE(903, "图书专库余额不足"),
    SPORTS_SPECIAL_INSUFFICIENT_BALANCE(904, "运动专场余额不足"),
    DIGITAL_SQUARE_INSUFFICIENT_BALANCE(905, "数码广场余额不足"),
    RENTING_HOUSE_INSUFFICIENT_BALANCE(906, "租房专区余额不足"),

    UNAUTHORIZED_PERMISSION_DENIED(1001, "用户没有权限");

    private int code;
    private String information;

    CustomizeStatus(int code, String information) {
        this.code = code;
        this.information = information;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
