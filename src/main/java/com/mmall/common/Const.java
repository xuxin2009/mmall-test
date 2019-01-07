package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by Administrator on 2018/9/12.
 */
public class Const {
    public static final String CURRENT_USER = "current_user";
    public static  final String EMAIL = "email";
    public static final  String USERNAME = "userName";

    public interface productListOrderBy{
        Set<String> PRICE_DESC_ASC = Sets.newHashSet("price_desc","price_asc");
    }
    public interface Role
    {
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//admin
    }
    public interface cart
    {
        int CHECKED = 1;//表示加入购物车选中状态
        int UN_CHECKED = 0;//为选中状态

        String LIMIT_NUM_FAIL = "limit_num_fail";
        String LIMIT_NUM_SUCCESS = "limit_num_success";
    }
    public enum ProductStatus
    {
        ON_SALE(1,"在线");
        private String value;
        private int code;

        ProductStatus( int code,String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum
    {
        CANCEL(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已支付"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");

        private String value;
        private int code;
        OrderStatusEnum(int code,String value)
        {
            this.code = code;
            this.value = value;
        }
        public String getValue()
        {
            return  value;
        }
        public int getCode()
        {
            return code;
        }
    }

    public interface AlipayCallback
    {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatformEnum
    {
        ALIPAY(1,"支付宝"),
        WECHATPAY(2,"微信支付");

        private  String value;
        private int code;

        PayPlatformEnum(int code,String value)
        {
            this.code = code;
            this.value = value;
        }
        public int  getCode()
        {
            return code;
        }
        public String getValue()
        {
            return  value;
        }
    }
}
