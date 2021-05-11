package com.bdu.laborder.controller;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Result;
import com.bdu.laborder.common.ResultGenerator;
import com.bdu.laborder.entity.LabOrder;
import com.bdu.laborder.service.OrderLabService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qi
 * @data 2021/4/5 17:47
 */
@RestController
public class OrderLabController {

    @Autowired
    OrderLabService orderLabService;

    @PostMapping("/orderLab")
    public Result orderLab(HttpServletRequest request){
        int i = orderLabService.orderLab(request);
        if (i > 0){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }

    @PostMapping("/orderList")
    public Result getOrderListByUser(HttpServletRequest request){
        PageInfo<LabOrder> userOrderList = orderLabService.getOrderListByUser(request);
        return  ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,userOrderList);
    }

    @PostMapping("/cancelOrder")
    public Result CancelOrder(HttpServletRequest request) {
        int i = orderLabService.CancelOrder(request);
        if (i > 0){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return ResultGenerator.error(BussinessCode.RESULT_GLOBAL_FAIL);
    }
}
