package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/16 11:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest implements Serializable {

    // private String newsType;        // 文章所属栏目
    private String title;           // 标题
    private String releaseStatus;   // 发布状态

}
