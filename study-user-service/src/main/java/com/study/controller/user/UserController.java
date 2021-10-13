package com.study.controller.user;

import common.pojo.dto.UserDto;
import common.pojo.info.UserInfo;
import com.study.service.user.UserService;
import common.api.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/8 18:12
 */
@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "create")
    public ResponseResult<String> create(@RequestBody UserInfo info){
        userService.create(info);
        return ResponseResult.success("操作成功");
    }

    @GetMapping(value = "get/{id}")
    public ResponseResult<UserDto> get(@PathVariable(value = "id")Long id){
        return ResponseResult.success(userService.get(id));
    }

    @GetMapping(value = "get/ids/{idList}")
    public ResponseResult<List<UserDto>> getByIdList(
            @PathVariable(value = "idList")String idStr){
        String[] ids = idStr.split(",");
        List<Long> idList= Arrays.stream(ids)
                .map(item-> Long.valueOf(item))
                .collect(Collectors.toList());
        return ResponseResult.success(userService.getByIdList(idList));
    }
}
