package com.example.demo.common.extdto;

import com.example.demo.common.base.ExtBaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * 医保区划
 *
 * @author wujlong
 * @date 2020/11/20 14:33
 */
@Data

public class PlcAdmdvsDTO extends ExtBaseDTO {

    /**
     * 医保区划
     */
    private String admdvs;

    /**
     * 医保区划名称
     */

    private String admdvsName;

    /**
     * 上级医保区划
     */

    private String prntAdmdvs;

    /**
     * 医保区划级别
     */

    private String admdvsLv;

    /**
     * 数据来源
     */
    private String admdvsDataSouc;

    /**
     * 备注
     */
    private String memo;

    /**
     * 统筹区标志
     */
    private String poolareaFlag;

    /**
     * 有效标志
     */

    private String valiFlag;

    /**
     * 唯一记录号
     */

    private String rid;

    /**
     * 数据创建时间
     */
    private Date crteTime;

    /**
     * 创建人
     */

    private String crterId;

    /**
     * 创建人姓名
     */

    private String crterName;

    /**
     * 创建经办机构
     */

    private String crteOptinsNo;

    /**
     * 经办人
     */
    private String opterId;

    /**
     * 经办人姓名
     */

    private String opterName;

    /**
     * 经办时间
     */

    private Date optTime;

    /**
     * 经办机构
     */
    private String optinsNo;

    /**
     * 版本号
     */

    private String ver;

}
