package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import com.example.demo.extdto.UserReqDTO;
import com.example.demo.extdto.UserRespDTO;


public interface UserService extends IService<User> {

    IPage<UserRespDTO> selectPage(UserReqDTO reqDTO);
}
