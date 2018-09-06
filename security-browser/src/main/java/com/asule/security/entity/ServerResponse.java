package com.asule.security.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServerResponse<T> implements Serializable{

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ServerResponse(int code) {
        this.code = code;
    }

    public ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ServerResponse(int code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data=data;
    }

    public static ServerResponse createSuccess(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }

    public static ServerResponse createSuccessByMsg(String msg){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static<T> ServerResponse<T> createSuccessByData(String msg,T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static ServerResponse createError(){
        return new ServerResponse(ResponseCode.ERROR.getCode());
    }

    public static ServerResponse createErrorByMsg(String msg){
        return new ServerResponse(ResponseCode.ERROR.getCode(),msg);
    }

    public static<T> ServerResponse<T> createErrorByData(String msg,T data){
        return new ServerResponse(ResponseCode.ERROR.getCode(),msg,data);
    }


    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }


}
