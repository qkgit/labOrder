package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.service.ClassroomService;
import com.bdu.laborder.service.CourseTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/19 14:14
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CourseTableService tableService;
    @Autowired
    private ClassroomOrderService orderService;


    /**
     * 查询各楼层教室课表（预约使用）
     */
    public Result getClassroomCourse(ClassroomOrderRequest request){
        List<ClassroomOrder> classroomCourse = orderService.getClassroomCourse(request,"");
        return success(classroomCourse);
    }


    // 查询教室的课程情况
    // 查询用户预约情况
}
