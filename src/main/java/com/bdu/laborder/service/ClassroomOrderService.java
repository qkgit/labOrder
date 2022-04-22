package com.bdu.laborder.service;

import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderRequest;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/22 15:38
 */
public interface ClassroomOrderService {
    public List<ClassroomOrder> getClassroomCourse(ClassroomOrderRequest request,String userId);
}
