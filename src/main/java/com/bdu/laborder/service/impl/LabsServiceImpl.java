package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.entity.LabRequest;
import com.bdu.laborder.entity.Labs;
import com.bdu.laborder.entity.LabsTop;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.mapper.LabsMapper;
import com.bdu.laborder.service.LabsService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Qi
 * @data 2020/12/26 22:02
 */
@Service
public class LabsServiceImpl implements LabsService {

    @Autowired
    LabsMapper labsMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public PageInfo<Labs> getLabs(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
//        String pageSize = (String) ((LinkedHashMap) page.get(0)).get("pageSize");
        Gson gson = CreateGson.createGson();
        LabRequest item = gson.fromJson(gson.toJson(pageQuery.getItem()), LabRequest.class);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Labs> Labs = labsMapper.getLabs(item);
//        redisUtil.set("lab",Labs);
        PageInfo<Labs> pageInfo = new PageInfo<>(Labs);
        return pageInfo;
    }

    @Override
    public Labs addLab(Labs labs) {
        String address = labs.getLAddress();
        Labs lab = labsMapper.getLabByAddress(address);
        if (lab != null){
            // 如果存在有该地址的实验室 返回异常
            throw new BaseException(BussinessCode.LAB_ADD_EXIST);
        }
        int i = labsMapper.addLab(labs);
        if(i == 0){
            return null;
        }
        return labs;

    }

    @Override
    public Labs getLabById(Integer id) {
        Labs lab = labsMapper.getLabById(id);
        return lab;
    }

    @Override
    public Labs updataLab(Labs labs) {
        try {
            labsMapper.updateLab(labs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return labs;
    }

    @Override
    public int deleteLab(Integer id) {
        try {
            int i = labsMapper.deleteLab(id);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    @Cacheable(value = "labs")
    public List<Labs> getAllLab() {
        try {
            List<Labs> allLab = labsMapper.getAllLab();
            return allLab;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<LabsTop> getLabsTop() {
        try {
            List<LabsTop> labsTop = labsMapper.getLabsTop();
            for (LabsTop top : labsTop) {
                int lId = top.getLId();
                Labs lab = labsMapper.getLabById(lId);
                top.setLName(lab.getLName());
                top.setLAddress(lab.getLAddress());
            }
            return labsTop;
        }catch (Exception e) {
            return null;
        }
    }

}
