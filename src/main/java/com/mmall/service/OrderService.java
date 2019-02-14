package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderProductVO;
import com.mmall.vo.OrderVO;

import java.util.Map;

/**
 * Created by Administrator on 2019/1/5.
 */
public interface OrderService {

     ServerResponse pay(Long orderNo,Integer userId,String path);

     ServerResponse aliCallback(Map<String,String> params);

     ServerResponse<Boolean> queryOrderPayStatus(Integer userId,Long orderNo);

     ServerResponse<Object> create(Integer userId,Integer shipping);

     ServerResponse<OrderProductVO> getOrderCartProduct(Integer userId);

     ServerResponse<String>  cancel(Integer userId,Long orderNo);

     ServerResponse<OrderVO> detail(Integer userId,Long orderNo);

     ServerResponse<PageInfo> getOrderList(Integer userId,Integer pageNum,Integer pageSize);

     /*******************************backend********************************/
     ServerResponse<PageInfo> manageList(Integer pageNum,Integer pageSize);

     ServerResponse<OrderVO> manageDetail(Long orderNo);

     ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);

     ServerResponse<String> manageSendGoods(Long orderNo);



}
