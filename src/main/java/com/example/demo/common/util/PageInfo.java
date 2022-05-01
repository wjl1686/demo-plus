package com.example.demo.common.util;

import java.io.Serializable;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int pages;
    private int recordCounts;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage = false;
    private boolean isLastPage = false;

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return this.prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isFirstPage() {
        return this.isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return this.isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public int getRecordCounts() {
        return this.recordCounts;
    }

    public void setRecordCounts(int recordCounts) {
        this.recordCounts = recordCounts;
    }

    public String toString() {
        return "PageInfo [pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", size=" + this.size + ", startRow=" + this.startRow + ", endRow=" + this.endRow + ", pages=" + this.pages + ", recordCounts=" + this.recordCounts + ", prePage=" + this.prePage + ", nextPage=" + this.nextPage + ", isFirstPage=" + this.isFirstPage + ", isLastPage=" + this.isLastPage + "]";
    }
}
