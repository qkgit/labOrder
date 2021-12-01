package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/11 10:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private Integer msgId;   // 留言id
    private String userId;  // 用户id
    private String userName; // 用户的登录名 ----院系+姓名---（舍弃）
    private String content;  // 留言内容
    private String time;     // 留言事件

}
