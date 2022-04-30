package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.domain.service.UserService;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.mapper.ClassroomOrderMapper;
import com.bdu.laborder.mapper.CourseTableMapper;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.utils.DateUtils;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
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
        ClassroomOrder orderInfo = null;
        if (StringUtils.isEmpty(orderDetail.getOrderId())) {
            addOrderFlag = true;
            orderInfo = new ClassroomOrder();
            orderInfo.setUuid(UuidUtil.getUuid());
            orderInfo.setRoomId(orderDetail.getClassroomId());
            orderInfo.setOrderDate(orderDetail.getOrderDate());
            orderInfo.setOrderNode(orderDetail.getOrderNode());
            orderInfo.setOrderNum(0);
        } else {
            orderInfo = getClassroomOrderById(orderDetail.getOrderId());
        }
        // 根据用户角色添加预约人数
        //     => 教师  orderInfo不进行操作      orderDetail.orderstatus = '6'
        //     => 学生  orderInfo.orderNum +1   orderDetail.orderstatus = '8'
        if (UserService.isTeacher(user)) {
            orderDetail.setOrderStatus("6");
        }
        if (UserService.isStudent(user)) {
            Integer orderNum = orderInfo.getOrderNum();
            orderInfo.setOrderNum(orderNum + 1);
            orderDetail.setOrderStatus("1");
        }
        orderDetail.setUuid(UuidUtil.getUuid());
        orderDetail.setOrderUser(user.getUserId());
        orderDetail.setOrderId(orderInfo.getUuid());
        // 添加预约详情表
        orderMapper.insertOrderDetail(orderDetail);
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
        sortMap.put("c2_2", 3);
        sortMap.put("c2_1", 4);
        sortMap.put("c5", 5);
        sortMap.put("c3", 6);
        sortMap.put("c1_2", 7);
        sortMap.put("c1_1", 8);
        sortMap.put("j6", 9);
        sortMap.put("j5", 10);
        sortMap.put("j4", 11);
        sortMap.put("j0", 12);
        classroomCourse.sort(Comparator.comparingInt(n -> sortMap.get(n.getRoomAddress())));
        return classroomCourse;
    }
}
