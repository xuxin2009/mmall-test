package com.mmall.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/11.
 */

public class ServerResponse<T> implements Serializable {

    private int status;
    private String message;
    private T  data;

    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status,T data)
    {
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status,String message,T data)
    {
        this.status = status;
        this.message = message;
        this.data = data;
    }
  private ServerResponse(int status,String message)
  {
      this.status = status;
      this.message = message;
  }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }


    public boolean isSuccess()
    {
        return  this.status == ResponseCode.SUCCESS.getCode();
    }
    
}
