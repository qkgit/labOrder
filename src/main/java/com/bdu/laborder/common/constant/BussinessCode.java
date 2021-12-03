package com.bdu.laborder.common.constant;

import com.bdu.laborder.exception.BaseErrorInfoInterface;

/**
 *  返回状态码和状态描述
 *  状态码概况:  状态码为4位    分割为2部分 状态码第一位为 模块信息，后面三位为 范围信息
 *  模块信息:    0.通用（如系统）  1.系统管理(角色、资源、用户)等
 *  范围信息:    1.操作成功范围 001-500，2.操作失败范围 501-999
 *  特殊：       200 成功， 999异常
 *
 * @Author Qi
 * @data 2020/12/11 23:07
 */
public enum BussinessCode implements BaseErrorInfoInterface {

    //通用
    RESULT_GLOBAL_SUCCESS ("200","O(∩_∩)O 操作成功！"),
    RESULT_GLOBAL_FAIL("999","/(ㄒoㄒ)/~~ 操作失败！"),
    RESULT_FIELD_NULL("000","必填字段不能为空！"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),

    // 登录模块
    RESULT_LOGIN_NULL("1501","用户名密码不能为空！"),
    RESULT_LOGIN_FAIL("1502","登录失败！用户名或密码错误"),
    RESULT_TOKEN_OVER("1503","登录过期，请重新登录！"),
    RESULT_INFO_FAIL("1504","获取用户信息失败，请重新登录"),
    IS_FIRST_LOGIN("1505","首次登录！"),
    UPDATE_PWD_NULL("1506","密码必须大于6位!"),


    // 用户模块
    // 用户名已存在
    USER_NAME_REREAT("2501","用户名已存在！"),
    // 修改密码
    OLD_PWD_FAIL("2502","原密码不正确！"),

    // 实验室管理模块
    LAB_ADD_EXIST("3501","实验室地址已存在！"),

    // 实验管理模块
    EXP_NAME_EXIST("4051","实验名称重复！"),

    // 预约管理模块
    ADD_LABTOP_FAIL("5501","添加排行榜失败！"),
    ADD_ORDER_FAIL("5502","添加失败！\n当前时间段内实验室已被占用！"),

    // 实验室预约模块
    LAB_NOT_ODD("6501","预约人数已满，请下次提前预约或更改实验室预约！"),
    ORDER_TIME_CLASH("6501","预约时间冲突！"),

    ;

    /** 返回代码 */
    private String code;
    /** 返回信息 */
    private String msg;

    BussinessCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
