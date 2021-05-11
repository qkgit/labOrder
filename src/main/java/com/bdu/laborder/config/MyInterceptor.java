package com.bdu.laborder.config;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.utils.JwtUtils;
import com.bdu.laborder.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 请求拦截器
 * @Author Qi
 * @data 2021/4/20 14:35
 */
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  在请求处理之前进行调用（ Controller方法调用之前 ）
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
                // token过期 返回异常让用户重新登录
                throw new LabOrderException(BussinessCode.RESULT_INFO_FAIL);
            }
        }catch (Exception e){
            // token解析失败 返回异常让用户重新登录
            throw new LabOrderException(BussinessCode.RESULT_INFO_FAIL);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
