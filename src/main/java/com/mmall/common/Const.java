package com.mmall.common;

/**
 * Created by Administrator on 2018/9/12.
 */
public class Const {
    public static final String CURRENT_USER = "current_user";
    public static  final String EMAIL = "email";
    public static final  String USERNAME = "userName";

    public interface Role
    {
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//admin
    }
}
