package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    @ResultType(User.class)
    @Select("SELECT mcs.id AS id, " +
            "mcs.name AS name, " +
            "mcs.age AS age," +
            " FROM user mcs")
    void userAllDataStreamQuery(ResultHandler<User> handler);

}
