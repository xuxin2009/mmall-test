package com.mmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/9/11.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/login.do",method = RequestMethod.GET)
    public void login()
    {
        System.out.print("i cam in");
    }
}
