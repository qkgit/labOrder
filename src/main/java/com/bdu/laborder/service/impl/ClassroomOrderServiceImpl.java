package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.domain.service.UserService;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.entity.OrderAudit;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.mapper.ClassroomMapper;
import com.bdu.laborder.mapper.ClassroomOrderMapper;
import com.bdu.laborder.mapper.CourseTableMapper;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/22 15:38
 */
@Service
public class ClassroomOrderServiceImpl implements ClassroomOrderService {

    @Autowired
    private ClassroomOrderMapper orderMapper;
    @Autowired
    private CourseTableMapper tableMapper;
    @Autowired
    private ClassroomMapper classroomMapper;

    @Override
    public List<ClassroomOrder> getClassroomCourse(ClassroomOrderRequest request, String userId) {

        List<ClassroomOrder> classroomCourse = orderMapper.getClassroomCourse(request);
        classroomCourse.forEach(cc -> {
            // 判断教室开启状态
            if ("0".equals(cc.getOrderStatus())) {
                if (StringUtils.isEmpty(cc.getTableId())) {
                    // 没有课程
                    // 查询该教室有无预约记录及当前用户预约状态
                    if (StringUtils.isNotEmpty(cc.getUuid())) {
                        // 存在预约表记录
                        // 判断教室预约人员是否满员
                        if (StringUtils.equals(cc.getRoomCap(), cc.getOrderNum().toString())) {
                            cc.setOrderStatus("9");
                        }
                        // 设置当前用户预约状态
                        // 0-未预约 6-一审 7-二审 8-预约成功 9-预约失败
                        ClassroomOrderDetail orderInfo = orderMapper.getOrderDetailByUserAndOrderId(cc.getUuid(), userId);
                        if (orderInfo != null) {
                            cc.setOrderStatus(orderInfo.getOrderStatus());
                        }
                    } else {
                        // 不存在预约表记录 初始化记录表
                        cc.setOrderDate(request.getOrderDate());
                        cc.setOrderNode(request.getOrderNode());
                        cc.setOrderNum(0);
                    }
                } else {
                    // 有课程 设置课程信息
                    cc.setTable(tableMapper.selectCourseTableById(cc.getTableId()));
                    cc.setOrderStatus("1");
                }
            }
        });
        return sortClassroomCourse(classroomCourse);
    }

    @Override
    public ClassroomOrder getClassroomOrderById(String id) {
        return orderMapper.selectClassroomOrderById(id);
    }

    @Override
    @Transactional
    public int addOrder(ClassroomOrderDetail orderDetail, SysUser user) {
        // 添加标识
        Boolean addOrderFlag = false;
        // 根据orderId获取预约记录表
        //     => orderId为空    教室首次预约,需要初始化_预约记录表
        //     => orderId不为空  教室已存在预约记录,查询获取
        String roomCap = classroomMapper.getCapByClassroomId(orderDetail.getClassroomId());
        ClassroomOrder orderInfo = null;
        if (StringUtils.isEmpty(orderDetail.getOrderId())) {
            addOrderFlag = true;
            orderInfo = new ClassroomOrder();
            orderInfo.setUuid(UuidUtil.getUuid());
            orderInfo.setRoomId(orderDetail.getClassroomId());
            orderInfo.setOrderDate(orderDetail.getOrderDate());
            orderInfo.setOrderNode(orderDetail.getOrderNode());
            orderInfo.setOrderNum(0);
            orderInfo.setRoomCap(roomCap);
        } else {
            orderInfo = getClassroomOrderById(orderDetail.getOrderId());
        }
        // 存在预约记录表 进行后续操作
        // 根据用户角色添加预约人数 预约详情 审核信息
        //     => 教师
        //            orderInfo不进行操作  orderDetail.orderstatus = '6'  添加预约审核信息
        //     => 学生
        //           orderInfo.orderNum +1   orderDetail.orderstatus = '8'

        // 预置预约审核实体
        OrderAudit orderAudit = null;

        orderDetail.setUuid(UuidUtil.getUuid());
        orderDetail.setOrderUser(user.getUserId());
        orderDetail.setOrderId(orderInfo.getUuid());
        if (UserService.isTeacher(user)) {
            orderDetail.toLeaderCheck();
            // 添加预约审核信息
            orderAudit = new OrderAudit();
            orderAudit.setUuid(UuidUtil.getUuid());
            orderAudit.setOrderRecordId(orderDetail.getUuid());
            orderAudit.setType(Constant.ORDER_TYPE_LEADER);
        }
        if (UserService.isStudent(user)) {
            Integer orderNum = orderInfo.getOrderNum();
            Integer cap = Integer.valueOf(roomCap);
            // 满员验证
            if (orderNum+1>cap){
                throw new BaseException("该教室已经满员，请重新选择教室预约！");
            }
            orderInfo.setOrderNum(orderNum + 1);
            orderDetail.toComplete();
        }

        // 添加预约详情表
        orderMapper.insertOrderDetail(orderDetail);
        // 添加预约审核表
        if (orderAudit !=null){
            orderMapper.insertOrderAudit(orderAudit);
        }
        // 添加预约表
        int i = 0;
        if (addOrderFlag) {
            i = orderMapper.insertOrder(orderInfo);
        } else {
            i = orderMapper.updateOrder(orderInfo);
        }
        return i;
    }

    @Override
    public List<ClassroomOrderDetail> getOrderRecordByUser(ClassroomOrderRequest orderRequest,String userId) {
        return orderMapper.getOrderDetailByUser(orderRequest,userId);
    }

