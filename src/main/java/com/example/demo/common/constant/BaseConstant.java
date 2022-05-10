package com.example.demo.common.constant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.base.ExtPage;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 基础变量
 *
 * @author wujlong
 * @date 2019/7/3 16:09
 */
public class BaseConstant {

    /**
     * 有效数据标识
     */
    public static final Integer NOT_DELETE = 0;

    /**
     * 有效标示
     */
    public static final String INVDFLAG_NO = "0";

    /**
     * 成功标识
     */
    public static final Integer SUCCESS_FLAG = 0;

    /**
     * 无效数据标识
     */
    public static final Integer DELETE = 1;

    /**
     * 结果消息分隔符
     */
    public static final String ERROR_MASSAGE_SEPARATOR = ";";

    /**
     * 增加分页常量，size 每页显示数量
     */
    public static final long PAGE_SIZE = 10;

    /**
     * 系统标识
     */
    public static final String SYS_FLAG = "tps";

    /**
     * 同步外部数据起始页
     */
    public static final Integer START_PAGE_NUM = 1;

    /**
     * 拉取外部数据一批数量
     */
    public static final Integer EXT_PULL_BATCH_SIZE = 500;

    /**
     * 外部分页空对象
     */
    public static final ExtPage EXT_PAGE_NULL = null;

    /**
     * 空字符串
     */
    public static final String EMPTY_STR = "";

    /**
     * 同步外部数据全量同步标示
     */
    public static final String FULL_UPDATE_FLAG = "all";

    /**
     * 空 Integer
     */
    public static final Integer INTEGER_NULL = null;

    /**
     * 时间空对象
     */
    public static final Date DATE_NULL = null;

    /**
     * 空字符串
     */
    public static final String STR_EMPTY = "";

    /**
     * Double空对象
     */
    public static final Double DOUBLE_NULL = null;

    /**
     * 分页空对象
     */
    public static final Page PAGE_NULL = null;

    /**
     * 未下发
     */
    public static final String ISU_FLAG_0 = "0";

    /**
     * 已下发
     */
    public static final String ISU_FLAG_1 = "1";

    public static String getPrefix(String entpQuaType){
        return StringUtils.equals("1",entpQuaType)? "药品" : "医疗器械";
    }


}
