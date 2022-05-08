package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.entity.OrderAudit;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.service.ClassroomOrderService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
    @PostMapping("/classroom/list")
    public Result getClassroomCourse(@RequestBody ClassroomOrderRequest request){
        List<ClassroomOrder> classroomCourse = orderService.getClassroomCourse(request,getUserId());
        return success(classroomCourse);
    }

    /**
     *  用户预约操作
     */
    @PostMapping("/classroom")
    public Result orderClassroom(@RequestBody ClassroomOrderDetail orderDetail){
        SysUser loginUser = getLoginUser();
        // 校验用户存在同时间预约冲突
        checkOrderDetail(orderDetail);
        if (UserConstants.NOT_UNIQUE.equals(orderService.checkOrderTime(orderDetail,loginUser))){
            return error("/(ㄒoㄒ)/~~ 预约失败！已存在相同时间预约，请选择教室！");
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
    @PostMapping("/classroom/record")
    public Result getOrderRecordByUser(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        ClassroomOrderRequest orderRequest = getParam(pageQuery, ClassroomOrderRequest.class);
        List<ClassroomOrderDetail> orderRecords = orderService.getOrderRecordByUser(orderRequest,getUserId());
        // 设置过期
        orderRecords.forEach(o->{
            LocalDate orderDate = o.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (LocalDate.now().isAfter(orderDate)){
                o.setTimeOut();
            }
        });
        return getPageInfo(orderRecords);
    }

    /**
     *  用户取消预约
     * @param orderDetailId
     * @return
     */
    @PutMapping("/classroom/cencel/{orderDetailId}")
    public Result cencelOrder(@PathVariable("orderDetailId") String orderDetailId){
        return toResult(orderService.cencelOrderById(orderDetailId));
    }

    /**
     *  查询审核页面
     * @param pageQuery
     * @return
     */
    @PostMapping("/classroom/audit")
    public Result getOrderRecordRole(@RequestBody PageQuery pageQuery) {
        SysUser loginUser = getLoginUser();
        startPage(pageQuery);
        ClassroomOrderRequest orderRequest = getParam(pageQuery, ClassroomOrderRequest.class);
        List<ClassroomOrderDetail> orderRecords = orderService.getOrderRecordByRoles(orderRequest,loginUser);
        return getPageInfo(orderRecords);
    }

    // (批量) 审核通过
    @PutMapping("/classroom/audit/pass")
    public Result passOrderAudit(@RequestBody OrderAudit orderAudit){
        SysUser loginUser = getLoginUser();
        orderAudit.setReviewUser(loginUser.getRealName());
        orderAudit.setReviewUserId(loginUser.getUserId());
        orderAudit.setState("1");
        return toResult(orderService.auditOrder(orderAudit,true));
    }

    // (批量) 审核不通过
    @PutMapping("/classroom/audit/nopass")
    public Result noPassOrderAudit(@RequestBody OrderAudit orderAudit){
        SysUser loginUser = getLoginUser();
        orderAudit.setReviewUser(loginUser.getRealName());
        orderAudit.setReviewUserId(loginUser.getUserId());
        orderAudit.setState("1");
        return toResult(orderService.auditOrder(orderAudit,false));
    }

    private void checkOrderDetail(ClassroomOrderDetail orderDetail){
        if (orderDetail.getOrderDate() == null
                || StringUtils.isEmpty(orderDetail.getOrderNode())
                || StringUtils.isEmpty(orderDetail.getClassroomId())){
            throw new BaseException("预约数据不能为空！");
        }
    }
}
