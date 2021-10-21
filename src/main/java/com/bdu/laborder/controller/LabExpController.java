package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.entity.Experiment;

import com.bdu.laborder.service.LabExpService;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:34
 */
@RestController
public class LabExpController {

    @Autowired
    LabExpService labExpService;

    @PostMapping("/expList")
    public Result getLabExpList(@RequestBody PageQuery pageQuery){

        PageInfo<Experiment> labExpList = labExpService.queryLabExpList(pageQuery);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,labExpList);
    }
    @GetMapping("/exp/{id}")
    public Result getLabExpById(@PathVariable Integer id){
        if (id == null){
            return ResultGenerator.error(BussinessCode.RESULT_FIELD_NULL);
        }
        Experiment exp = labExpService.getLabExpById(id);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,exp);
    }
    @PostMapping("/exp")
    public Result addLabExp(@RequestBody Experiment experiment){
        String expName = experiment.getExpName();
        if (expName.isEmpty()){
            return ResultGenerator.error(BussinessCode.RESULT_FIELD_NULL);
        }
        int i = labExpService.addLabExp(experiment);
        if (i == 0){
            return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
        }
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
    }

    @PutMapping("/exp")
    public Result updateLabExp(@RequestBody Experiment experiment){
        String expName = experiment.getExpName();
        if (expName.isEmpty()){
            return ResultGenerator.error(BussinessCode.RESULT_FIELD_NULL);
        }
        int i = labExpService.updateLabExp(experiment);
        if (i == 0){
            return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
        }
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
    }

    @DeleteMapping("/exp/{id}")
    public Result deleteLabExp(@PathVariable Integer id){
        if (id == null){
            return ResultGenerator.error(BussinessCode.RESULT_FIELD_NULL);
        }
        int i = labExpService.deleteLabExp(id);
        if (i == 0){
            return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
        }
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
    }

    @GetMapping("/expName/{lId}")
    public Result getExpNameByLId(@PathVariable String lId){
        List<Experiment> expNameList = labExpService.getExpNameByLId(lId);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,expNameList);
    }

}
