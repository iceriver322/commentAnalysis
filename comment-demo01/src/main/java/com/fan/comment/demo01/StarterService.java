package com.fan.comment.demo01;

import com.fan.comment.demo01.dep.dao.UserInfoDao;
import com.fan.comment.demo01.dep.model.UserInfo;
import com.fan.comment.demo01.dep.outcall.PasswordCheck;

import java.util.Date;
/*
    final String MESSAGE="message";
    final String PACKAGENAME="packageName";
    final String CLASSNAME="className";
    final String METHOD="method";
    final String ARGS="args";
 */
public class StarterService {

    private UserInfoDao userInfoDao;
    private PasswordCheck passwordCheck;

    public UserInfo checkUser(Long userId, String password){
        /* ==========
          ^message:  接收用户ID、密码，合法性检查
        ========== */
        checkInput(userId, password);

        /* ==========
          ^message:  业务处理
        ========== */

        /* ==========
          ^message: 获取用户信息
          ^method:getUserInfo
          ^args:Long
          ^className:UserInfoDao
          ^packageName:com.fan.comment.demo01.dep.dao
        ========== */
        UserInfo userInfo = userInfoDao.getUserInfo(userId);

        /* ==========
          ^message: 接收用户ID、密码
          ^method:check
          ^args:UserInfo, String
          ^className:PasswordCheck
          ^packageName:com.fan.comment.demo01.dep.outcall
        ========== */
        passwordCheck.check(userInfo, password);

        /* ==========
          ^message: 获取时间
          ^method:getDate
        ========== */
        userInfo.setDate(getDate());
        /* ==========
          ^message: 返回用户信息
        ========== */
        return userInfo;
    }

    private Date getDate(){
//        /* ==========
//          ^message: 设置id
//        ========== */
//        long i= 0l;

        /* ==========
          ^message: 获取时区函数，转化普通注释
          ^method:getLocal
          ^args:Long
        ========== */

        /* ==========
          ^message: 返回时间
        ========== */
        return new Date();
    }


    public void checkInput(Long userId, String passwd){

    }
}
