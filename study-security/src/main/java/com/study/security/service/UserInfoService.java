package com.study.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import com.study.security.domain.UserInfo;
public interface UserInfoService {
    UserDetails loadUserByUsername(String userName);
    UserInfo getByUsername(String userName);
    String login(String userName,String password);
}
