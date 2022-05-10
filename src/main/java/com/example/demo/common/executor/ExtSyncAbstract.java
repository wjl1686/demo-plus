package com.example.demo.common.executor;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.common.base.ExtBaseDTO;
import com.example.demo.common.base.ExtFetchDataRequest;
import com.example.demo.common.base.ExtPage;
import com.example.demo.common.util.PageResult;
import com.example.demo.common.util.WrapperResponse;
import com.example.demo.common.constant.BaseConstant;
import com.example.demo.common.constant.ExtDataEsInxConstant;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 外部数据同步模版类
 *
 * @author wujlong
 * @date 2020/7/9 10:59
 */
@Slf4j
public abstract class ExtSyncAbstract {

    /*@Autowired
    private CacheUtil cacheUtil;*/

  /*  @Autowired
    private ElasticsearchTemplate template;*/

    /**
     * 获取ES索引名称
     *
     * @return
     */
    protected abstract String getIdxName();

    /**
     * 由各实现类提供数据
     *
     * @param request
     * @return
     */
    protected abstract ExtPage fetchSourceData(ExtFetchDataRequest request);

    /**
     * 外部数据保存到数据库
     *
     * @param esSyncModelList
     */
    protected abstract void bulkToDb(List<?> esSyncModelList);


    /**
     * 标志 空 增量数据 否则全量数据
     *
     * @param
     */
    protected String flag;

    /**
     * 获取数据库更新时间最大值
     */
    protected abstract Date getUpTime();

    @Value("${pageSize:}")
    private String pageSize;

    /**
     * 同步方法
     *
     * @return
     */
    private final long sync() {
        long count = 0;
        List<String> excludes = excludesList();
        ExtFetchDataRequest request = null;
        log.info("pageSize=" + pageSize);
        if (!excludes.contains(getIdxName())) {
            if (StringUtils.isNotBlank(pageSize)) {
                request =
                        new ExtFetchDataRequest(getLastUpdateTime(), BaseConstant.START_PAGE_NUM, Integer.parseInt(pageSize));
            } else {
                request =
                        new ExtFetchDataRequest(getLastUpdateTime(), BaseConstant.START_PAGE_NUM, BaseConstant.EXT_PULL_BATCH_SIZE);
            }
        } else {
            request =
                    new ExtFetchDataRequest(getMaxVer(), BaseConstant.START_PAGE_NUM, BaseConstant.EXT_PULL_BATCH_SIZE);
        }
        log.info("request={}", JSON.toJSONString(request));
        // 首次查询, 并获取分页信息
        ExtPage extPage = fetchSourceData(request);
        if (Objects.isNull(extPage)) {
            return 0L;
        }
        List<? extends ExtBaseDTO> docs = extPage.getRecords();
        log.info("  >>> 同步数据标志 [{}],当前页 :: [{}], 总页数 :: {},总条数size :: {} ", getIdxName(), extPage.getPageNumber(), extPage.getTotalPages(), extPage.getRecords().size());
        //记录用户中心最大版本
        CopyOnWriteArrayList<BigDecimal> maxVerList = new CopyOnWriteArrayList<>();
        //记录政策中心最大时间版本
        CopyOnWriteArrayList<Date> maxTimeList = new CopyOnWriteArrayList<>();
        if (!excludes.contains(getIdxName())) {
            maxTimeList.add(bulkAndUpdateTime(docs));
        } else {
            maxVerList.add(bulkAndMaxVer(docs));
        }
        count += docs.size();
        //记录每页时间

        while (extPage.getPageNumber() < extPage.getTotalPages()) {
            // 一次同步只更新分页信息, 不同步更新时间
            request.setPageNum(extPage.getPageNumber() + 1);
            log.info("  >>> 拉取外部数据 :: [{}] 开始执行, 当前页 :: [{}], 当前拉取条数 :: [{}], 满足记录总行数 :: [{}], 总页数 :: [{}] ",
                    getIdxName(), extPage.getPageNumber(), extPage.getPageSize(), extPage.getRecordCount(), extPage.getTotalPages());
            log.info("req参数={}", JSON.toJSONString(request));
            extPage = fetchSourceData(request);
            if (Objects.isNull(extPage)) {
                break;
            }
            docs = extPage.getRecords();
            if (!excludes.contains(getIdxName())) {
                maxTimeList.add(bulkAndUpdateTime(docs));
            } else {
                maxVerList.add(bulkAndMaxVer(docs));
            }
            count += docs.size();
        }
        if (!excludes.contains(getIdxName())) {
            Date maxTime = Collections.max(maxTimeList);
            //cacheUtil.set(getCacheKey(), maxTime, 365, TimeUnit.DAYS);
            log.info("  >>> [8包基础数据定时任务] 同步数据完成 :: [{}], 记录最后更新Max时间 :: [{}]", getCacheKey(), DateUtil.format(maxTime, DatePattern.NORM_DATETIME_PATTERN));
        } else {
            String ver = Collections.max(maxVerList).toString();
            //cacheUtil.set(getCacheVerKey(), ver, 365, TimeUnit.DAYS);
            log.info("  >>> [8包基础数据定时任务] 同步数据完成 :: [{}], 记录最后更新Max版本 :: [{}]", getCacheVerKey(), ver);
        }
        return count;
    }

