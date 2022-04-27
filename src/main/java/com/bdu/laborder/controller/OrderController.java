package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.service.ClassroomService;
import com.bdu.laborder.service.CourseTableService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private ClassroomOrderService orderService;


    /**
     * 查询各楼层教室课表（预约使用）
     */
    @PostMapping("/classroom")
    public Result getClassroomCourse(@RequestBody ClassroomOrderRequest request){
        List<ClassroomOrder> classroomCourse = orderService.getClassroomCourse(request,getUserId());
        return success(classroomCourse);
    }

    /**
     *  用户预约操作
     * @return
     */
    @PostMapping
    public Result orderClassroom(@RequestBody ClassroomOrderDetail orderDetail){
        SysUser loginUser = getLoginUser();

        // 是否教室首次预约
        if (StringUtils.isEmpty(orderDetail.getOrderId())){
            // 添加预约记录表
            ClassroomOrder classroomOrder = new ClassroomOrder();
            classroomOrder.setUuid(UuidUtil.getUuid());
            classroomOrder.setRoomId(orderDetail.getClassroomId());
            classroomOrder.setOrderDate(orderDetail.getOrderDate());
            classroomOrder.setOrderNode(orderDetail.getOrderNode());
        }
        // 校验用户存在同时间预约冲突

        // 判断预约人数是否已满

        // 添加预约人数

        return success();
    }


}
