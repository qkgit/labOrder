package com.bdu.laborder.controller;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Result;
import com.bdu.laborder.common.ResultGenerator;
import com.bdu.laborder.entity.LabOrder;
import com.bdu.laborder.entity.LabOrderDetil;
import com.bdu.laborder.service.LabOrderService;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qi
 * @data 2020/12/27 0:54
 */
@RestController
public class LabOrderController {

    @Autowired
    LabOrderService labOrderService;

    @PostMapping("/labOrders")
    public Result getAllLabOrderList(@RequestBody PageQuery pageQuery){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        PageInfo<LabOrder> pageInfo = labOrderService.queryAllLabList(pageQuery);
        result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,pageInfo);
        return  result;
    }

    @PostMapping("/sLabOrders")
    public Result getSLabOrderList(@RequestBody PageQuery pageQuery){
        PageInfo<LabOrder> pageInfo = labOrderService.querySLabOrderList(pageQuery);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,pageInfo);
    }

    @PostMapping("/tLabOrders")
    public Result getTLabOrderList(@RequestBody PageQuery pageQuery){
        PageInfo<LabOrder> pageInfo = labOrderService.queryTLabOrderList(pageQuery);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,pageInfo);
    }

    @GetMapping("/labOrder/{id}")
    public Result getLabOrderById(@PathVariable Integer id){
        if (id == null){
            return ResultGenerator.error(BussinessCode.RESULT_FIELD_NULL);
        }
        LabOrder order = labOrderService.getLabOrderById(id);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,order);
    }

    @PostMapping("/labOrder")
    public Result addLabOrder(@RequestBody LabOrder labOrder){
        int i = labOrderService.addLabOrder(labOrder);
        if (i >0){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }else {
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        }
    }

    @PutMapping("/labOrders")
    public Result updateLabOrder(@RequestBody LabOrder labOrder){
         int i = labOrderService.updateLabOrder(labOrder);
        if (i >0){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }else {
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        }
    }

    @PostMapping("/suspend/{id}")
    public Result suspendLabOrder(@PathVariable Integer id){
        int i = labOrderService.suspendLabOrder(id);
        if (i >0){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }else {
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        }
    }

    @PostMapping("/orderDetail")
    public Result getOrderDetail(@RequestBody PageQuery pageQuery) {
        PageInfo<LabOrderDetil> orderDetail = labOrderService.getOrderDetail(pageQuery);
        return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,orderDetail);
    }

//    @DeleteMapping("/labOrders")
//    public Result deleteLabOrder(int id){
//        int i = labOrderService.deleteLabOrder(id);
//        if (i >0){
//            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
//        }else {
//            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
//        }
//    }



}
