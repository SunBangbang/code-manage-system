package com.codemanage.common.entity;

import com.codemanage.common.constants.Constants;

import java.io.Serializable;

/**
 * 返回结果
 * @author hyh
 * @since  2022-05-30
 **/
public class BaseResult implements Serializable {

    private int code = Constants.CODE_SUCCESS;

    private String msg;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static BaseResult successMsg(String msg) {
        return BaseResult.successMsg(msg, null);
    }
    public static BaseResult failedMsg(String msg) {
        return BaseResult.failedMsg(msg, null);
    }

    public static BaseResult successMsg(String msg, Object object) {
        return BaseResult.returnBaseResult(Constants.CODE_SUCCESS, msg, object);
    }

    public static BaseResult failedMsg(String msg, Object object) {
        return BaseResult.returnBaseResult(Constants.CODE_FAILED, msg, object);
    }

    private static BaseResult returnBaseResult(int code, String msg, Object object) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setMsg(msg);
        baseResult.setData(object);
        return baseResult;
    }
}
