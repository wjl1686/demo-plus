package com.example.demo.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * 高效GUID产生算法(sequence),基于Snowflake实现64位自增ID算法。
 * <p>优化开源项目 https://gitee.com/yu120/sequence</p>
 *
 * @author hubin
 * @since 2016-08-01
 */
public class DistributedIdUtil {


    public static long nextId() {
        return IdWorker.getId();
    }

    public static String nextIdStr() {
        return IdWorker.getIdStr();
    }



}
