package com.study.config.controller;

import common.api.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/20 15:55
 */
@RestController
@RequestMapping(value = "/config")
@RefreshScope
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/get")
    public ResponseResult<String> getConfigInfo(){
        return ResponseResult.success(configInfo);
    }
}
