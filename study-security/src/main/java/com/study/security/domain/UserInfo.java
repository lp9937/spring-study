package com.study.security.domain;

public class UserInfo {
    private String userName;
    private String password;
    private Integer status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status=status;
    }
}
