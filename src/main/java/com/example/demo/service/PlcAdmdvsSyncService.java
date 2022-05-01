package com.example.demo.service;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.base.ExtFetchDataRequest;
import com.example.demo.common.base.ExtPage;
import com.example.demo.constant.ExtDataEsInxConstant;
import com.example.demo.entity.AdmdvsInfoDO;
import com.example.demo.executor.ExtSyncAbstract;
import com.example.demo.extdto.PlcAdmdvsDTO;
import com.example.demo.extdto.convert.PlcAdmdvsConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 医保区划数据同步
 *
 * @author wujil2
 * @date 2020/8/10 15:44
 */
@Slf4j
@Component
public class PlcAdmdvsSyncService extends ExtSyncAbstract {

    /*@Autowired(required = false)
    private QueryAdmdvsService queryAdmdvsService;*/

    @Autowired
    private AdmdvService admdvsInfoBO;

    @Override
    protected String getIdxName() {
        return ExtDataEsInxConstant.ADMDVS_IDX_NAME;
    }

    @Override
    protected ExtPage fetchSourceData(ExtFetchDataRequest request) {
        // QAdmdvsDTO admdvsDTO = BeanUtil.convert(request, QAdmdvsDTO.class);
        //WrapperResponse<PageResult<AdmdvsDTO>> responsePageResult = queryAdmdvsService.queryAdmdvsByPage(admdvsDTO);
        // 判断调取中台接口是否成功并且是否有数据
        /*if (!isSuccessAndData(responsePageResult)) {
            return BaseConstant.EXT_PAGE_NULL;
        }*/

        //List<PlcAdmdvsDTO> extDicList =
        // BeanUtil.convert(responsePageResult.getData().getData(), PlcAdmdvsDTO.class);

        //return buildDataResultPage(responsePageResult, extDicList);
        return null;
    }

    @Override
    protected void bulkToDb(List<?> sourceList) {
        List<AdmdvsInfoDO> admdvsInfoDOS = null;
        try {
            admdvsInfoDOS = PlcAdmdvsConvert.INSTANCE.extDtoToDo((List<PlcAdmdvsDTO>) sourceList);
            admdvsInfoBO.saveOrUpdateBatch(admdvsInfoDOS);
        } catch (Exception ex) {
            log.error(String.format("  >>> 医保区划同步失败, 失败记录 :: %s", JSON.toJSONString(admdvsInfoDOS)), ex);
            throw ex;
        }
    }

    @Override
    protected Date getUpTime() {
        return null;
    }
}
