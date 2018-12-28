package com.mmall.service;

import com.mmall.common.ServerResponse;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/12/20.
 */
public interface CartService {

    ServerResponse add(Integer userId, Integer count, Integer productId);
}
