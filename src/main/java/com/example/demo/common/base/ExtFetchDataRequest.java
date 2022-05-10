package com.example.demo.common.base;

import com.example.demo.common.util.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 外部拉取数据入参
 *
 * @author wujlong
 * @date 2020/7/10 15:19
 */
@Getter
@NoArgsConstructor
public class ExtFetchDataRequest extends PageInfo {

    /**
     * 最后更新时间
     */
    private Date updtTime;

    /**
     * 最大版本号
     */
    private String ver;

    public ExtFetchDataRequest(Date lastUpdateTime) {
        this.updtTime = lastUpdateTime;
    }

    public ExtFetchDataRequest(String ver) {
        this.ver = ver;
    }

    public ExtFetchDataRequest(Date updtTime, int pageNum, int pageSize) {
        this.updtTime = updtTime;
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
    }

    public ExtFetchDataRequest(String ver, int pageNum, int pageSize) {
        this.ver = ver;
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
    }
}
