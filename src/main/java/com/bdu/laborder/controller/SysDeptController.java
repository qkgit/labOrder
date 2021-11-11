package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysDept;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Result add(@Validated @RequestBody SysDept dept){
        if (deptService.checkDeptNameUnique(dept)){
            return error("新增部门 '"+dept.getDeptName()+"失败，部门名称已存在！");
        }
        dept.setCreateBy(getUserName());
        return toResult(deptService.insertDept(dept));
    }
}
