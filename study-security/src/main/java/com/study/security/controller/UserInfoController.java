package com.study.security.controller;

import com.study.security.dto.UserInfoDto;
import com.study.security.service.UserInfoService;
import common.api.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller()
@Api(tags="UserInfoController",value ="用户管理")
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/login")
    @ApiOperation(value = "登录以后返回token")
    public ResponseResult login(@Validated @RequestBody UserInfoDto userInfoDto){
        String token=userInfoService.login(userInfoDto.getUserName(),
                userInfoDto.getPassword());
        if(StringUtils.isEmpty(token)){
            return ResponseResult.validateFailed();
        }
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        ResponseResult<Map<String,String>> result
                =ResponseResult.success(tokenMap);
        return result;
    }
}
