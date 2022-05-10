package com.example.demo.common.extdto.convert.trans;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

/**
 * 医用耗材目录外部数据特殊转换
 *
 * @author wujil2
 * @date 2020/10/28 14:18
 */
@Named("StringToIntegerTransForm")
public class StringToIntegerTransForm {

    @Named("stringToInteger")
    public Integer stringToInteger(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Integer.parseInt(value);
        }
        return null;
    }
}
