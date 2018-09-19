package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.sun.corba.se.spi.activation.Server;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/9/12.
 */

public interface UserService {
    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    ServerResponse<User> login(String userName,String password);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 校验用户名或邮箱
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str,String type);

    /**
     * 获取当前登陆用户信息
     * @param session
     * @return
     */
    ServerResponse<User> getUserInfo(HttpSession session);

    /**
     * 退出登陆
     * @param session
     * @return
     */
    ServerResponse<String> logout(HttpSession session);

    /**
     * 登录状态更新个人信息
     * @param email
     * @param phone
     * @param question
     * @param answer
     * @param session
     * @return
     */
    ServerResponse<String> updateUserInfo(String email,String phone,String question,String answer,HttpSession session);

    /**
     * 忘记密码
     * @param userName
     * @return
     */
    ServerResponse<String> forgetGetQuestion(String userName);

    /**
     * 提交问题答案
     * @param userName
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> forgetCheckAnswer(String userName,String question,String answer);

    /**
     * 忘记密码的重设密码
     * @param userName
     * @param newPassword
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetRestPassword(String userName,String newPassword,String forgetToken);

    /**
     * 登陆状态下重置密码
     * @param oldPasword
     * @param newPassword
     * @param session
     * @return
     */
    ServerResponse<String> restPassword(String oldPassword,String newPassword,HttpSession session);

    /**
     * 登录状态更新个人信息
     * @param session
     * @param user
     * @return
     */
    ServerResponse<User> updateInformation(HttpSession session,User user);
}
