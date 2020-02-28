package com.fan.comment.demo01;

import com.fan.comment.demo01.model.UserInfo;

import java.util.Date;
/*
    StarterService
    final String MESSAGE="message";
    final String PACKAGENAME="packageName";
    final String CLASSNAME="className";
    final String METHOD="method";
    final String ARGS="args";
 */
public class StarterService {

    //hello

    int i = 0;
    public UserInfo getUser(Long userId, Date date){
        /* ==========
          ^message: 开始 step1
        ========== */
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(getName(userId));
        /* ==========
          ^message: 获取时间
          ^method:getDate
        ========== */
        userInfo.setDate(getDate());
        /* ==========
          ^message: 返回用户信息 step3
        ========== */
        return userInfo;
    }

    private Date getDate(){
        /* ==========
          ^message: 设置id
        ========== */
        long i= 0l;

        /* ==========
          ^message: 获取用户信息
          ^method:getName
          ^args:Long
        ========== */
        getName(i);

        /* ==========
          ^message: 返回时间
        ========== */
        return new Date();
    }

    //getName
    private String getName(Long userId){
        /* ==========
          ^message: 获取用户名
        ========== */
        return "fan-"+userId;

    }
}
