package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.extdto.UserReqDTO;
import com.example.demo.extdto.UserRespDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public IPage<UserRespDTO> selectPage(UserReqDTO reqDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (reqDTO.getAge() != null) {
            queryWrapper.lambda().like(User::getAge, reqDTO.getAge());
        }
        if (StringUtils.isNotBlank(reqDTO.getName())) {
            queryWrapper.lambda().like(User::getName, reqDTO.getName());
        }
        queryWrapper.lambda().orderByDesc(User::getCreateTime);
        IPage page = baseMapper.selectPage(reqDTO, queryWrapper);
        return page.convert(item -> BeanUtil.copyProperties(item, UserRespDTO.class));
    }

}
