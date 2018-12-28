package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.CartService;
import com.mmall.utils.BigdecimalUtil;
import com.mmall.utils.PropertiesUtil;
import com.mmall.vo.CartProductVO;
import com.mmall.vo.CartVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/12/20.
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse add(Integer userId, Integer count, Integer productId) {

        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart == null)
        {
            //这个产品不在购物车里面，需要增加一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.cart.CHECKED);
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartMapper.insert(cartItem);
        }else
        {//这个产品已经存在购物车里面，只需要把数量相加
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        CartVO cartVO = this.getCartVOLimit(userId);

        return ServerResponse.createBySuccess(cartVO);
    }

    private CartVO getCartVOLimit(Integer userId)
    {
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVO> cartProductVOList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(cartList))
        {
            for(Cart cartItem: cartList)
            {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null)
                {
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity())
                    {
                        cartProductVO.setLimitQuantity(Const.cart.LIMIT_NUM_SUCCESS);
                    }else
                    {
                        buyLimitCount = product.getStock();
                        cartProductVO.setLimitQuantity(Const.cart.LIMIT_NUM_FAIL);
                        //更新有效库存
                        Cart cart4Quantity = new Cart();
                        cart4Quantity.setId(cartItem.getId());
                        cart4Quantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cart4Quantity);
                    }

                    cartProductVO.setQuantity(buyLimitCount);
                    //计算某个产品的总价
                    cartProductVO.setProductTotalPrice(BigdecimalUtil.mul(product.getPrice().doubleValue(),buyLimitCount));
                    cartProductVO.setProductChecked(cartItem.getChecked());
                }
                if(cartItem.getChecked() == Const.cart.CHECKED)
                {//如果已经勾选则增加到总的购物车总价中
                    cartTotalPrice = BigdecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());
                }
                cartProductVOList.add(cartProductVO);
            }
        }
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setAllChecked(getAllCheckedStatus(userId));
        cartVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return  cartVO;
    }

    private boolean getAllCheckedStatus(Integer userId)
    {
        if(userId == null)
        {
            return  false;
        }
        return cartMapper.selectAllCheckedStatusByUserId(userId) == 0;
    }



}
