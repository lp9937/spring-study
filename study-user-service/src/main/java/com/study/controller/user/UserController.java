package com.study.controller.user;

import common.pojo.info.UserInfo;
import com.study.service.user.UserService;
import common.api.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
