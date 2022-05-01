package com.example.demo.common.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tree<T> {
    /**
     * 节点ID
     */
    private String admdvs;
    /**
     * 图标
     */
    private String icon;
    /**
     * url
     */
    private String url;
    /**
     * 显示节点文本
     */
    private String admdvsName;

    private String status = "close";

    /**
     * 节点的子节点
     */
    private List<Tree<T>> children = new ArrayList<>();

    /**
     * 父ID parentId
     */
    private String prntAdmdvs;

}