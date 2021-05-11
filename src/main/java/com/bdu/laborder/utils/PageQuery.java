package com.bdu.laborder.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * 接收前台分页以及查询条件的类
 * @Author Qi
 * @data 2021/3/31 22:19
 */
public class PageQuery {
    private Map<String,Object> item = null;
    private PageInfo page = null;

    public Map<String, Object> getItem() {
        return item;
    }

    public void setItem(Map<String, Object> item) {
        this.item = item;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }
}
