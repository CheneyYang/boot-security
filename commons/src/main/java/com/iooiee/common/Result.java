package com.iooiee.common;

import java.io.Serializable;

/**
 * Description:
 *
 * @Author: Yanggc
 * DateTime: 10/22 13:52
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;

    private String msg;

    private T data ;


    Result() {
    }

    private Result(T data) {
        this.code = 200;
        this.msg = "SUCCESS";
        this.data = data;
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> Result<T> success() {
        Result result = new Result<T>();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        return result;
    }

    public static <T> Result<T> failure(ResultCodeEnum resultCodeEnum) {
        Result result = new Result();
        result.setResultCode(resultCodeEnum);
        return result;
    }

    //自定义相应失败值
    public static <T> Result<T> failure(String failMsg) {
        Result result = new Result();
        result.setMsg(failMsg);
        result.setCode(201);
        return result;
    }


    public void setResultCode(ResultCodeEnum code) {
        this.code = code.code();
        this.msg = code.message();
    }

    // 自定义返回数据
    public Result<T> data(T data) {
        this.setData(data);
        return this;
    }

    // 自定义状态信息
    public Result msg(String message) {
        this.setMsg(message);
        return this;
    }

    // 自定义状态码
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }





    /**
     *     getter setter
     */
    public Integer getCode() {
        return code;
    }

    private void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }


    public T getData() {
        return data;
    }


    private void setData(T data) {
        this.data = data;
    }





}
