package com.example.demo.common.constant;

/**
 * @projectName: hsa-tps-nation
 * @package: cn.hsa.tps.losecred.constant
 * @className: LoseConsts
 * @description:
 * @author: wujlong
 * @date: 2020/12/22 下午6:26
 * @version: 1.0
 */
public final class LoseConsts {
    private LoseConsts() {
    }

    /**
     * limit 1
     */
    public static final String SQL_LIMIT_1 = " LIMIT 1 ";

    public static final String CRT_HOME_FILE_TEMP_NAME = "CRTHOME";

    /**
     * 信用评价承诺书模板
     */
    public static final String CRT_OBJ_TEMP_NAME = "crt_obj_template";
    
    public static final String LOSE_CRED_LIST_IDS = "lose:cred:list:ids";
    
    
    public static final Long LOSE_CRED_LIST_TIME = 60 * 24L;
    
    

    /**
     * 局级医保区划
     */
    public static final String ADMIN_DIVISION = "100000";

    /**
     * 信用评价用户手册编码
     */
    public static final String CRT_USER_GUIDE = "crt_user_guide";
    
    /**
     * 第一季度
     */
    public static final String QUARTER_1 = "1";
    
    /**
     * 第二季度
     */
    public static final String QUARTER_2 = "2";
    
    /**
     * 第三季度
     */
    public static final String QUARTER_3 = "3";
    
    /**
     * 第四季度
     */
    public static final String QUARTER_4 = "4";
    
    
    /**
     * Integer 类型
     */
    public static final String  FILED_TYEP_INTEGER = "java.lang.Integer";
    
    /**
     * Integer 类型
     */
    public static final String  FILED_TYEP_STRING = "java.lang.String";
    
    /**
     * Date 类型
     */
    public static final String  FILED_TYEP_DATE = "java.util.Date";
    
    /**
     * 信用评价制度: 省市区、联系人等信息行确认
     */
    public static final String  PROT_PROV_CITY_BEGIN = "省（区、市）";
    
    /**
     * 信用评价制度: 本省份的制度规定  行确认
     */
    public static final String  PROT_FILE_DOC_BEGIN = "本省份的制度规定";
    
    /**
     * 文件名开头
     */
    public static final String  PROT_FILE_NAME_BEGIN = "（文件";
    
    /**
     * 信用评价制度: 填充数据信息 sheet 行确认
     */
    public static final String  PROT_FILL_CONTENT_BEGIN = "是否组织企业提交守信承诺";
    
    /**
     * 匹配填表联系人
     */
    public static final String  PROT_FILL_CONER = "填表联系人：";
    
    
    /**
     * 匹配联系电话
     */
    public static final String  PROT_FILL_TELEPHONE = "联系电话（移动电话和座机）：";
    
    
    /**
     * 格式化截取字段 .0
     */
    public static final String  SPOT_ZERO = ".0";

    /**
     * 信用目录简称
     */
    public static final String CRED_LIST_ABBR = "信用目录简称";

    /**
     * 主要情节
     */
    public static final String MAIN_INFO = "主要情节";
    

    /**
     * 失信行为编号开头编码
     */
    public static final String LOSE_CRED_BHVR_ACT_CODE = "XW";

    /**
     * 失信行为编号-最后一位-补充字符
     */
    public static final char LOSE_CRED_BHVR_LAST_CODE = '0';
    
    /**
     * 全国
     */
    public static final String NATION_PROV_STR = "全国";
}
