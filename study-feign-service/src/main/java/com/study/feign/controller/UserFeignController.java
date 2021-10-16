package com.study.feign.controller;

import com.study.feign.client.UserService;
import common.api.ResponseResult;
import common.pojo.dto.UserDto;
import common.pojo.info.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/16 11:24
 */
@RestController
@RequestMapping(value = "/user/")
public class UserFeignController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "create")
    public ResponseResult<String> create(@RequestBody UserInfo info){
        return userService.create(info);
    }
    @GetMapping(value = "/get/{id}")
    public ResponseResult<UserDto> get(@PathVariable(value="id")Long id){
        return userService.get(id);
    }
}
