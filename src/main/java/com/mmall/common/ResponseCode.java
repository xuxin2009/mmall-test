package com.mmall.common;

/**
 * Created by Administrator on 2018/9/11.
 */
public enum  ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL ARGUMENT");


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
