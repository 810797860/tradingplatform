package com.secondhand.tradingplatformcommon.base.BaseEntity;

import java.io.Serializable;

/**
 * @author 81079
 */
public class Sort implements Serializable {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    public String field;

    public Boolean isAsc;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Boolean getAsc() {
        return isAsc;
    }

    public void setAsc(Boolean asc) {
        isAsc = asc;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "field='" + field + '\'' +
                ", isAsc=" + isAsc +
                '}';
    }
}
