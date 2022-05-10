package com.example.demo.common.executor;

import com.example.demo.common.constant.ExtDataEsInxConstant;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 外部数据同步全局配置
 *
 * @author wujlong
 * @date 2020/11/5 14:32
 */
public class ExtConfiguration {

    private static final List<String> FAST_LIST = Lists.newArrayList(
            ExtDataEsInxConstant.DRUG_ES_IDX_NAME,
            ExtDataEsInxConstant.OPTINS_IDX_NAME,
            ExtDataEsInxConstant.DRUG_MCS_ENTP_IDX_NAME,
            ExtDataEsInxConstant.MCS_PROD_SPECMOD_IDX_NAME,
            ExtDataEsInxConstant.MEDINS_IDX_NAME,
            ExtDataEsInxConstant.MCS_PROD_SIN_IDX_NAME,
            ExtDataEsInxConstant.MCS_PROD_REGCERT_IDX_NAME,
            ExtDataEsInxConstant.MCS_INFO_IDX_NAME,
            ExtDataEsInxConstant.RTAL_PHAC_IDX_NAME,
            ExtDataEsInxConstant.MCS_ASOC_IDX_NAME);

    public static SyncExecutor newSyncExecutor(ExtSyncAbstract extSyncAbstract) {
        SyncExecutor syncExecutor;
        if (FAST_LIST.contains(extSyncAbstract.getIdxName())) {
            syncExecutor = new FastSyncExecutor(extSyncAbstract);
        } else {
            syncExecutor = new SimpleSyncExecutor(extSyncAbstract);
        }
        return syncExecutor;
    }
}
