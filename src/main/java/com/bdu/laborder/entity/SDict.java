package com.bdu.laborder.entity;

import com.bdu.laborder.common.core.domain.entity.BaseEntity;
import lombok.Data;

/**
 * @Author Qi
 * @data 2021/9/27 14:10
 */
@Data
public class SDict extends BaseEntity {
    /** id */
    private String uuid;
    /** 字典编码 */
    private String code;
    /** 字典值 */
    private String name;
    /** 父字典id */
    private String tableType;
    /** 字典类型（父字典值） */
    private String tableName;
    /** 排序号 */
    private Integer orderNum;
    /** 状态 */
    private String status;
    /** 备注 */
    private String remark;
    /** 表单回显样式*/
    private String listClass;
    /** 样式属性*/
    private String cssClass;
}
