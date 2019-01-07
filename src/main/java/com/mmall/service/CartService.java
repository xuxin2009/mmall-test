package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVO;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/12/20.
 */
public interface CartService {

    ServerResponse<CartVO> add(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVO> update(Integer userId,Integer count,Integer productId);

    ServerResponse<CartVO> deleteProduct(Integer userId,String products);

    ServerResponse<CartVO> listCart(Integer userId);

    ServerResponse<CartVO> selectAllOrUnSelect(Integer userId,Integer productId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
