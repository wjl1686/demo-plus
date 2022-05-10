package com.example.demo.common.executor;


import com.example.demo.common.base.ExtBaseDTO;
import com.example.demo.common.config.ApplicationContextHolder;
import com.example.demo.common.plugin.CheckDataIsNullService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 基础同步执行
 *
 * @author machen
 * @date 2020/11/5 14:05
 */
@Slf4j
@NoArgsConstructor
public class BaseSyncExecutor implements SyncExecutor {

    private CheckDataIsNullService checkDataIsNullService;

    private ExtSyncAbstract extSyncAbstract;

    public BaseSyncExecutor(ExtSyncAbstract extSyncAbstract) {
        this.extSyncAbstract = extSyncAbstract;
        checkDataIsNullService = ApplicationContextHolder.get().getBean(CheckDataIsNullService.class);
    }

    @Override
    public void executor(List<? extends ExtBaseDTO> dataList) {
        /*// 判断数据是否标准, 考虑到8包数据并不标准, 为了测试入库, 暂时注释
        Map<Boolean, List<ExtEsSyncModel>> resultMap =
                checkDataIsNullService.judgeNormAndRemarkReturnMap(dataList);*/

        try {
            extSyncAbstract.bulkToEs(dataList);
        } catch (Exception ex) {
            log.error("  >>> 执行 ES 失败 ", ex);
        }

        try {
            extSyncAbstract.bulkToDb(dataList);
        } catch (Exception ex) {
            log.error("  >>> nation执行入库失败 ",  ex);
        }
    }
}
