package com.bdu.laborder.common.core.result;


import java.io.Serializable;

/**
 * @Author Qi
 * @data 2020/12/11 23:06
 */
public class Result implements Serializable {
    //返回code
    private String resultCode;
    //返回描述
    private String message;
    //返回数据
    private Object data ;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



}
