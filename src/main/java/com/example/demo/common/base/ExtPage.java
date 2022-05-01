package com.example.demo.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 外部数据分页对象
 *
 * @author machen
 * @date 2020/10/30 17:13
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ExtPage<T> {

    /**
     * 当前页
     */
    @NonNull
    private int pageNumber;

    /**
     * 当前页条数
     */
    @NonNull
    private int pageSize;

    /**
     * 符合条件总记录数
     */
    private int recordCount;

    /**
     * 返回总页数
     */
    @NonNull
    private int totalPages;

    /**
     * 当前页记录
     */
    @NonNull
    private List<T> records;
}
