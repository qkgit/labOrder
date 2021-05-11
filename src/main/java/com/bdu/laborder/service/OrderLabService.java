package com.bdu.laborder.service;

import com.bdu.laborder.entity.LabOrder;
import com.github.pagehelper.PageInfo;


import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qi
 * @data 2021/4/5 17:50
 */
public interface OrderLabService {

    int orderLab(HttpServletRequest request);
    PageInfo<LabOrder> getOrderListByUser (HttpServletRequest request);
    int CancelOrder(HttpServletRequest request);
}
