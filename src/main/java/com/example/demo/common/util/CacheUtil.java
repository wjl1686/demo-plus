package com.example.demo.common.util;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.example.demo.common.constant.NumberConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 缓存工具
 *
 * @author wujlong
 * @version 1.0
 * @since 2019-07-22 15:35
 */
@Component
public class CacheUtil {
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;

    private RedisTemplate stringRedisTemplate;


    public CacheUtil(RedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 设置缓存
     *
     * @param key        缓存key
     * @param object     缓存对象
     * @param ttlMinutes 过期时间
     */
    public void set(String key, Object object, long ttlMinutes) {
        set(key, object, ttlMinutes, DEFAULT_TIME_UNIT);
    }

    public void set(String key, Object object) {
        checkKeyNotBlank(key);

        String json = object instanceof String ? (String) object : JSON.toJSONString(object);
        stringRedisTemplate.opsForValue().set(key, json);
    }

    /**
     * 设置缓存
     *
     * @param key      缓存key
     * @param object   缓存对象
     * @param ttl      过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object object, long ttl, TimeUnit timeUnit) {
        checkKeyNotBlank(key);

        String json = object instanceof String ? (String) object : JSON.toJSONString(object);
        stringRedisTemplate.opsForValue().set(key, json, ttl, timeUnit);
    }

    /**
     * 放置一个键值对
     *
     * @param key     hash键
     * @param hashKey hash key
     * @param value   map值
     */
    public void putHash(String key, String hashKey, Object value) {
        checkKeyNotBlank(key);

        String json = JSON.toJSONString(value);
        stringRedisTemplate.opsForHash().put(key, hashKey, json);
    }

    /**
     * 放置一个键值对 并设置过期时间
     *
     * @param key     hash键
     * @param hashKey hash key
     * @param value   map值
     */
    public void putHash(String key, String hashKey, Object value, long ttlMinutes) {
        putHash(key, hashKey, value, ttlMinutes, DEFAULT_TIME_UNIT);
    }

    /**
     * 放置一个键值对 并设置过期时间
     *
     * @param key     hash键
     * @param hashKey hash key
     * @param value   map值
     */
    public void putHash(String key, String hashKey, Object value, long ttl, TimeUnit timeUnit) {
        putHash(key, hashKey, value);
        // 设置过期时间
        expire(key, ttl, timeUnit);
    }

    /**
     * 放入map中所有键值对
     *
     * @param key key
     * @param map hash
     */
    public void putHash(String key, Map<String, ?> map) {
        checkKeyNotBlank(key);

        Map<String, Object> mapForSave = new HashMap<>(map.size());
        map.forEach((hashKey, val) -> mapForSave.put(hashKey, JSON.toJSONString(val)));
        stringRedisTemplate.opsForHash().putAll(key, mapForSave);
    }


    public void putHashByInteger(String key, Map<String, Integer> map, long ttl, TimeUnit timeUnit) {
        checkKeyNotBlank(key);

        Map<String, Object> mapForSave = new HashMap<>(map.size());
        map.forEach((hashKey, val) -> mapForSave.put(hashKey, val.toString()));
        stringRedisTemplate.opsForHash().putAll(key, mapForSave);
        expire(key, ttl, timeUnit);
    }

    public void putHashNew(String key, Map<String, ?> map) {
        checkKeyNotBlank(key);
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 放入map中所有键值对 并设置过期时间
     *
     * @param key key
     * @param map hash
     */
    public void putHash(String key, Map<String, ?> map, long ttlMinutes) {
        putHash(key, map, ttlMinutes, DEFAULT_TIME_UNIT);
    }

    /**
     * 放入map中所有键值对 并设置过期时间和时间单位
     *
     * @param key key
     * @param map hash
     */
    public void putHash(String key, Map<String, ?> map, long ttl, TimeUnit timeUnit) {
        putHashNew(key, map);
        expire(key, ttl, timeUnit);
    }

    /**
     * 获取普通对象
     *
     * @param key   key
     * @param clazz 类对象
     * @param <T>   T
     * @return 普通对象
     */
    @SuppressWarnings("all")
    public <T> T get(String key, Class<T> clazz) {
        checkKeyNotBlank(key);

        // 如果取String类型 则直接取出返回
        if (String.class.isAssignableFrom(clazz)) {
            return (T) stringRedisTemplate.opsForValue().get(key);
        }
        return getComplex(key, clazz);
    }

    /**
     * 从hash中获取普通对象
     *
     * @param key     key
     * @param hashKey hash key
     * @param clazz   类对象
     * @param <T>     T
     * @return 普通对象
     */
    @SuppressWarnings("all")
    public <T> T getForHash(String key, String hashKey, Class<T> clazz) {
        checkKeyNotBlank(key, hashKey);

        // 如果取String类型 则直接取出返回
        if (String.class.isAssignableFrom(clazz)) {
            return (T) stringRedisTemplate.opsForHash().get(key, hashKey);
        }
        return getComplexForHash(key, hashKey, clazz);
    }


    /**
     * 获取对象列表
     *
     * @param key   key
     * @param clazz 类对象
     * @param <T>   T
     * @return 对象列表
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        return getComplex(key, List.class, clazz);
    }

    /**
     * 从hash中获取集合对象
     *
     * @param key     key
     * @param hashKey hash key
     * @param clazz   类对象
     * @param <T>     T
     * @return 普通对象
     */
    public <T> T getListForHash(String key, String hashKey, Class<T> clazz) {
        return getComplexForHash(key, hashKey, List.class, clazz);
    }

    /**
     * 获取缓存对象
     * 需要传入所有的泛型类对象 例如Result<List<FileInfoBO>> 需要传入 Result.class List.class FileInfoBO.class
     *
     * @param key     缓存键
     * @param classes 类型参数
     * @return 获取的对象
     */
    public <T> T getComplex(String key, Class... classes) {
        checkKeyNotBlank(key);

        String cachedValue = (String) stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(cachedValue)) {
            return null;
        }
        return JSONObject.parseObject(cachedValue, buildType(classes));
    }

    /**
     * 获取hash缓存对象
     * 需要传入所有的泛型类对象 例如Result<List<FileInfoBO>> 需要传入 Result.class List.class FileInfoBO.class
     *
     * @param key     缓存键
     * @param classes 类型参数
     * @param hashKey hash key
     * @return 获取的对象
     */
    public <T> T getComplexForHash(String key, String hashKey, Class... classes) {
        checkKeyNotBlank(key, hashKey);

        Object cachedValue = stringRedisTemplate.opsForHash().get(key, hashKey);
        return cachedValue instanceof String ? JSONObject.parseObject((String) cachedValue, buildType(classes))
                : null;
    }

    /**
     * 判断key是否存在
     *
     * @param key key
     * @return 是否存在 可能为空
     */
    public Boolean hasKey(String key) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 获取key超时时间
     *
     * @param key key
     * @return 超时时间
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 判断hash中 key是否存在
     *
     * @param key key
     * @return 是否存在 可能为空
     */
    public Boolean hasKeyForHash(String key, String hashKey) {
        checkKeyNotBlank(key, hashKey);

        return stringRedisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 删除key
     *
     * @param key key
     */
    public Boolean del(String key) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.delete(key);
    }

    /**
     * 删除hash key
     *
     * @param key     key
     * @param hashKey hash key
     */
    public Long delForHash(String key, String hashKey) {
        checkKeyNotBlank(key, hashKey);

        return stringRedisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 批量删除hash key
     *
     * @param key      key
     * @param hashKeys hash keys
     */
    public Long delForHash(String key, Collection<String> hashKeys) {
        checkKeyNotBlank(key);
        Objects.requireNonNull(hashKeys, "keys不能为null");

        return stringRedisTemplate.opsForHash().delete(key, hashKeys.toArray());
    }

    /**
     * 批量删除key
     *
     * @param keys keys
     */
    public Long del(Collection<String> keys) {
        Objects.requireNonNull(keys, "keys不能为null");
        return stringRedisTemplate.delete(keys);
    }

    /**
     * 设置key过期时间
     *
     * @param key      key
     * @param ttl      过期时间
     * @param timeUnit 时间单位
     */
    public Boolean expire(String key, long ttl, TimeUnit timeUnit) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.expire(key, ttl, timeUnit);
    }

    /**
     * 生成每天重置的自增id
     *
     * @param key key
     */
    public long incrementDaily(String key) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory(), "redis连接工厂初始化有误");
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        // 设置一天的末尾超时
        counter.expireAt(getTodayEndTime());
        return counter.incrementAndGet();
    }

    /**
     * 获取自增值
     */
    public long getIncr(String key, Integer value, long ttl, TimeUnit timeUnit) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory(), "redis连接工厂初始化有误");
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        if (ObjectUtils.allNotNull(value)) {
            counter.set(value);
        }
        counter.expire(ttl, timeUnit);
        return counter.incrementAndGet();
    }

    /**
     * 失信行为编号-最后一位自增id
     * 可以左侧补充字符
     *
     * @param key     key
     * @param length  补充长度(补充到x位)
     * @param padChar 补充字符
     */
    public String generateIncrOfLastBhvrNo(String key, int length, char padChar) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory(), "redis连接工厂初始化有误");
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expire(NumberConstants.THREE, TimeUnit.DAYS);
        long count = counter.incrementAndGet();
        return StringUtils.leftPad(String.valueOf(count), length, padChar);
    }

    /**
     * 产品资质申报-药品-资审共享资质编码-最后一位自增id
     * 可以左侧补充字符
     *
     * @param key     key
     * @param length  补充长度(补充到x位)
     * @param padChar 补充字符
     */
    public String generateIncrOfLastQuaCode(String key, int length, char padChar) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory(), "redis连接工厂初始化有误");
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expire(NumberConstants.ONE, TimeUnit.HOURS);
        long count = counter.incrementAndGet();
        return StringUtils.leftPad(String.valueOf(count), length, padChar);
    }

    public String generateIncrOfLastQuaCode(String key, int length, char padChar, int time, TimeUnit timeUnit) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory(), "redis连接工厂初始化有误");
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expire(time, timeUnit);
        long count = counter.incrementAndGet();
        return StringUtils.leftPad(String.valueOf(count), length, padChar);
    }

    /**
     * 生成每天重置的自增id
     * 可以左侧补充字符
     *
     * @param key     key
     * @param length  补充长度(补充到x位)
     * @param padChar 补充字符
     */
    public String incrementDaily(String key, int length, char padChar) {
        long count = incrementDaily(key);
        return StringUtils.leftPad(String.valueOf(count), length, padChar);
    }

    /**
     * 存储有序列表并设置超时时间(分钟)
     *
     * @param key key
     * @param map key为score(排序值) value为object(要存储的对象)
     * @return 影响的条目
     */
    public Long zSet(String key, Map<?, Double> map, Long ttlMinutes) {
        return zSet(key, map, ttlMinutes, DEFAULT_TIME_UNIT);
    }

    /**
     * 存储有序列表并设置超时时间 自定义单位
     *
     * @param key key
     * @param map key为score(排序值) value为object(要存储的对象)
     * @return 影响的条目
     */
    public Long zSet(String key, Map<?, Double> map, Long ttl, TimeUnit timeUnit) {
        checkKeyNotBlank(key);

        Set<ZSetOperations.TypedTuple<String>> collect = map.entrySet().stream()
                .map(item -> new DefaultTypedTuple<>(JSON.toJSONString(item.getKey()), item.getValue()))
                .collect(Collectors.toSet());
        Long count = stringRedisTemplate.opsForZSet().add(key, collect);
        expire(key, ttl, timeUnit);
        return count;
    }

    /**
     * 获取有序列表中范围条目,并转为指定类型
     *
     * @param key   key
     * @param start 开始下标 从0开始
     * @param end   结束下标 包含此条
     * @param clazz 序列化类型
     * @param <T>   泛型参数
     * @return 结果set集合
     */
    public <T> List<T> getRange(String key, long start, long end, Class<T> clazz) {
        checkKeyNotBlank(key);

        Set<String> resultSet = stringRedisTemplate.opsForZSet().range(key, start, end);
        return null == resultSet ? null : resultSet.stream()
                .map(item -> JSON.parseObject(item, clazz)).collect(Collectors.toList());
    }

    /**
     * 反转获取有序列表中范围条目,并转为指定类型
     *
     * @param key   key
     * @param start 开始下标 从0开始
     * @param end   结束下标 包含此条
     * @param clazz 序列化类型
     * @param <T>   泛型参数
     * @return 结果set集合
     */
    public <T> List<T> getReverseRange(String key, long start, long end, Class<T> clazz) {
        checkKeyNotBlank(key);

        Set<String> resultSet = stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
        return null == resultSet ? null : resultSet.stream()
                .map(item -> JSON.parseObject(item, clazz)).collect(Collectors.toList());
    }

    /**
     * 删除zSet条目
     *
     * @param key   key
     * @param value 数据
     * @return 影响条目
     */
    public Long delForZSet(String key, Object value) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.opsForZSet().remove(key, JSON.toJSONString(value));
    }

    /**
     * 批量删除zSet条目
     *
     * @param key             key
     * @param valueCollection 数据
     * @return 影响条目
     */
    public Long delForZSet(String key, Collection<?> valueCollection) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.opsForZSet().remove(key
                , valueCollection.stream().map(JSON::toJSONString).distinct().toArray());
    }

    /**
     * 删除范围元素
     *
     * @param key   key
     * @param start 开始range
     * @param end   结束range
     * @return 影响条目
     */
    public Long delForRange(String key, Long start, Long end) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.opsForZSet().removeRange(key, start, end);
    }


    /**
     * 计算zSet总条数
     *
     * @param key key
     * @return 总条数  不存在则空
     */
    public Long sizeForZSet(String key) {
        return stringRedisTemplate.opsForZSet().size(key);
    }

    /**
     * 查询 zSet中值的下标 如不存在返回null
     *
     * @param key   key
     * @param value value
     * @return rank
     */
    public Long rankForZSet(String key, Object value) {
        checkKeyNotBlank(key);

        return stringRedisTemplate.opsForZSet().rank(key, JSON.toJSONString(value));
    }

    private static Type buildType(Type... types) {
        ParameterizedTypeImpl beforeType = null;
        if (types != null && types.length > 0) {
            if (types.length == 1) {
                return new ParameterizedTypeImpl(new Type[]{null}, null,
                        types[0]);
            }

            for (int i = types.length - 1; i > 0; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? types[i] : beforeType}, null,
                        types[i - 1]);
            }
        }
        return beforeType;
    }

    private static void checkKeyNotBlank(String... key) {
        for (String ks : key) {
            if (StringUtils.isBlank(ks)) {
                throw new IllegalArgumentException("key不能为空");
            }
        }
    }

    /**
     * 获得一天的最后一刻 23:59:999
     */
    private static Date getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 批量获取
     */
    public List<String> getStrs(List<String> strList) {
        if (CollUtil.isEmpty(strList)) {
            return new ArrayList<>();
        }
        return stringRedisTemplate.opsForValue().multiGet(strList);
    }

    /**
     * 取hash里面的数据
     *
     * @param key hash键
     * @return Map
     */
    public Map<String, Object> getHash(String key) {
        checkKeyNotBlank(key);
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取对象列表特殊处理解压的
     *
     * @param key   key
     * @param clazz 类对象
     * @param <T>   T
     * @return 对象列表
     */
    public <T> List<T> getSpecList(String key, Class<T> clazz) {
        return getSpeComplex(key, List.class, clazz);
    }

    /**
     * 特殊处理解压的获取缓存对象
     * 需要传入所有的泛型类对象 例如Result<List<FileInfoBO>> 需要传入 Result.class List.class FileInfoBO.class
     *
     * @param key     缓存键
     * @param classes 类型参数
     * @return 获取的对象
     */
    public <T> T getSpeComplex(String key, Class... classes) {
        checkKeyNotBlank(key);

        String cachedValue = (String) stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(cachedValue)) {
            return null;
        }
        return JSONObject.parseObject(ZipUtil.unGZip(cachedValue), buildType(classes));
    }
}
