package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysDept;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.service.SysDeptService;
import com.bdu.laborder.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2021/11/11 11:05
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController extends BaseController {

    @Autowired
    SysDeptService deptService;

    @GetMapping("/list")
    public Result list( SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return success(depts);
    }

    @GetMapping("/list/exclude/{deptId}")
    public Result excludeChild(@PathVariable(value = "deptId",required = false) String deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().equals(deptId)
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId )) {
                it.remove();
            }
        }
        return success(depts);
    }

    @GetMapping(value = "/{deptId}")
    public Result getInfo(@PathVariable String deptId) {
        return success(deptService.selectDeptById(deptId));
    }


    @PostMapping
    public Result add(@Validated @RequestBody SysDept dept){
        if (deptService.checkDeptNameUnique(dept)){
            return error("新增部门 '"+dept.getDeptName()+"失败，部门名称已存在！");
        }
        dept.setCreateBy(getUserName());
        return toResult(deptService.insertDept(dept));
    }

    @PutMapping
    public Result edit(@Validated @RequestBody SysDept dept) {
        if (deptService.checkDeptNameUnique(dept)){
            return error("新增部门 '"+dept.getDeptName()+"失败，部门名称已存在！");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(getUserName());
        return toResult(deptService.updateDept(dept));
    }

    @DeleteMapping("/{deptId}")
    public Result remove(@PathVariable String deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return error("部门存在用户,不允许删除");
        }
        return toResult(deptService.deleteDeptById(deptId));

    }
}
