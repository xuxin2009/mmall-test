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
}
