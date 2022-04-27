package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.mapper.ClassroomOrderMapper;
import com.bdu.laborder.mapper.CourseTableMapper;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ClassroomOrder> getClassroomCourse(ClassroomOrderRequest request,String userId) {
        List<ClassroomOrder> classroomCourse = orderMapper.getClassroomCourse(request);
        classroomCourse.forEach(cc->{
            // 判断教室开启状态
            if ("0".equals(cc.getOrderStatus())){
                if(StringUtils.isEmpty(cc.getTableId())){
                    // 没有课程
                    // 查询该教室有无预约记录及当前用户预约状态
                    if (StringUtils.isNotEmpty(cc.getUuid())){
                        // 存在预约表记录
                        // 判断教室预约人员是否满员
                        if (StringUtils.equals(cc.getRoomCap(),cc.getOrderNum().toString())){
                            cc.setOrderStatus("9");
                        }
                        // 设置当前用户预约状态 0-未预约 6-一审 7-二审 8-预约成功  9-预约失败
                        ClassroomOrderDetail orderInfo = orderMapper.getOrderDetailByUserOnRequest(cc.getUuid(), userId);
                        if (orderInfo!=null){
                            cc.setOrderStatus(orderInfo.getOrderStatus());
                        }
                    }else {
                        // 不存在预约表记录 初始化记录表
//                        cc.setUuid(UuidUtil.getUuid());
                        cc.setOrderDate(request.getOrderDate());
                        cc.setOrderNode(request.getOrderNode());
                        cc.setOrderNum(0);
                    }
                }else {
                   // 有课程 设置课程信息
                    cc.setTable(tableMapper.selectCourseTableById(cc.getTableId()));
                    cc.setOrderStatus("1");
                }
            }
        });
        return sortClassroomCourse(classroomCourse);
    }

    @Override
    public int addOrder(ClassroomOrderDetail orderDetail, SysUser user) {
        return 0;
    }

    private List<ClassroomOrder> sortClassroomCourse(List<ClassroomOrder> classroomCourse) {
        HashMap<String, Integer> sortMap = new HashMap<>();
        sortMap.put("c6",1);
        sortMap.put("c4",2);
        sortMap.put("c2_2",3);
        sortMap.put("c2_1",4);
        sortMap.put("c5",5);
        sortMap.put("c3",6);
        sortMap.put("c1_2",7);
        sortMap.put("c1_1",8);
        sortMap.put("j6",9);
        sortMap.put("j5",10);
        sortMap.put("j4",11);
        sortMap.put("j0",12);
        classroomCourse.sort(Comparator.comparingInt(n -> sortMap.get(n.getRoomAddress())));
        return classroomCourse;
    }
}
