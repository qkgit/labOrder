package com.bdu.laborder.common.constant;

/**
 * 常量
 * @Author Qi
 * @data 2021/2/3 15:08
 */
public class Constant {

    /** 是否为首次登录 */
    public static final String IS_FIRST_LOGIN = "1";

    /** 校验返回结果码 */
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    /** 通用默认值*/
    public static final String DEFAULT_TRUE = "1";
    public static final String DEFAULT_FALSE = "0";

    /** 预约状态 未开始*/
    public static final String NOT_START="1";
    /** 预约状态 进行中*/
    public static final String IN_HAND = "2";
    /**预约状态 已结束*/
    public static final String IS_OVER = "3";

    /** 开放预约类型*/
    public static final String OPEN_ORDER = "1";
    /** 教学预约类型*/
    public static final String TEACH_ORDER = "2";

    /** 文章发布状态 已发布*/
    public static final String NEWS_RELEASE_TRUE = "1";
    /** 文章发布状态 未发布*/
    public static final String NEWS_RELEASE_FALSE = "0";

    /** 查询字典详情标识*/
    public static final String DICT_TYPE = "type";
    public static final String DICT_DATA = "data";



    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/user/profile";
}
