package com.hh.attendance.commons;

import java.io.Serializable;

/**
 * Copyright (C),2011-2019,杭州湖畔网络科技有限公司
 *
 * @description: 实体返回类
 * @FileName: customerdelivery
 * @Author: anthony
 * @Date: 2020/04/12 20:46
 **/
public class ResultBody implements Serializable {
    private static final long serialVersionUID = 2114765287796005521L;

    private boolean success;
    private Object data;
    private Integer code;
    private String msg;

    public ResultBody(boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public ResultBody(boolean success, Object data, int code) {
        this.success = success;
        this.data = data;
        this.code = code;
    }

    public ResultBody(boolean success, Object data, Integer code, String msg) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ResultBody() {
    }

    public static ResultBody success(Object data) {
        return new ResultBody(true, data, 200);
    }

    public static ResultBody success(Object data, String msg) {
        return new ResultBody(true, data, 200, msg);
    }

    public static ResultBody success(String msg) {
        return new ResultBody(true, 200, msg);
    }

    public static ResultBody failed(int errorCode, String errorMsg) {
        return new ResultBody(false, errorCode, errorMsg);
    }

    public static ResultBody failed(String errorMsg) {
        return new ResultBody(false, 500, errorMsg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
