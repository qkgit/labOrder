package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.entity.ExpRequest;
import com.bdu.laborder.entity.Experiment;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.LabExpMapper;
import com.bdu.laborder.mapper.LabsMapper;
import com.bdu.laborder.service.LabExpService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:36
 */
@Service
public class LabExpServiceImpl implements LabExpService {

    @Autowired
    LabExpMapper labExpMapper;
    @Autowired
    LabsMapper labsMapper;

    @Override
    public PageInfo<Experiment> queryLabExpList(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        Gson gson = CreateGson.createGson();
        ExpRequest item = gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), ExpRequest.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Experiment> labExpList = labExpMapper.getExps(item);
        PageInfo<Experiment> pageInfo = new PageInfo<>(labExpList);
        return pageInfo;
    }

    @Override
    public Experiment getLabExpById(Integer id) {
        Experiment labExp = labExpMapper.getLabExpById(id);
        return labExp;
    }

    @Override
    public int addLabExp(Experiment experiment) {
        String expName = experiment.getExpName();
        String expId = labExpMapper.getExpByName(expName);
        if (expId != null){
            throw new LabOrderException(BussinessCode.EXP_NAME_EXIST);
        }
        int i = labExpMapper.addLabExp(experiment);
        return i;
    }

    @Override
    public int updateLabExp(Experiment experiment) {
        int i = labExpMapper.updataLabExp(experiment);
        return i;
    }

    @Override
    public int deleteLabExp(Integer id) {
        int i = labExpMapper.deleteLabExp(id);
        return i;
    }

    @Override
    public List<Experiment> getExpNameByLId(String lId) {
        // 通过实验室id获取实验室类型
        String lType = labsMapper.getLTypeById(lId);
        // 一通过实验室类型 获取实验list
        List<Experiment> expName = labExpMapper.getExpNameByType(lType);
        return expName;
    }
}
