package com.example.demo.common.extdto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * user
 *
 * @author wujl2
 * @since 2021-03-10
 */
@Data
@ApiModel(value = "user对象", description = "user")
public class UserReqDTO extends Page {

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

}
