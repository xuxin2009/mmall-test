package com.mmall.common;

/**
 * Created by Administrator on 2018/9/11.
 */
public enum  ResponseCode {

    SUCCESS(1,"success"),
    ERROR(1,"error"),
    NEED_LOGIN(10,"need login"),
    ILLEGAL_ARGUMENT(2,"illegal argument");


    private final String desc;
    private final int code;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
