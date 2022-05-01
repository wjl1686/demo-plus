package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.AdmdvsInfoDO;
import com.example.demo.mapper.AdmdvMapper;
import com.example.demo.service.AdmdvService;
import org.springframework.stereotype.Service;

@Service
public class AdmdvServiceImpl extends ServiceImpl<AdmdvMapper, AdmdvsInfoDO> implements AdmdvService {
}
