package com.yanxuemeng.domain;

import java.util.List;

//这个JavaBean类用来存放和分页相关的数据参数
public class PageBean<T> {
    private List<T> pageList;//当前页数据列表 limit
    private int curPage;//当前页
    private int totalPage;//总页数，或末页
    private int count;//总记录数
    private int pageSize;//每页大小，用于设置每页显示多少条数据


    public PageBean() {
    }

    public PageBean(List<T> pageList, int curPage, int totalPage, int count, int pageSize) {
        this.pageList = pageList;
        this.curPage = curPage;
        this.totalPage = totalPage;
        this.count = count;
        this.pageSize = pageSize;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageList=" + pageList +
                ", curPage=" + curPage +
                ", totalPage=" + totalPage +
                ", count=" + count +
                ", pageSize=" + pageSize +
                '}';
    }
}
