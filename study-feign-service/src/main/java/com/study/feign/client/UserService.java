package com.study.feign.client;

import com.study.feign.client.fallback.UserFallbackService;
import common.api.ResponseResult;
import common.pojo.dto.UserDto;
import common.pojo.info.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/16 11:19
 */
@FeignClient(value = "user-service",fallback = UserFallbackService.class)
public interface UserService {
    @PostMapping(value="/user/create")
    ResponseResult<String> create(@RequestBody UserInfo info);

    @GetMapping(value = "/user/get/{id}")
    ResponseResult<UserDto> get(@PathVariable(value="id")Long id);
}
