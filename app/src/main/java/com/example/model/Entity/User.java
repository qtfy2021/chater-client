package com.example.model.Entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String userName;

    private String headImg_url;
    private String password;
    private int sex;
    private String email;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNameString() {
        return userName;
    }

    public String getUserIdString() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImg_url() {
        return headImg_url;
    }

    public void setHeadImg_url(String headImg_url) {
        this.headImg_url = headImg_url;
    }

}
