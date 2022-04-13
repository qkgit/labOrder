package com.bdu.laborder.config;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.utils.JwtUtils;
import com.bdu.laborder.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 请求拦截器
 * @Author Qi
 * @data 2021/4/20 14:35
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 前置处理：
     * 在业务处理器处理请求之前被调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 获取token
            String token = request.getHeader("X-Token");
            if (token == null){
                token = request.getHeader("Token");
            }
            // 解析token 获取id
            String userId = jwtUtils.parseJwt(token).getId();
            // 获取缓存中存储的token
            String o = (String)redisUtil.get("token :"+userId);
            if (!token.equals(o)){
                //  Redis内token过期 返回异常让用户重新登录
                throw new BaseException(BussinessCode.RESULT_TOKEN_OVER);
            }
        }catch (Exception e){
            // token解析失败或token过期  返回异常让用户重新登录
            throw new BaseException(BussinessCode.RESULT_TOKEN_OVER);
        }
        return true;
    }

    /**
     * 中置处理：
     * 在业务处理器处理请求执行完成后，生成视图之前执行。
     * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染），
     * 有机会修改ModelAndView ，现在这个很少使用了
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    /**
     * 后置处理：
     * 在DispatcherServlet完全处理完请求后被调用，
     * 可用于清理资源等
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