    public final void pullExternalDataAndSync(String param) {
        flag = param;
        List<String> excludes = excludesList();
        String index = getIdxName();
        log.info("  >>> [8包基础数据定时任务] 开始同步数据, 类型名称 :: [{}], 缓存 key :: [{}]", index, getCacheKey());
        try {
            if (Objects.equals(BaseConstant.FULL_UPDATE_FLAG, param)) {
                if (!excludes.contains(getIdxName())) {
                    // cacheUtil.del(getCacheKey());
                } else {
                    // cacheUtil.del(getCacheVerKey());
                }

                log.info("  >>> [8包基础数据定时任务] 全量同步 :: [{}]", index);
            }
            if (!excludes.contains(getIdxName())) {
                log.info("  >>> [8包基础数据定时任务] 获取的最后更新时间 :: [{}]", getFormatLastUpdateTime());
            } else {
                log.info("  >>> [8包基础数据定时任务] 获取的最后版本 :: [{}]", getMaxVer());
            }
            long count = sync();
            if (!excludes.contains(getIdxName())) {
                log.info("  >>> [8包基础数据定时任务] 共处理 [{}] 条数据, 记录的最后更新时间 :: [{}],类型名称 ::[{}]", count, getFormatLastUpdateTime(), index);
            } else {
                log.info("  >>> [8包基础数据定时任务] 共处理 [{}] 条数据, 记录的最后版本 :: [{}],类型名称 ::[{}]", count, getMaxVer(), index);
            }
        } catch (Exception e) {
            log.error(String.format("  >>> [8包基础数据定时任务] 同步外部数据异常, 类型名称 :: [%s]", index), e);
        }
    }

    /**
     * 组装缓存的key
     *
     * @return
     */
    private String getCacheKey() {
        StringBuilder cacheKey =
                new StringBuilder(ExtDataEsInxConstant.REDIS_EXTERNAL_ES_PREFIX)
                        .append(getIdxName());
        return cacheKey.toString();
    }

    /**
     * 获取最后更新时间
     *
     * @return
     */
    private Date getLastUpdateTime() {
        // Date lastUpdateTime = cacheUtil.get(getCacheKey(), Date.class);

        Date lastUpdateTime = null;
        log.info("key={},redis-upTime={}", getIdxName(), lastUpdateTime);
        Date upTime = getUpTime();
        if (upTime == null) {
            upTime = DateTime.parse("1970-01-01").toDate();
        }
        return lastUpdateTime == null ? upTime : lastUpdateTime;
    }

