package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.LabRequest;
import com.bdu.laborder.entity.Labs;
import com.bdu.laborder.entity.LabsTop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @Author Qi
 * @data 2020/12/26 22:03
 */
@Mapper
@Repository
public interface LabsMapper {

    /**
     * 条件查询开放实验室列表
     */
    List<Labs> getLabs(LabRequest item);

    /**
     * 查询某个实验室详情
     */
    Labs getLabById(Integer id);

    /**
     * 添加实验室
     */
    int addLab(Labs labs);

    /**
     * 修改实验室
     */
    int updateLab(Labs labs);

    /**
     * 根据id删除实验室
     */
    int deleteLab(Integer id);

    Labs getLabByAddress(String add);

    List<Labs> getAllLab();

    String getLTypeById(String lId);


    /**
     *  实验室实验排行榜
     */
    LabsTop getLabTopById(Integer id);

    int addLabTop(LabsTop labsTop);

    int updateLabTop(LabsTop labsTop);

    List<LabsTop> getLabsTop();
}
