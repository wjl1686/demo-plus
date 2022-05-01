package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 行政区信息表
 *
 * @author wujlong
 * @date 2019-10-28 12:39:55
 */
@Data
@TableName("bidprcu_admdvs_info_a")
public class AdmdvsInfoDO {

    /**
     * 行政区信息id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String admdvsInfoId;

    /**
     * 行政区划
     */
    private String admdvs;

    /**
     * 行政区划名称
     */
    private String admdvsName;

    /**
     * 上级行政区划
     */
    private String prntAdmdvs;

    /**
     * 排序
     */
    private String srt;

    /**
     * 级别
     */
    private String lv;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT, value = "CRTER_ID")
    public String crter;
    /**
     * 创建人姓名
     */
    @TableField(fill = FieldFill.INSERT, value = "CRTER_NAME")
    public String crterName;
    /**
     * 创建经办机构
     */
    @TableField(fill = FieldFill.INSERT, value = "CRTE_OPTINS_NO")
    public String crteOptins;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "CRTE_TIME")
    public Date crteTime;
    /**
     * 经办人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "OPTER_ID")
    public String opter;
    /**
     * 经办人姓名
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "OPTER_NAME")
    public String opterName;
    /**
     * 经办机构
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "OPTINS_NO")
    public String optins;
    /**
     * 经办时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "OPT_TIME")
    public Date optTime;
    /**
     * 有效标志 0:未删除  1:删除
     */
    @TableField(value = "INVD_FLAG")
    public String invdFlag;
    /**
     * 唯一记录号
     */
    @TableField(fill = FieldFill.INSERT, value = "RID")
    public String rid;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "UPDT_TIME")
    public Date updtTime;

}