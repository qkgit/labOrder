package com.bdu.laborder.service;

import com.bdu.laborder.entity.Experiment;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:35
 */
public interface LabExpService {

    PageInfo<Experiment> queryLabExpList(PageQuery pageQuery);
    Experiment getLabExpById(Integer id);
    int addLabExp(Experiment experiment);
    int updateLabExp(Experiment experiment);
    int deleteLabExp(Integer id);
    List<Experiment> getExpNameByLId(String lId);
}