    @Override
    public List<ClassroomOrderDetail> getOrderRecordByRoles(ClassroomOrderRequest orderRequest, SysUser user) {
        List<ClassroomOrderDetail> orderRecordList = new ArrayList<>();
        if (UserService.isClassroomLeader(user) && UserService.isSecretary(user)){
            // 两者都是
            orderRecordList = orderMapper.getOrderRecordByType(orderRequest, null, user.getUserId());
        }else if (UserService.isClassroomLeader(user)){
            // 教室负责人
            orderRecordList = orderMapper.getOrderRecordByType(orderRequest, Constant.ORDER_TYPE_LEADER, user.getUserId());
        }else if (UserService.isSecretary(user)){
            // 秘书
            orderRecordList = orderMapper.getOrderRecordByType(orderRequest, Constant.ORDER_TYPE_SEC, user.getUserId());
        }
        return orderRecordList;
    }

    /**
     * 审核预约
     *
     * @param orderAudit 预约审核信息
     * @param passFlag   通过状态
     * @return
     */
    @Override
    @Transactional
    public int auditOrder(OrderAudit orderAudit, boolean passFlag) {
        // 添加意见
        orderMapper.updateOrderAudit(orderAudit);
        // 获取用户预约信息
        ClassroomOrderDetail orderDetailInfo = orderMapper.getOrderDetailById(orderAudit.getOrderRecordId());
        // 根据是否通过修改用户预约状态
        String orderStatus = "";
        if (passFlag){
            if (Constant.ORDER_STATUS_LEADER_CHECK.equals(orderDetailInfo.getOrderStatus())){
                orderStatus = Constant.ORDER_STATUS_SEC_CHECK;
            }
            if (Constant.ORDER_STATUS_SEC_CHECK.equals(orderDetailInfo.getOrderStatus())){
                orderStatus = Constant.ORDER_STATUS_COMPLETE;
            }
        }else {
            orderStatus = Constant.ORDER_STATUS_NOT_PASS;
        }
        // 更新用户预约记录状态
        int updateOrderDetailStatus = orderMapper.updateOrderDetailStatus(orderStatus, orderAudit.getOrderRecordId());
        // 如果发送给秘书 需要添加预约审核信息
        if (Constant.ORDER_STATUS_SEC_CHECK.equals(orderStatus)){
            OrderAudit addOrderAudit = new OrderAudit();
            addOrderAudit.setUuid(UuidUtil.getUuid());
            addOrderAudit.setOrderRecordId(orderAudit.getOrderRecordId());
            addOrderAudit.setType(Constant.ORDER_TYPE_SEC);
            orderMapper.insertOrderAudit(addOrderAudit);
        }
        // 如果审核完成 修改教室预约人数为满员
        if (Constant.ORDER_STATUS_COMPLETE.equals(orderStatus)){
            String classroomCap = classroomMapper.getCapByClassroomId(orderDetailInfo.getClassroomId());
            int updateOrderNumById = orderMapper.updateOrderNumById(classroomCap, orderDetailInfo.getOrderId());
            return updateOrderNumById;
        }
        return updateOrderDetailStatus;
    }



    /**
     *  取消预约
     * @param id    预约记录id
     * @param user  预约用户
     * @return
     */
    @Override
    public int cencelOrderById(String id,SysUser user) {
        // 获取预约记录
        ClassroomOrderDetail orderDetail = orderMapper.getOrderDetailById(id);
        // 获取预约单
        ClassroomOrder classroomOrder = orderMapper.selectClassroomOrderById(orderDetail.getOrderId());
        // 判断预约状态是否已成功
        // 如果已经预约成功了 取消就要将预约人数进行相应的减少
        if (Constant.ORDER_STATUS_COMPLETE.equals(orderDetail.getOrderStatus())){

            if (UserService.isStudent(user)){
                Integer orderNum = classroomOrder.getOrderNum();
                classroomOrder.setOrderNum(orderNum-1);
            }
            if (UserService.isTeacher(user)){
                classroomOrder.setOrderNum(0);
            }
            orderMapper.updateOrder(classroomOrder);
        }
        return orderMapper.cencelOrderById(id);
    }

    @Override
    public String checkOrderTime(ClassroomOrderDetail orderDetail, SysUser user) {
        orderDetail.setOrderUser(user.getUserId());
        ClassroomOrderDetail info = orderMapper.checkOrderTime(orderDetail);
        if (StringUtils.isNotNull(info)){
            return UserConstants.NOT_UNIQUE;
        }else {
            return UserConstants.UNIQUE;
        }

    }



    private List<ClassroomOrder> sortClassroomCourse(List<ClassroomOrder> classroomCourse) {
        HashMap<String, Integer> sortMap = new HashMap<>();
        sortMap.put("c6", 1);
        sortMap.put("c4", 2);
        sortMap.put("c2", 3);
        sortMap.put("c2_2", 4);
        sortMap.put("c2_1", 5);
        sortMap.put("c5", 6);
        sortMap.put("c3", 7);
        sortMap.put("c1", 8);
        sortMap.put("c1_2", 9);
        sortMap.put("c1_1", 10);
        sortMap.put("j6", 11);
        sortMap.put("j5", 12);
        sortMap.put("j4", 13);
        sortMap.put("j0", 14);
        classroomCourse.sort(Comparator.comparingInt(n -> sortMap.get(n.getRoomAddress())));
        return classroomCourse;
    }
}
