package com.study.feign.client.fallback;

import com.study.feign.client.UserService;
import common.api.ResponseResult;
import common.pojo.dto.UserDto;
import common.pojo.info.UserInfo;
import org.springframework.stereotype.Component;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/16 19:13
 */
@Component
public class UserFallbackService implements UserService {
    @Override
    public ResponseResult<String> create(UserInfo info) {
        return ResponseResult.success("create:服务降级处理");
    }

    @Override
    public ResponseResult<UserDto> get(Long id) {
        UserDto dto=new UserDto();
        dto.setUsername("-1");
        return ResponseResult.success(dto);
    }
}
