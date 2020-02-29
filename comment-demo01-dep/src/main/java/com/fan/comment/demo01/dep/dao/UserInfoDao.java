package com.fan.comment.demo01.dep.dao;

import com.fan.comment.demo01.dep.model.UserInfo;

public class UserInfoDao {

    public UserInfo getUserInfo(Long userId){

        /* ==========
          ^message: 查询用户初始化
        ========== */
        UserInfo userInfo = null;

        /* ==========
          ^message: 从db获取用户信息
          ^method:getFromDb
          ^args:Long
        ========== */
        userInfo = getFromDb(userId);

        /* ==========
          ^message: 查询用户结束，返回
        ========== */
        return userInfo;
    }

    private UserInfo getFromDb(Long userId){

        /* ==========
          ^message: 根据用户id查询数据库
          ^db: 数据表User_INFO
        ========== */
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName("fan");

        /* ==========
          ^message: 循环测试
          ^method:getUserInfo
          ^args:Long
        ========== */
        return userInfo;
    }




}
