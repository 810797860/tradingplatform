package com.secondhand.tradingplatformcommon.pojo;

/**
 * 抛出自定义异常
 * @author 81079
 */
public class CustomizeException extends Exception{

    private CustomizeStatus customizeStatus;
    private String clazz;

    public CustomizeException(CustomizeStatus customizeStatus, Class clazz) {
        this.customizeStatus = customizeStatus;
        this.clazz = clazz.getName();
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

    @Override
    public String toString() {
        return "CustomizeException{" +
                "customizeStatus=" + customizeStatus +
                ", clazz='" + clazz + '\'' +
                '}';
    }
}
