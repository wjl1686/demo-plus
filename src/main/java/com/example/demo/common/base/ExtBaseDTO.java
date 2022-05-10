package com.example.demo.common.base;


import lombok.Data;

import java.util.Date;

/**
 * 外部数据基础信息
 *
 * @author wujlong
 * @date 2020/11/3 12:07
 */
@Data
public class ExtBaseDTO {

    /**
     * 信息不通过备注
     */

    public String remark;

    /**
     * 更新时间
     */
    public Date updtTime;

    /**
     * 版本号
     */
    public String ver;
}
