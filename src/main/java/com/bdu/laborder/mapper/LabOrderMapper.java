package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.LabOrder;
import com.bdu.laborder.entity.LabOrderDetil;
import com.bdu.laborder.entity.LabOrderRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/3/6 17:47
 */
@Mapper
@Repository
public interface LabOrderMapper {



    /**
     *  获取所有类型的预约实验室列表
     * @param item
     * @return
     */
    List<LabOrder> getAllLabOrderList(LabOrderRequest item);

    /**
     * 查询-个人预约的实验室列表
     * @return
     */
    List<LabOrder> getSLabOrderList(LabOrderRequest item);

    /**
     * 查询-课程预约的是实验室列表
     * @return
     */
    List<LabOrder> getTLabOrderList(LabOrderRequest item);

    /**
     *  获取预约实验室详细信息
     * @param id
     * @return
     */
    LabOrder getLabOrderById(int id);

    /**
     *
     * @param ids
     * @return
     */
    List<LabOrder> getLabOrderInId(List<String> ids);

    /**
     * 添加可被预约的实验室
     * @param labOrder
     * @return
     */
    int addLabOrder(LabOrder labOrder);

    List<LabOrder> labOrderInTime(int lId, Date dayBeginTime,Date dayEndTime);

    /**
     * 更新-可预约实验室的数据
     * @param labOrder
     * @return
     */
    int updateLabOrder(LabOrder labOrder);

    /**
     *  更新余量
     * @param loId
     * @param loOdd
     * @return
     */
    int updateOdd(int loId,int loOdd);

    /**
     *  中止预约
     * @param id
     * @return
     */
    int suspendLabOrder(int id);

    /**
     * 删除可预约的实验室
     * @return
     */
    int deleteLabOrder(int loId);

    /**
     *  查询预约详情
     * @param loId
     * @return
     */
    List<LabOrderDetil> getOrderDetail(String loId);

}
