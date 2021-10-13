package com.study.hystrix.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.study.hystrix.service.user.UserService;
import common.api.ResponseResult;
import common.pojo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/11 11:51
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;
    @Override
    @HystrixCommand(fallbackMethod = "getDefault",
            commandKey = "getUserCommand",
            groupKey = "getUserGroup",
            threadPoolKey = "getUserThreadPool",
            ignoreExceptions ={NullPointerException.class} )
    public UserDto get(Long id) {
        if(id==1){
            throw new IndexOutOfBoundsException();
        }else if(id==2){
            throw new NullPointerException();
        }
        ResponseResult<UserDto> responseResult = restTemplate.exchange(userServiceUrl + "/user/get/{1}",
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseResult<UserDto>>(){},id)
                .getBody();
        return responseResult.getData();
    }

    @HystrixCommand(fallbackMethod = "getDefault2",commandKey = "getCache")
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @Override
    public UserDto getCache(Long id) {
        log.info("getCache id:{}",id);
        ResponseResult<UserDto> responseResult= restTemplate.exchange(userServiceUrl + "/user/get/{1}",
                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseResult<UserDto>>() {},id)
                .getBody();
        return responseResult.getData();
    }

    @HystrixCommand
    @CacheRemove(cacheKeyMethod = "getCacheKey",
            commandKey = "getCache")
    @Override
    public UserDto removeCache(Long id) {
        ResponseResult<UserDto> responseResult= restTemplate.exchange(userServiceUrl + "/user/get/{1}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<ResponseResult<UserDto>>() {},id)
                .getBody();
        return responseResult.getData();
    }

    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "100")
    })
    @Override
    public Future<UserDto> getFuture(Long id) {
        return new AsyncResult<UserDto>(){

            @Override
            public UserDto invoke() {
                log.info("getFuture id:{}",id);
                ResponseResult<UserDto> responseResult= restTemplate.exchange(userServiceUrl + "/user/get/{1}",
                                HttpMethod.GET, null, new ParameterizedTypeReference<ResponseResult<UserDto>>() {},id)
                        .getBody();
                return responseResult.getData();
            }
        };
    }

    @HystrixCommand
    public List<UserDto> getUserByIds(List<Long> idList){
        log.info("getUserByIds:{}",idList);
        String idStr = CollUtil.join(idList,",");
        ResponseResult<List<UserDto>> responseResult=restTemplate.exchange(userServiceUrl+"/user/get/ids/{1}",
                HttpMethod.GET,null,new ParameterizedTypeReference<ResponseResult<List<UserDto>>>(){},idStr)
                .getBody();
        return responseResult.getData();
    }

    public UserDto getDefault(@PathVariable Long id){
        UserDto dto=new UserDto();
        dto.setUsername("李四");
        return dto;
    }

    public UserDto getDefault2(@PathVariable Long id){
        UserDto dto=new UserDto();
        dto.setUsername("王五");
        return dto;
    }

    /**
     * 为缓存生成key的方法
     * @param id
     * @return
     */
    public String getCacheKey(Long id){
        return String.valueOf(id);
    }
}
