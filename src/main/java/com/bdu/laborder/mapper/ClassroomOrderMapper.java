package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.ClassroomOrder;
import com.bdu.laborder.entity.ClassroomOrderDetail;
import com.bdu.laborder.entity.ClassroomOrderRequest;
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

   public ClassroomOrderDetail getOrderDetailByUserOnRequest(@Param("orderId") String orderId,@Param("userId") String userId);
}
