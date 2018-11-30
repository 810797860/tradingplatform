package com.secondhand.tradingplatformcommon.base.BaseEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.plugins.Page;

import java.io.Serializable;
import java.util.List;

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
    @TableField(exist = false)
    private Page page;

    /**
     * 排序参数
     */
    @TableField(exist = false)
    public List<Sort> sorts;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "page=" + page +
                ", sorts=" + sorts +
                '}';
    }
}
