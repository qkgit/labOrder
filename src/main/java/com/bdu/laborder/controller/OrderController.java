package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.utils.StringUtils;
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
     */
    @PostMapping
    public Result orderClassroom(@RequestBody ClassroomOrderDetail orderDetail){
        SysUser loginUser = getLoginUser();
        // 校验用户存在同时间预约冲突
        checkOrderDetail(orderDetail);
        if (UserConstants.NOT_UNIQUE.equals(orderService.checkOrderTime(orderDetail,loginUser))){
            return error("/(ㄒoㄒ)/~~ 预约失败！已存在相同时间预约！");
        }
        int i = orderService.addOrder(orderDetail, loginUser);
        if (i>0){
            return success();
        }
        return error("\"/(ㄒoㄒ)/~~ 预约失败！请重新进行预约!");
    }

    /**
     *  用户查询预约记录
     */
    @PostMapping("/record/classroom")
    public Result getOrderRecordByUser(@RequestBody ClassroomOrderDetail orderDetail){
        SysUser loginUser = getLoginUser();
        return success();
    }

    private void checkOrderDetail(ClassroomOrderDetail orderDetail){
        if (orderDetail.getOrderDate() == null
                || StringUtils.isEmpty(orderDetail.getOrderNode())
                || StringUtils.isEmpty(orderDetail.getClassroomId())){
            throw new BaseException("预约数据不能为空！");
        }
    }
}
