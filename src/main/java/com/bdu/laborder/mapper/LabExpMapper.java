package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.ExpRequest;
import com.bdu.laborder.entity.Experiment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:37
 */
@Mapper
@Repository
public interface LabExpMapper {

    /**
     * 查询实验列表
     */
    List<Experiment> getExps(ExpRequest item);

    /**
     * 查询某个实验 详情信息
     */
    Experiment getLabExpById(Integer id);

    /**
     * 添加实验
     */
    int addLabExp(Experiment experiment);

    /**
     * 修改实验
     */
    int updataLabExp(Experiment experiment);

    /**
     * 根据id删除实验
     */
    int deleteLabExp(Integer id);

    String getExpByName(String expName);

    List<Experiment> getExpNameByType(String expType);

}
