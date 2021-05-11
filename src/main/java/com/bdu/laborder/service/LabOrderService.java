package com.bdu.laborder.service;

import com.bdu.laborder.entity.LabOrder;
import com.bdu.laborder.entity.LabOrderDetil;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;

/**
 * @Author Qi
 * @data 2021/3/6 18:18
 */
public interface LabOrderService {

    PageInfo<LabOrder> queryAllLabList(PageQuery pageQuery);
    PageInfo<LabOrder> querySLabOrderList(PageQuery pageQuery);
    PageInfo<LabOrder> queryTLabOrderList(PageQuery pageQuery);
    LabOrder getLabOrderById(int id);
    int addLabOrder(LabOrder labOrder);
    int updateLabOrder(LabOrder labOrder);
    int suspendLabOrder(int id);
    PageInfo<LabOrderDetil> getOrderDetail(PageQuery pageQuer);
    int deleteLabOrder(int id);
}
