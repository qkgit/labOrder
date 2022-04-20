package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.service.ClassroomService;
import com.bdu.laborder.service.CourseTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 查询各楼层教室课表（预约使用）
     */

    // 查询指定条件教室
    // 教室List转换为教室预约List(未存在预约人数初始化为0 存在设置预约人数)
    // 查询教室的课程情况
    // 查询用户预约情况
}
