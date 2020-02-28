package com.fan.comment.demo01.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class UserInfo {
    private Long userId;
    private String userName;
    private Date date;
}
