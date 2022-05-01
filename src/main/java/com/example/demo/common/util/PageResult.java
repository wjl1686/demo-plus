package com.example.demo.common.util;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T>
{
  private int pageNum;
  private int pageSize;
  private int size;
  private int startRow;
  private int endRow;
  private int pages;
  private int recordCounts;
  private boolean isFirstPage = false;
  private boolean isLastPage = false;
  private List<T> Data;
  
  public PageResult()
  {
    this.Data = new ArrayList();
  }
  
  public PageResult(int recordCounts, int pages, int pageSize, int pageNum, List<T> data)
  {
    this.recordCounts = recordCounts;
    this.pages = pages;
    this.pageSize = pageSize;
    this.pageNum = pageNum;
    this.Data = data;
  }
  
  public int getPageNum()
  {
    return this.pageNum;
  }
  
  public void setPageNum(int pageNum)
  {
    this.pageNum = pageNum;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public void setSize(int size)
  {
    this.size = size;
  }
  
  public int getStartRow()
  {
    return this.startRow;
  }
  
  public void setStartRow(int startRow)
  {
    this.startRow = startRow;
  }
  
  public int getEndRow()
  {
    return this.endRow;
  }
  
  public void setEndRow(int endRow)
  {
    this.endRow = endRow;
  }
  
  public int getPages()
  {
    return this.pages;
  }
  
  public void setPages(int pages)
  {
    this.pages = pages;
  }
  
  public int getRecordCounts()
  {
    return this.recordCounts;
  }
  
  public void setRecordCounts(int recordCounts)
  {
    this.recordCounts = recordCounts;
  }
  
  public boolean isFirstPage()
  {
    return this.isFirstPage;
  }
  
  public void setFirstPage(boolean isFirstPage)
  {
    this.isFirstPage = isFirstPage;
  }
  
  public boolean isLastPage()
  {
    return this.isLastPage;
  }
  
  public void setLastPage(boolean isLastPage)
  {
    this.isLastPage = isLastPage;
  }
  
  public List<T> getData()
  {
    return this.Data;
  }
  
  public void setData(List<T> data)
  {
    this.Data = data;
  }
}
