package com.example.demo.extdto.convert.trans;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

/**
 * 专家外部数据特殊转换转成空字符串
 *
 * @author wujil2
 * @date 2020/11/18 14:18
 */
@Named("StringToStringTransForm")
public class StringToStringTransForm {

    @Named("stringToString")
    public String stringToString(String value) {
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        return "";
    }
}
