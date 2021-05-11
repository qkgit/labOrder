package com.bdu.laborder.service;

import com.bdu.laborder.entity.Message;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qi
 * @data 2021/4/11 11:01
 */
public interface MessageService {
     boolean saveMessage(HttpServletRequest request);
     PageInfo<Message> messageList(PageInfo page);
     boolean deleteMessage(Integer id);
}
