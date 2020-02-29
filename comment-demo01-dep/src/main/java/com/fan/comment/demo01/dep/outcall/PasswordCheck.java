package com.fan.comment.demo01.dep.outcall;

import com.fan.comment.demo01.dep.model.UserInfo;

public class PasswordCheck {

    public boolean check(UserInfo userInfo, String password){

        /* ==========
          ^message: 检查密码初始化
        ========== */
        boolean result = false;

        /* ==========
          ^message: 外呼校验密码
          ^method:outCall
          ^args:UserInfo, String
        ========== */
        result = outCall(userInfo, password);

        /* ==========
          ^message: 返回密码校验结果
        ========== */
        return result;

    }

    private boolean outCall(UserInfo userInfo, String passowrd){
        /* ==========
          ^message: 组装外呼报文
        ========== */

        /* ==========
          ^message: 外呼密码组件
          ^outcall: 外呼XX组件，交易码123456789
        ========== */

        /* ==========
          ^message: 整理外呼并返回
        ========== */

        return true;
    }
}
