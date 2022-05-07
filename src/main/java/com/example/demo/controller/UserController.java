package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.Result;
import com.example.demo.common.Results;
import com.example.demo.entity.User;
import com.example.demo.extdto.UserReqDTO;
import com.example.demo.extdto.UserRespDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ExecutorService extDataSyncExecutor;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    /**
     * 直接调用原生http接口
     *
     * @param id
     * @return
     */
    @GetMapping("get")
    public String deductStock(@RequestParam("productId") Long id) {
        User byId = userService.getById(id);
        //byId.setUpdateTime(new Date());
        userMapper.updateById(byId);
        System.out.println(JSON.toJSON(byId));
        return "sucess";
    }

    @GetMapping("save")
    public String deductStock() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> users = new ArrayList<>();
        try {

            users = userMapper.selectList(queryWrapper);
        } catch (Exception e) {
            log.error("异常" + e);
        }
       /* for (User user : users) {
            user.setId(null);
        }
        System.out.println(Thread.currentThread().getName());
        userService.saveOrUpdateBatch(users);*/
        List<User> finalUsers = users;
        extDataSyncExecutor.execute(() -> executeTask(finalUsers));
        return "sucess";
    }

    private void executeTask(List<User> list) {

        try {
            System.out.println("线程namae=" + Thread.currentThread().getName());
            userService.saveOrUpdateBatch(list);
        } catch (Exception ex) {
            log.error(" [Error] 基础数据同步耗材数据 -> ElasticSearch 执行失败" , ex);
        } finally {
            // copyList = null;
        }
    }

    @PostMapping("saveBath")
    public String saveBath(@RequestBody List<User> list) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.clear();
        if (CollectionUtils.isEmpty(list)) {
            return "data is empty";
        }
        redisTemplate.opsForValue().set("qq" , 11);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for (int i = 0; i < 1005; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("name" + i);
            list.add(user);
        }
        if (list != null && list.size() > 0) {
            userService.saveOrUpdateBatch(list);
        }
        return "sucess";
    }
    //get 获取用户列表

    @PostMapping("test")
    public String saveBath1(@RequestBody List<Integer> integers) {

        User load = userMapper.load(9121);
        System.out.println(load);
        Collections.sort(integers);

        System.out.println(integers.get(0));
        System.out.println(integers.get(integers.size() - 1));

        // 55408
        // 4321457
        return "sucess";
    }


    @PostMapping("/selectPage")
    @ApiOperation(value = "用户分页查询" , notes = "用户分页查询")
    public Result<IPage<UserRespDTO>> selectPage(@RequestBody UserReqDTO reqDTO) {
        return Results.success(userService.selectPage(reqDTO));
    }
}
