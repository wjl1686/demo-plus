package com.example.demo.common.config;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * mybatis-plus审计字段自填充设置
 *
 * @author zhangjun
 * @date 2019/7/3 16:09
 */
@Component
public class MybatisPlusObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();

        // 填充修改时间
        Object updtTime = getFieldValByName("updateTime", metaObject);
        if (ObjectUtil.isEmpty(updtTime)) {
            setFieldValByName("updateTime", now, metaObject);
        }
        // 填充创建时间
        Object crteTime = getFieldValByName("createTime", metaObject);
        if (ObjectUtil.isEmpty(crteTime)) {
            setFieldValByName("createTime", now, metaObject);
        }
        /*// 填充经办人信息
        Object crter = getFieldValByName("crter", metaObject);
        Object crterId = getFieldValByName("crterId", metaObject);


        Object opter = getFieldValByName("opter", metaObject);
        Object opterId = getFieldValByName("opterId", metaObject);
        if (ObjectUtil.isEmpty(opter)) {
            setFieldValByName("opter", userContextManager.getUserContext().map(UserContext::getUserId).orElse(BaseConstant.NULLVAL), metaObject);
            setFieldValByName("optins", userContextManager.getUserContext().map(UserContext::getOrgId).orElse(BaseConstant.NULLVAL), metaObject);
        }else if (ObjectUtil.isNotEmpty(opter)  && ObjectUtil.isEmpty(opterId)) {
            setFieldValByName("opterId", userContextManager.getUserContext().map(UserContext::getUscc).orElse(null), metaObject);
            setFieldValByName("optinsNo", userContextManager.getUserContext().map(UserContext::getOrgId).orElse(null), metaObject);
        }
        if(ObjectUtil.isEmpty(opter)  && ObjectUtil.isEmpty(opterId)){
            setFieldValByName("opterName", userContextManager.getUserContext().map(UserContext::getUserName).orElse(null), metaObject);
        }
        //删除标识
        Object invdFlag = getFieldValByName("invdFlag", metaObject);
        if (Objects.isNull(invdFlag)) {
            setFieldValByName("invdFlag", AuthConstant.INVD_FLAG, metaObject);
        }*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时填充的字段
        Date now = new Date();

        setFieldValByName("updateTime", now, metaObject);

    }

}