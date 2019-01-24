package com.secondhand.tradingplatformcommon.pojo;

/**
 * 抛出自定义异常
 * @author 81079
 */
public class CustomizeException extends Exception{

    private CustomizeStatus customizeStatus;
    private String clazz;
    private int code;

    public CustomizeException(CustomizeStatus customizeStatus, Class clazz) {
        this.customizeStatus = customizeStatus;
        this.clazz = clazz.getSimpleName();
        this.code = customizeStatus.getCode();
    }

    public CustomizeStatus getCustomizeStatus() {
        return customizeStatus;
    }

    public void setCustomizeStatus(CustomizeStatus customizeStatus) {
        this.customizeStatus = customizeStatus;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CustomizeException{" +
                "customizeStatus=" + customizeStatus +
                ", clazz='" + clazz + '\'' +
                ", code=" + code +
                '}';
    }
}
