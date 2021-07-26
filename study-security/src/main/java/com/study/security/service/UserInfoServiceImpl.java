package com.study.security.service;

import com.study.security.domain.UserInfo;
import com.study.security.domain.UserInfoDetails;
import com.study.security.util.JwtTokenUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserInfo userInfo=getByUsername(userName);
        return new UserInfoDetails(userInfo);
    }

    @Override
    public UserInfo getByUsername(String userName) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("lp");
        userInfo.setPassword("123456");
        userInfo.setStatus(1);
        return userInfo;
    }

    @Override
    public String login(String userName, String password) {
        String token= Strings.EMPTY;
        UserDetails userDetails=loadUserByUsername(userName);
        if(!password.equals(userDetails.getPassword())){
            //throw new Exception("密码不正确");
        }

        if(userDetails.isEnabled()){
            //throw new Exception("账号已被禁用");
        }
        UsernamePasswordAuthenticationToken passwordAuthenticationToken
                =new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        token=jwtTokenUtil.generateToken(userDetails);
        return token;
    }
}
