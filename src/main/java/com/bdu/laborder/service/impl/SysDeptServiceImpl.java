package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.TreeSelect;
import com.bdu.laborder.common.core.domain.entity.SysDept;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.SysDeptMapper;
import com.bdu.laborder.service.SysDeptService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title
 * @Author Qi
 * @data 2021/11/11 11:06
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return
     */
    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<String> tempList = new ArrayList<String>();
        for (SysDept dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().equals(t.getDeptId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTree = buildDeptTree(depts);
        return deptTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return null;
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(String deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(String deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    @Override
    public boolean hasChildByDeptId(String deptId) {
        return false;
    }

    @Override
    public boolean checkDeptExistUser(String deptId) {
        return false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return
     */
    @Override
    public boolean checkDeptNameUnique(SysDept dept) {
        String deptId = dept.getDeptId();
        SysDept deptInfo = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(deptInfo) && !deptInfo.getDeptId().equals(deptId)) {
            return Constant.TRUE;
        }
        return Constant.FALSE;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new LabOrderException("部门停用，不允许新增");
        }
        dept.setDeptId(UuidUtil.getUuid());
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        if (!dept.getParentId().equals(oldDept.getParentId())) {
            // 如果修改上级部门
            if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
                // 修改该部门所有子部门的祖级列表
                String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
                String oldAncestors = oldDept.getAncestors();
                dept.setAncestors(newAncestors);
                updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
            }
        }
        if (!oldDept.getStatus().equals(dept.getStatus())) {
            // 如果修改状态
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                // 启用部门，则启用该部门的所有上下级部门
                updateParentDeptStatus(dept);
                updateChildrenDeptStatus(dept);
            }
        }

        int result = deptMapper.updateDept(dept);

        return result;
    }

    /**
     * 修改该部门的子级部门状态
     *
     * @param dept 当前部门
     */
    private void updateChildrenDeptStatus(SysDept dept) {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept) {
        String[] parentList = dept.getAncestors().split(",");
        for (String s : parentList) {
            deptMapper.openDeptStatus(s, dept.getUpdateBy());
        }
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(String deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(String deptId) {
        return deptMapper.deleteDeptById(deptId);
    }
}
