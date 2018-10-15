package com.yanxuemeng.domain;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 */
public class ResultInfo implements Serializable {
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    private int flag;//状态标志
    private Object data;//后端返回结果数据对象
    private String errorMsg;//发生异常的错误消息

    public ResultInfo() {
    }

    public ResultInfo(int flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ResultInfo2{" +
                "flag=" + flag +
                ", data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
