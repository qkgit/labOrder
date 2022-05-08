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

   public ClassroomOrderDetail getOrderDetailById(String id);

   public ClassroomOrderDetail getOrderDetailByUserAndOrderId(@Param("orderId") String orderId,@Param("userId") String userId);

   public List<ClassroomOrderDetail> getOrderDetailByUser(@Param("orderRequest") ClassroomOrderRequest orderRequest,@Param("userId")String userId);

   public List<ClassroomOrderDetail> getOrderRecordByType(@Param("orderRequest") ClassroomOrderRequest orderRequest,@Param("type")String type,@Param("userId")String userId);

   public ClassroomOrder selectClassroomOrderById(String id);

   public int insertOrderDetail(ClassroomOrderDetail orderDetail);

   public int insertOrder(ClassroomOrder order);

   public int updateOrder(ClassroomOrder order);

   public int cencelOrderById(String id);

   public ClassroomOrderDetail checkOrderTime(ClassroomOrderDetail orderDetail);

   public int insertOrderAudit(OrderAudit orderAudit);

   public int updateOrderAudit(OrderAudit orderAudit);

   public int updateOrderDetailStatus(@Param("orderStatus") String orderStatus,@Param("id") String id);

   public int updateOrderNumById(@Param("orderNum") String orderNum,@Param("id") String id);
}
