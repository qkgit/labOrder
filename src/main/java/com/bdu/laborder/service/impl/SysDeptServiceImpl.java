package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysDept;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.SysDeptMapper;
import com.bdu.laborder.service.SysDeptService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     *  查询部门管理数据
     * @param dept 部门信息
     * @return
     */
    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        return null;
    }

    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return null;
    }

    @Override
    public SysDept selectDeptById(Long deptId) {
        return null;
    }

    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return 0;
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        return false;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        return false;
    }

    /**
     *  校验部门名称是否唯一
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

    @Override
    public int updateDept(SysDept dept) {
        return 0;
    }

    @Override
    public int deleteDeptById(Long deptId) {
        return 0;
    }
}
