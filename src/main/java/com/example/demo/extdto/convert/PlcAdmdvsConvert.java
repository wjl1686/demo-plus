package com.example.demo.extdto.convert;

import com.example.demo.entity.AdmdvsInfoDO;
import com.example.demo.extdto.PlcAdmdvsDTO;
import com.example.demo.extdto.convert.trans.InvdFlagTransForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 医保区划转换
 *
 * @author wujil2
 * @date 2020/10/28 14:18
 */
@Mapper(uses = {InvdFlagTransForm.class})
public interface PlcAdmdvsConvert {

    PlcAdmdvsConvert INSTANCE = Mappers.getMapper(PlcAdmdvsConvert.class);

    /**
     * 医保区划转换
     *
     * @param source
     * @return
     */
    @Mappings({
            @Mapping(source = "admdvs", target = "admdvsInfoId"),//ID
            @Mapping(source = "admdvs", target = "admdvs"), // 医保区划
            @Mapping(source = "admdvsName", target = "admdvsName"), // 医保区划名称
            @Mapping(source = "prntAdmdvs", target = "prntAdmdvs"), // 上级医保区划
            @Mapping(source = "admdvsLv", target = "lv"), // 级别
            @Mapping(source = "valiFlag", target = "invdFlag",qualifiedByName = {"InvdFlagTransForm", "invdFlag"})
    })
    AdmdvsInfoDO extDtoToDoOne(PlcAdmdvsDTO source);

    List<AdmdvsInfoDO> extDtoToDo(List<PlcAdmdvsDTO> source);
}
