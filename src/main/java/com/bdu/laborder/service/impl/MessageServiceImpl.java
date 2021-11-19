package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.entity.Message;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.MessageMapper;
import com.bdu.laborder.service.MessageService;
import com.bdu.laborder.service.UserService;
import com.bdu.laborder.utils.JwtUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/11 11:04
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    MessageMapper messageMapper;

    @Override
    public boolean saveMessage(HttpServletRequest request) {
        // 获取留言内容
        com.alibaba.fastjson.JSONObject jsonParam = this.getJSONParam(request);
        String content = jsonParam.get("content").toString();
        // 获取用户信息
        Integer userId = getUserId(request);
        SysUser user = userService.getUserById(userId);
        // 添加留言
        Message message = new Message();
        message.setUserId(userId);
        message.setUserName(user.getLoginName());
        message.setContent(content);
        int i = messageMapper.addMessage(message);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<Message> messageList(PageInfo page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Message> messageList = messageMapper.getMessageList();
        PageInfo<Message> messagePageInfo = new PageInfo<>(messageList);
        return messagePageInfo;
    }

    @Override
    public boolean deleteMessage(Integer id) {
        return false;
    }

    /**
     *  获取用户id
     * @param request
     * @return
     */
    public Integer getUserId(HttpServletRequest request) {
        String userId = null;
        try {
            // 获取token
            String token = request.getHeader("X-Token");
            // 解析token
            Claims claims = jwtUtils.parseJwt(token);
            // 获取id
            userId = claims.getId();
        }catch (Exception e){
            // token解析失败 返回异常让用户重新登录
            throw new LabOrderException(BussinessCode.RESULT_INFO_FAIL);
        }
        int id = Integer.parseInt(userId);
        return id;
    }

    /**
     *  获取request中携带的参数
     * @param request
     * @return
     */
    public com.alibaba.fastjson.JSONObject getJSONParam(HttpServletRequest request){
        com.alibaba.fastjson.JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = com.alibaba.fastjson.JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }
}
