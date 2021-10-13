package com.study.service.user.impl;

import common.pojo.dto.UserDto;
import common.pojo.info.UserInfo;
import com.study.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/8 18:25
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void create(UserInfo info) {

    }

    @Override
    public UserDto get(Long id) {
        UserDto dto=new UserDto();
        dto.setUsername(id.toString());
        return dto;
    }

    @Override
    public List<UserDto> getByIdList(List<Long> idList) {
        return idList.stream().map(item->{
            UserDto dto=new UserDto();
            dto.setUsername(item.toString());
            return dto;
        }).collect(Collectors.toList());
    }
}
