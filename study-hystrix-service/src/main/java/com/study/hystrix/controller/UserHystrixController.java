package com.study.hystrix.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.netflix.hystrix.HystrixCommandProperties;
import com.study.hystrix.service.user.UserService;
import common.api.ResponseResult;
import common.pojo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/11 11:46
 */
@RestController
@RequestMapping(value = "/user/")
public class UserHystrixController {
    @Autowired
    private UserService userService;

    @GetMapping("get/{id}")
    public ResponseResult<UserDto> get(
            @PathVariable(value = "id")Long id){
        return ResponseResult.success(userService.get(id));
    }

    @GetMapping("get/cache/{id}")
    public ResponseResult<UserDto> getCache(
            @PathVariable(value = "id")Long id){
        UserDto dto=userService.getCache(id);
        dto=userService.getCache(id);
        dto=userService.getCache(id);
        return ResponseResult.success(dto);
    }

    @GetMapping("get/cache/remove/{id}")
    public ResponseResult<UserDto> removeCache(
            @PathVariable(value = "id")Long id){
        UserDto dto=userService.getCache(id);
        dto=userService.removeCache(id);
        dto=userService.getCache(id);
        return ResponseResult.success(dto);
    }

    @GetMapping("get/all")
    public ResponseResult<List<UserDto>> getAll() throws ExecutionException, InterruptedException {
        List<UserDto> dtoList=new ArrayList<>();
        Future<UserDto> future1=userService.getFuture(1L);
        Future<UserDto> future2=userService.getFuture(2L);
        dtoList.add(future1.get());
        dtoList.add(future2.get());
        ThreadUtil.safeSleep(200);
        Future<UserDto> future3= userService.getFuture(3L);
        dtoList.add(future3.get());
        //HystrixCommandProperties
        return ResponseResult.success(dtoList);
    }
}
