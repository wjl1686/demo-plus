package com.example.demo.common.base;

import java.util.Date;

/**
 * 外部数据同步ES模型
 *
 * @author machen
 * @date 2020/7/9 10:56
 */
public interface ExtEsSyncModel {

    /**
     * 数据最后更新时间
     *
     * @return
     */
    Date getLastUpdateTime();

}
