package com.example.demo.common.constant;

/**
 * 外部数据同步ES索引名称
 *
 * @author wujlong
 * @date 2020/7/9 15:50
 */
public class ExtDataEsInxConstant {

    /**
     * 8包各数据项 ES 索引前缀
     */
    public static final String REDIS_EXTERNAL_ES_PREFIX = "elasticsearch:sync:";

    /**
     * 存储外部药品索引
     */
    public static final String DRUG_ES_IDX_NAME = "external-drug-info";

    /**
     * 存储外部耗材索引
     */
    public static final String MCS_PROD_IDX_NAME = "external-mcs-prod-info";

    /**
     * 存储外部耗材单件索引
     */
    public static final String MCS_PROD_SIN_IDX_NAME = "external-mcs-prod-sin-info";

    /**
     * 存储外部耗材单一规格索引
     */
    public static final String MCS_PROD_SPECMOD_IDX_NAME = "external-mcs-prod-specmod-info";

    /**
     * 存储外部耗材注册证索引
     */
    public static final String MCS_PROD_REGCERT_IDX_NAME = "external-mcs-prod-regcert-info";

    /**
     * 存储数据字典索引
     */
    public static final String DIC_IDX_NAME = "external-dic-info";

    /**
     * 存储医保区划索引
     */
    public static final String ADMDVS_IDX_NAME = "external-admdvs-info";

    /**
     * 存储医保目录索引
     */
    public static final String HI_LIST_IDX_NAME = "external-hi-list-info";

    /**
     * 存储经办机构医保单位索引
     */
    public static final String OPTINS_IDX_NAME = "external-optins-info";

    /**
     * 存储医疗机构索引
     */
    public static final String MEDINS_IDX_NAME = "external-medins-info";

    /**
     * 存储医保单位与医疗机构关系索引
     */
    public static final String MEDINS_HI_RLTS_IDX_NAME = "external-hi-rlts-info";

    /**
     * 存储零售药店索引
     */
    public static final String RTAL_PHAC_IDX_NAME = "external-rtal-phac-info";

    /**
     * 存储专家索引
     */
    public static final String PROFESSIONAL_IDX_NAME = "external-professional-info";

    /**
     * 存储企业信息（生产、配送、代理）
     */
    public static final String ENTP_IDX_NAME = "external-entp-info";

    /**
     * 存储外部耗材分类索引
     */
    public static final String MCS_TYPE_IDX_NAME = "external-mcs-type-info";

    /**
     * 存储外部耗材数据组合索引
     */
    public static final String MCS_DATA_IDX_NAME = "external-mcs-data-info";

    /**
     * 存储药品和耗材企业信息
     */
    public static final String DRUG_MCS_ENTP_IDX_NAME = "external-drug-mcs-entp-info";

    /**
     * 存储外部医联体
     */
    public static final String MEDUNION_INFO_IDX_NAME = "external-medunion-info";

    /**
     * 存储外部医联体成员
     */
    public static final String MEDUNION_MEM_INFO_IDX_NAME = "external-medunion-mem-info";

    /**
     * 存储外部耗材索引
     */
    public static final String MCS_INFO_IDX_NAME = "external-mcs-info";


    /**
     * 8包各数据项 ES 版本 索引前缀
     */
    public static final String REDIS_EXTERNAL_ES_PREFIX_VER = "elasticsearch:sync:ver:";

    /**
     * 后缀00000
     */
    public static final String SUFFIX_ZERO = "00000";

    /**
     * 长度15位
     */
    public static final int LENGTH_15 = 15;

    /**
     * 存储外部耗材关系索引
     */
    public static final String MCS_ASOC_IDX_NAME = "external-mcs-asoc-info";


}
