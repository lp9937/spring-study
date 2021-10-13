package com.study.hystrix.service.user;

import common.pojo.dto.UserDto;

import java.util.concurrent.Future;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/11 11:51
 */
public interface UserService {
    UserDto get(Long id);

    UserDto getCache(Long id);

    UserDto removeCache(Long id);

    Future<UserDto> getFuture(Long id);
}
