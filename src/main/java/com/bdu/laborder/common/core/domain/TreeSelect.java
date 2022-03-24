package com.bdu.laborder.common.core.domain;

import com.bdu.laborder.common.core.domain.entity.SysDept;
import com.bdu.laborder.common.core.domain.entity.SysMenu;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title 树结构实体类
 * @Author Qi
 * @data 2021/11/17 17:43
 */
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 父节点ID */
    private String parentId;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.parentId = dept.getParentId();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysUser user) {
        this.id = user.getUserId();
        this.label = user.getRealName();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeSelect{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", parentId='" + parentId + '\'' +
                ", children=" + children +
                '}';
    }
}
