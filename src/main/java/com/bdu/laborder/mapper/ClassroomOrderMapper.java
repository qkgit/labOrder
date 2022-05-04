package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
import com.bdu.laborder.entity.OrderAudit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/21 10:59
 */
@Mapper
@Repository
public interface ClassroomOrderMapper {

   public List<ClassroomOrder> getClassroomCourse(ClassroomOrderRequest request);

   public ClassroomOrderDetail getOrderDetailByUserAndOrderId(@Param("orderId") String orderId,@Param("userId") String userId);

   public List<ClassroomOrderDetail> getOrderDetailByUser(@Param("orderRequest") ClassroomOrderRequest orderRequest,@Param("userId")String userId);

   public ClassroomOrder selectClassroomOrderById(String id);

   public int insertOrderDetail(ClassroomOrderDetail orderDetail);

   public int insertOrder(ClassroomOrder order);

   public int updateOrder(ClassroomOrder order);

   public int cencelOrderById(String id);

   public ClassroomOrderDetail checkOrderTime(ClassroomOrderDetail orderDetail);

   public int insertOrderAudit(OrderAudit orderAudit);
}
