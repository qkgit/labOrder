package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.LabOrderDetil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/5 18:14
 */
@Mapper
@Repository
public interface OrderLabMapper {

    int userOrderLab(LabOrderDetil labOrderDetil);

    List<String> getLoIdListByUserId(Integer id);
    List<Integer> getAwaitLoIdListByUser(Integer id);

    int deleteOrderByUser(Integer id);
}
