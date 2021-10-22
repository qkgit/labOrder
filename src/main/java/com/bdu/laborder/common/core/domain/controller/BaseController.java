package com.bdu.laborder.common.core.domain.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.entity.User;
import com.bdu.laborder.service.UserService;
import com.bdu.laborder.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/10/2 16:53
 */
public class BaseController {
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   JwtUtils jwtUtils;
   @Autowired
   RedisUtil redisUtil;
   @Autowired
   UserService userService;

   protected void startPage(PageQuery pageQuery){
      PageInfo page = pageQuery.getPage();
      int pageNum = page.getPageNum();
      int pageSize = page.getPageSize();
       if (StringUtils.isNotNull(pageNum)&&StringUtils.isNotNull(pageSize)) {
          PageHelper.startPage(pageNum,pageSize);
       }
   }

   protected Result getPageInfo(List<?> list) {
      Result result = ResultGenerator.success();
      result.setData(new PageInfo(list));
      return result;
   }

   protected <T> T getParam(PageQuery pageQuery,Class<T> classOfT) {
      Gson gson = CreateGson.createGson();
      gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), classOfT);
      return gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), classOfT);
   }

   public Result success(Object data) {
      return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,data);
   }
   public Result error() {
      return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
   }
   public Result error(String msg){
      return ResultGenerator.error(msg);
   }

   /**
    * 获取用户缓存信息
    */
   public User getLoginUser() {
      HttpServletRequest request = ServletUtils.getRequest();
      String token = request.getHeader("X-Token");
      if (token == null){
         token = request.getHeader("Token");
      }
      // 解析token 获取id
      String userId = jwtUtils.parseJwt(token).getId();
      if (StringUtils.isNotEmpty(userId)){
         User loginUser = userService.getUserById(Integer.parseInt(userId));
         return loginUser;
      }
      return null;
   }

   public String getUserName(){
      return this.getLoginUser().getRealName();
   }


}
