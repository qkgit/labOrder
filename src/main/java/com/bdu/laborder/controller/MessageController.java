package com.bdu.laborder.controller;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Result;
import com.bdu.laborder.common.ResultGenerator;
import com.bdu.laborder.entity.Message;
import com.bdu.laborder.service.MessageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qi
 * @data 2021/4/11 10:52
 */
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/getMessage")
    public Result getMessageList(@RequestBody PageInfo page){
        PageInfo<Message> messagePageInfo = messageService.messageList(page);
        return  ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,messagePageInfo);
    }

    @PostMapping("/postMessage")
    public Result postMessage(HttpServletRequest request){
        boolean b = messageService.saveMessage(request);
        if (b){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }

    public Result deleteMessage(Integer id) {
        return null;
    }
}
