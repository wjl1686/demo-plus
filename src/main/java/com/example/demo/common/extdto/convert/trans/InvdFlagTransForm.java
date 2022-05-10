package com.example.demo.common.extdto.convert.trans;

import com.example.demo.common.enums.InvdFlagEnum;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

/**
 * 有效标识转换 1-0  0-1
 *
 * @author wujil2
 * @date 2020/11/18 14:18
 */
@Named("InvdFlagTransForm")
public class InvdFlagTransForm {

    @Named("invdFlag")
    public String invdFlag(String value) {
        if (StringUtils.isBlank(value)) {
            return InvdFlagEnum.NOT.code;
        }
        if (value.equals(InvdFlagEnum.YES.code)) {
            return InvdFlagEnum.NOT.code;
        } else {
            return InvdFlagEnum.YES.code;
        }
    }
}
