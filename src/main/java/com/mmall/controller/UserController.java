package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/9/11.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 1.用户登陆
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.GET)
    public ServerResponse<User> login(String userName, String password, HttpSession session)
    {
        ServerResponse response = userService.login(userName,password);
        if(response.isSuccess())
        {
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 2.用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public ServerResponse<String> register(User user)
    {
        return userService.register(user);
    }

    /**
     * 3.检查用户名是否有效
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "/check_valid.do", method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str , String type)
    {
        return userService.checkValid(str,type);
    }

    /**
     * 4.获取登录用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpSession session)
    {
        return userService.getUserInfo(session);
    }

    /**
     * 5.退出登陆
     * @return
     */
    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session)
    {
        return userService.logout(session);
    }

    /**
     * 6.登录状态更新个人信息
     * @param email
     * @param phone
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "/update_Information.do",method = RequestMethod.GET)
    public ServerResponse<String> updateUserInfo(String email,String phone,String question,String answer,HttpSession session)
    {
        return userService.updateUserInfo(email,phone,question,answer,session);
    }

    /**
     * 忘记密码
     * @param userName
     * @return
     */
    @RequestMapping(value = "/forget_get_question.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetGetQuestion(String userName)
    {
        return userService.forgetGetQuestion(userName);
    }

    @RequestMapping(value = "/forget_check_answer.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetCheckAnswer(String userName,String question,String answer)
    {
        return userService.forgetCheckAnswer(userName, question, answer);
    }

    /**
     * 忘记密码的重设密码
     * @param userName
     * @param newPassword
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "/forget_rest_password.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetRestPassword(String userName,String newPassword,String forgetToken)
    {
        return userService.forgetRestPassword(userName, newPassword, forgetToken);
    }

    /**
     * 登陆状态下重置密码
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @RequestMapping(value = "/rest_password.do",method = RequestMethod.GET)
    public ServerResponse<String> resetPassword(String oldPassword,String newPassword,HttpSession session)
    {
        return userService.restPassword(oldPassword, newPassword, session);
    }

    /**
     * 登录状态更新个人信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/update_information.do")
    public ServerResponse<User> updateInformation(HttpSession session,User user)
    {
        ServerResponse response = userService.updateInformation(session, user);
        if(response.isSuccess())
        {
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }


}
