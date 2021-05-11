package com.bdu.laborder.controller;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Result;
import com.bdu.laborder.common.ResultGenerator;
import com.bdu.laborder.entity.Labs;
import com.bdu.laborder.entity.LabsTop;
import com.bdu.laborder.service.LabsService;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Qi
 * @data 2020/12/13 17:55
 */
@RestController
public class LabsController {

    @Autowired
    LabsService labsService;

    /**
     * 查询实验室
     */
    @PostMapping("/labList")
    public Result GetLabs(@RequestBody PageQuery pageQuery) {
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        PageInfo<Labs> Labs = labsService.getLabs(pageQuery);
        result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,Labs);
        return result;
    }

    /**
     * 通过实验室id 查询实验室信息
     */
    @GetMapping("/lab/{id}")
    public Result GetLab( @PathVariable Integer id){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        Labs lab = labsService.getLabById(id);
        result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,lab);

        return result;
    }

    /**
     * 新增实验室
     */
    @PostMapping("/lab")
    public Result AddLab( @RequestBody Labs labs){
        // 判空
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        Labs lab = labsService.addLab(labs);
        if (lab != null){
            // 添加成功
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }

    /**
     *  修改实验室
     */
    @PutMapping("/lab")
    public Result updataLab(@RequestBody Labs labs){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        Labs lab = labsService.updataLab(labs);
        if (lab == null){

            return result;
        }
        result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        return result;
    }

    /**
     * 删除实验室
     */
    @DeleteMapping("/lab/{id}")
    public Result deleteLab(@PathVariable Integer id){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        int i = labsService.deleteLab(id);
        if (i == 0){
            return result;
        }
        result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        return result;
    }

    /**
     * 获取所有实验室 添加实验室预约时选择实验室用
     * @return
     */
    @GetMapping("/labs")
    public Result getAllLab(){
        List<Labs> allLab = labsService.getAllLab();
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,allLab);
    }

    // 实验室排行
    @GetMapping("/labsTop")
    public Result getLabTop(){
        List<LabsTop> labsTop = labsService.getLabsTop();
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,labsTop);
    }
}
