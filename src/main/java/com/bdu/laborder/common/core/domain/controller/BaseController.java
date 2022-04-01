package com.bdu.laborder.common.core.domain.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.service.SysUserService;
import com.bdu.laborder.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
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
   SysUserService userService;

   /** 处理返回信息 */
   public Result success(){
      return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_SUCCESS);
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
   public Result toResult(int rows){
      return rows > 0 ? ResultGenerator.success() : ResultGenerator.error("操作失败!");
   }
   public Result toResult(boolean result){return result ? success() : error();}



   /**
    *  设置分页
    * @param pageQuery
    */
   protected void startPage(PageQuery pageQuery){
      PageInfo page = pageQuery.getPage();
      int pageNum = page.getPageNum();
      int pageSize = page.getPageSize();
       if (StringUtils.isNotNull(pageNum)&&StringUtils.isNotNull(pageSize)) {
          PageHelper.startPage(pageNum,pageSize);
       }
   }

   /**
    * 获取分页数据
    * @param list
    * @return
    */
   protected Result getPageInfo(List<?> list) {
      Result result = ResultGenerator.success();
      result.setData(new PageInfo(list));
      return result;
   }

   /**
    *  获取查询字段信息
    * @param pageQuery  pageQuery
    * @param classOfT  解析为哪个实体类
    * @param <T>  接收返回数据的实体类类
    * @return
    */
   protected <T> T getParam(PageQuery pageQuery,Class<T> classOfT) {
      Gson gson = CreateGson.createGson();
      return gson.fromJson(gson.toJson(pageQuery.getItem()), classOfT);
   }



   /**
    * 获取用户缓存信息
    */
   public SysUser getLoginUser() {
      HttpServletRequest request = ServletUtils.getRequest();
      String token = request.getHeader("X-Token");
      if (token == null){
         token = request.getHeader("Token");
      }
      // 解析token 获取id
      String userId = jwtUtils.parseJwt(token).getId();
      if (StringUtils.isNotEmpty(userId)){
         SysUser loginUser = userService.getUserById(userId);
         return loginUser;
      }
      return null;
   }

   public String getUserId(){
      return getLoginUser().getUserId();
   }
   public String getUserName(){
      return this.getLoginUser().getRealName();
   }


}
