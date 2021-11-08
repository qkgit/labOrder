package com.bdu.laborder.common.core.result;


import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.exception.BaseErrorInfoInterface;
import com.alibaba.fastjson.JSONObject;


/**返回码信息帮助类
 * @Author Qi
 * @data 2020/12/11 23:08
 */
public class ResultGenerator {

    /** 返回消息代码 code 和 message
     * @param bussinessCode  返回码
     * @return
     */
    public static Result returnCodeMessage(BussinessCode bussinessCode){
        return returnCodeMessage(bussinessCode,null);
    }

    /** 返回消息 code message 和 data
     * @param bussinessCode  返回码
     * @param data       返回数据
     * @return
     */
    public static Result returnCodeMessage(BussinessCode bussinessCode, Object data){
        Result result = new Result();
        result.setResultCode(bussinessCode.getCode());
        result.setMessage(bussinessCode.getMsg());
        result.setData(data);
        return result;
    }

    /**
     *  返回成功消息
     * @return
     */
    public static Result success() {
        return returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
    }

    /**
     *  失败
     * @param errorInfo
     * @return
     */
    public static Result error (BaseErrorInfoInterface errorInfo){
        Result result = new Result();
        result.setResultCode(errorInfo.getCode());
        result.setMessage(errorInfo.getMsg());
        result.setData(null);
        return result;
    }

    /**
     *  失败
     * @param code
     * @param message
     * @return
     */
    public static Result error(String code, String message){
        Result result = new Result();
        result.setResultCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     *  失败
     * @param message
     * @return
     */
    public static Result error( String message){
        Result result = new Result();
        result.setResultCode("999");
        result.setMessage(message);
        result.setData(null);
        return result;
    }



    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
