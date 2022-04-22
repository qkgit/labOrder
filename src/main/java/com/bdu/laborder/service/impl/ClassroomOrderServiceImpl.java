package com.bdu.laborder.service.impl;

import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.mapper.ClassroomOrderMapper;
import com.bdu.laborder.mapper.CourseTableMapper;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if ("0".equals(cc.getRoomStatus())){
                if(StringUtils.isEmpty(cc.getTableId())){
                    // 没有课程
                    // 查询该教室有无预约记录及当前用户预约状态

                    // 查询
                    ClassroomOrderDetail orderInfo = orderMapper.getOrderDetailByUserOnRequest(request, userId);
                    if (orderInfo!=null){
                        cc.setRoomStatus(orderInfo.getOrderStatus());
                    }
                }else {
                   // 有课程 设置课程信息
                    cc.setTable(tableMapper.selectCourseTableById(cc.getTableId()));
                }

            }

        });
        return classroomCourse;
    }
}
