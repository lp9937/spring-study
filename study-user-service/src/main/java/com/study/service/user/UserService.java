package com.study.service.user;

import common.pojo.dto.UserDto;
import common.pojo.info.UserInfo;

import java.util.List;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/8 18:24
 */
public interface UserService {
    void create(UserInfo info);
    UserDto get(Long id);
    List<UserDto> getByIdList(List<Long> idList);
}
