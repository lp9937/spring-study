package com.study.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;


@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoDto {
    @NotEmpty
    @ApiModelProperty(value ="用户名",required=true)
    private String userName;
    @NotEmpty
    @ApiModelProperty(value="密码",required=true)
    private String password;
}