    /**
     * 获取格式化最后更新时间
     *
     * @return
     */
    private String getFormatLastUpdateTime() {
        return DateUtil.format(getLastUpdateTime(), DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 保存最后更新时间
     *
     * @param docs
     */
    private Date saveLastUpdateTime(List<? extends ExtBaseDTO> docs) {
        if (docs.size() == 0) {
            return null;
        }
        Date maxTime = null;
        String index = new StringBuilder(ExtDataEsInxConstant.REDIS_EXTERNAL_ES_PREFIX).append(getIdxName()).toString();
        try {
            //所有最大时maxTime间
            maxTime = Collections.max(docs.stream().map(ExtBaseDTO::getUpdtTime).collect(Collectors.toSet()));
            //cacheUtil.set(getCacheKey(), maxTime, 365, TimeUnit.DAYS);
            log.info("  >>> [8包基础数据定时任务] 同步数据 :: [{}], 每页记录最后更新Max时间 :: [{}]", index, DateUtil.format(maxTime, DatePattern.NORM_DATETIME_PATTERN));
        } catch (Exception ex) {
            log.error("  >>> [8包基础数据定时任务] 更新缓存失败, 缓存名称 :: {} ", index, ex);
        }
        return maxTime;
    }

    /**
     * 存储数据至ES或DB, 累计操作数据数量, 更新最后更新时间
     *
     * @param docs
     * @return
     */
    private Date bulkAndUpdateTime(List<? extends ExtBaseDTO> docs) {
        SyncExecutor executor = ExtConfiguration.newSyncExecutor(this);
        executor.executor(docs);
        return saveLastUpdateTime(docs);
    }

    /**
     * 数据批量同步ES
     *
     * @param docs
     */
    protected <T> void bulkToEs(List<T> docs) {
        List<String> excludes = Lists.newArrayList();
        // 经办关系
        excludes.add(ExtDataEsInxConstant.MEDINS_HI_RLTS_IDX_NAME);
        // 医保目录
        excludes.add(ExtDataEsInxConstant.HI_LIST_IDX_NAME);
        // 医联体
        excludes.add(ExtDataEsInxConstant.MEDUNION_INFO_IDX_NAME);
        // 医联体成员
        excludes.add(ExtDataEsInxConstant.MEDUNION_MEM_INFO_IDX_NAME);
        // 药品和耗材企业信息
        excludes.add(ExtDataEsInxConstant.DRUG_MCS_ENTP_IDX_NAME);
        //  耗材信息
        excludes.add(ExtDataEsInxConstant.MCS_INFO_IDX_NAME);

        // 耗材注冊證
        excludes.add(ExtDataEsInxConstant.MCS_PROD_REGCERT_IDX_NAME);

        if (!excludes.contains(getIdxName())) {
            // template.syncSave(docs);
        }
    }

    /**
     * 判断调用中台接口是否成功并且有数据
     *
     * @param wrapperResponse
     * @return
     */
    protected <T> boolean isSuccessAndData(WrapperResponse<PageResult<T>> wrapperResponse) {
        if (Objects.isNull(wrapperResponse)
                || !Objects.equals(wrapperResponse.getCode(), BaseConstant.SUCCESS_FLAG)
                || Objects.isNull(wrapperResponse.getData())
                || CollectionUtil.isEmpty(wrapperResponse.getData().getData())) {
            log.info("同步8包数据Index名称={},数据data={}", getIdxName(), JSON.toJSONString(wrapperResponse));
            return false;
        }
        return true;
    }

    /**
     * 构建调取外部数据接口返回数据组装为Page对象
     *
     * @param response
     * @param dataList
     * @return
     */
    public <T> ExtPage buildDataResultPage(WrapperResponse<PageResult<T>> response, List<?> dataList) {
        log.info("8包分页信息: 8包当前页={}，8包总页数={{}", response.getData().getPageNum(), response.getData().getPages());
        return new ExtPage(response.getData().getPageNum(), BaseConstant.EXT_PULL_BATCH_SIZE, response.getData().getPages(), dataList);
    }

    /**
     * 获取最大的版本
     *
     * @return
     */
    private String getMaxVer() {
        // String ver = cacheUtil.get(getCacheVerKey(), String.class);
        String ver = "";
        return StringUtils.isBlank(ver) ? "0" : ver;
    }

    /**
     * 保存最大的版本
     *
     * @param docs
     */
    private BigDecimal saveMaxVer(List<? extends ExtBaseDTO> docs) {
        if (docs.size() == 0) {
            return null;
        }
        BigDecimal ver = new BigDecimal(0);
        String index = new StringBuilder(ExtDataEsInxConstant.REDIS_EXTERNAL_ES_PREFIX_VER).append(getIdxName()).toString();
        try {
            //所有最大时maxTime间
            Set<String> collect = docs.stream().map(ExtBaseDTO::getVer).collect(Collectors.toSet());
            List<BigDecimal> bigDecimals = new ArrayList<>();
            for (String s : collect) {
                bigDecimals.add(new BigDecimal(s));
            }
            ver = Collections.max(bigDecimals);
            //cacheUtil.set(getCacheVerKey(), ver, 365, TimeUnit.DAYS);
            log.info("  >>> [8包基础数据定时任务] 同步数据 :: [{}], 每页记录最大版本 :: [{}]", index, ver);
        } catch (Exception ex) {
            log.error("  >>> [8包基础数据定时任务] 更新缓存失败, 缓存名称 :: {} ", index, ex);
        }
        return ver;
    }

    /**
     * 存储数据至ES或DB, 累计操作数据数量, 返回最后最大版本
     *
     * @param docs
     * @return
     */
    private BigDecimal bulkAndMaxVer(List<? extends ExtBaseDTO> docs) {
        SyncExecutor executor = ExtConfiguration.newSyncExecutor(this);
        executor.executor(docs);
        return saveMaxVer(docs);
    }

    /**
     * 组装缓存的key
     *
     * @return
     */
    private String getCacheVerKey() {
        StringBuilder cacheKey =
                new StringBuilder(ExtDataEsInxConstant.REDIS_EXTERNAL_ES_PREFIX_VER)
                        .append(getIdxName());
        return cacheKey.toString();
    }

    private List<String> excludesList() {
        List<String> excludes = Lists.newArrayList();
        // 专家
        //excludes.add(ExtDataEsInxConstant.PROFESSIONAL_IDX_NAME);
        // 医院
        //excludes.add(ExtDataEsInxConstant.MEDINS_IDX_NAME);
        // 经办机构
        //excludes.add(ExtDataEsInxConstant.OPTINS_IDX_NAME);
        // 药品和耗材企业
        //excludes.add(ExtDataEsInxConstant.DRUG_MCS_ENTP_IDX_NAME);
        // 药店
        //excludes.add(ExtDataEsInxConstant.RTAL_PHAC_IDX_NAME);
        return excludes;
    }

}