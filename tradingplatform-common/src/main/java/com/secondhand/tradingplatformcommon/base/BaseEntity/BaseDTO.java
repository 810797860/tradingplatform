package com.secondhand.tradingplatformcommon.base.BaseEntity;

import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;

/**
 * @author 81079
 */

public class BaseDTO implements Serializable {

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = -1954074095590364978L;

    /**
     * 分页参数
     */
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "page=" + page +
                '}';
    }
}
