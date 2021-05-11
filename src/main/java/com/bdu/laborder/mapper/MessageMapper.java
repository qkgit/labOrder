package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/11 11:06
 */
@Repository
@Mapper
public interface MessageMapper {

    List<Message> getMessageList();

    int addMessage(Message message);

    int deleteMessage(Integer id);
}
