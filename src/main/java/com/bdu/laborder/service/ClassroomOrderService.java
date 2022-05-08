package com.bdu.laborder.service;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.entity.OrderAudit;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/22 15:38
 */
public interface ClassroomOrderService {
    public List<ClassroomOrder> getClassroomCourse(ClassroomOrderRequest request,String userId);

    public ClassroomOrder getClassroomOrderById(String id);

    public int addOrder(ClassroomOrderDetail orderDetail, SysUser user);

    public List<ClassroomOrderDetail> getOrderRecordByUser(ClassroomOrderRequest orderRequest,String userId);

    public int cencelOrderById(String id);

    public String checkOrderTime(ClassroomOrderDetail orderDetail, SysUser user);

    public List<ClassroomOrderDetail> getOrderRecordByRoles(ClassroomOrderRequest orderRequest, SysUser user);

    /**
     * 审核预约
     * @param orderAudit 预约审核信息
     * @param passFlag  通过状态
     * @return
     */
    public int auditOrder(OrderAudit orderAudit,boolean passFlag);
}
