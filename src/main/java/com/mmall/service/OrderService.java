package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * Created by Administrator on 2019/1/5.
 */
public interface OrderService {

     ServerResponse pay(Long orderNo,Integer userId,String path);

     ServerResponse aliCallback(Map<String,String> params);

     ServerResponse<Boolean> queryOrderPayStatus(Integer userId,Long orderNo);
}
