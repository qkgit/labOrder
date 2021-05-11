package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/** 公告
 * @Author Qi
 * @data 2020/12/26 15:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News implements Serializable {
    private Integer newsId;         // 文章id
//    private String newsType;        // 文章所属栏目
    private String title;           // 标题
    private String content;         // 正文
    private String releaseStatus;   // 发布状态
    private Date createTime;        // 创建时间
    private Date releaseTime;       // 发布时间
}
