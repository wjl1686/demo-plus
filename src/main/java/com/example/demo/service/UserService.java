package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import com.example.demo.common.extdto.UserReqDTO;
import com.example.demo.common.extdto.UserRespDTO;


public interface UserService extends IService<User> {

    IPage<UserRespDTO> selectPage(UserReqDTO reqDTO);
}
