package com.example.demo.common.extdto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * user对象
 *
 * @author wujl2
 * @since 2022-03-10
 */
@Data
@ApiModel(value = "user对象", description = "user对象")
public class UserRespDTO {

    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * age
     */
    @ApiModelProperty(value = "age")
    private Integer age;

    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

}
