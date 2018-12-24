package com.secondhand.tradingplatformcommon.pojo;

/**
 * 自定义异常的状态码&信息
 * @author 81079
 */

public enum CustomizeStatus {

    MD5_VALUE_IS_EMPTY(601, "Md5值为空"),
    IMAGE_IS_EMPTY(602, "资源文件不存在"),

    UPLOADING_IMAGE_FAILED(701, "上传图片失败"),
    FILE_READ_FAILED(702, "文件读取失败"),
    FILE_DOWNLOAD_FAILED(703, "文件下载失败"),
    FILE_CONTINUES_TO_DOWNLOAD_FAILED(704, "文件继续下载失败")
    ;
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
