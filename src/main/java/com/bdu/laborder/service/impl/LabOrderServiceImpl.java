package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Constant;
import com.bdu.laborder.entity.*;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.LabExpMapper;
import com.bdu.laborder.mapper.LabOrderMapper;
import com.bdu.laborder.mapper.LabsMapper;
import com.bdu.laborder.mapper.UserMapper;
import com.bdu.laborder.service.LabOrderService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Qi
 * @data 2021/3/6 18:19
 */
@Service
public class LabOrderServiceImpl implements LabOrderService {

    @Autowired
    LabOrderMapper labOrderMapper;
    @Autowired
    LabsMapper labsMapper;
    @Autowired
    LabExpMapper labExpMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<LabOrder> queryAllLabList(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        Gson gson = CreateGson.createGson();
        LabOrderRequest item = gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), LabOrderRequest.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<LabOrder> allLabOrderList = labOrderMapper.getAllLabOrderList(item);
        PageInfo<LabOrder> pageInfo = new PageInfo<>(allLabOrderList);
        setFullAndOver(pageInfo);
        return pageInfo;
    }

    @Override
    public PageInfo<LabOrder> querySLabOrderList(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        Gson gson = CreateGson.createGson();
        LabOrderRequest item = gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), LabOrderRequest.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        // ??????????????????????????????
        List<LabOrder> sLabOrderList = labOrderMapper.getSLabOrderList(item);
        PageInfo<LabOrder> pageInfo = new PageInfo<>(sLabOrderList);
        setFullAndOver(pageInfo);
        return pageInfo;
    }

    @Override
    public PageInfo<LabOrder> queryTLabOrderList(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        Gson gson = CreateGson.createGson();
        LabOrderRequest item = gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), LabOrderRequest.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<LabOrder> tLabOrderList = labOrderMapper.getTLabOrderList(item);
        PageInfo<LabOrder> pageInfo = new PageInfo<>(tLabOrderList);
        setFullAndOver(pageInfo);
        return pageInfo;
    }

    @Override
    public LabOrder getLabOrderById(int id) {
        LabOrder labOrder = labOrderMapper.getLabOrderById(id);
        return labOrder;
    }

    @Override
    public int addLabOrder(LabOrder labOrder) {
        Integer lId = labOrder.getLId();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        boolean canOrder = isCanOrder(labOrder);
        if (!canOrder){
            throw new LabOrderException(BussinessCode.ADD_ORDER_FAIL);
        }
        // ??????top??????????????????????????????
        LabsTop labTop = labsMapper.getLabTopById(lId);
        String nowDate = df.format(new Date());
        if (labTop == null){
            // ?????????????????????????????? ?????????
            LabsTop labsTop = new LabsTop();
            labsTop.setLId(lId);
            labsTop.setNewestDate(nowDate);
            labsTop.setAllTime(1);
            int i = labsMapper.addLabTop(labsTop);
            if (i == 0){
                throw new LabOrderException(BussinessCode.ADD_LABTOP_FAIL);
            }
        }else{
            // ??????
            String newestDate = labTop.getNewestDate();
            if ( !(newestDate.equals(nowDate))){
                // ????????????????????????
                // ??????????????????????????? ??????+1
                labTop.setNewestDate(nowDate);
                labTop.setAllTime(labTop.getAllTime()+1);
                int i = labsMapper.updateLabTop(labTop);
                if (i == 0){
                    throw new LabOrderException(BussinessCode.ADD_LABTOP_FAIL);
                }
            }
        }


        Labs lab = labsMapper.getLabById(lId);
        labOrder.setLName(lab.getLName());
        labOrder.setLAddress(lab.getLAddress());
        labOrder.setLoCap(Integer.parseInt(lab.getLCap()));
        labOrder.setLoOdd(Integer.parseInt(lab.getLCap()));
        // ??????????????????
        Integer expId = labOrder.getExpId();
        Experiment exp = labExpMapper.getLabExpById(expId);
        labOrder.setExpName(exp.getExpName());
        int i = labOrderMapper.addLabOrder(labOrder);
        return i;
    }

    @Override
    public int updateLabOrder(LabOrder labOrder) {
        // ????????????????????????
        Integer lId = labOrder.getLId();
        Labs lab = labsMapper.getLabById(lId);
        labOrder.setLName(lab.getLName());
        labOrder.setLAddress(lab.getLAddress());
        labOrder.setLoCap(Integer.parseInt(lab.getLCap()));
        // ??????????????????
        Integer expId = labOrder.getExpId();
        Experiment exp = labExpMapper.getLabExpById(expId);
        labOrder.setExpName(exp.getExpName());
        int i = labOrderMapper.updateLabOrder(labOrder);
        return i;
    }

    @Override
    public int suspendLabOrder(int id) {
        int i = labOrderMapper.suspendLabOrder(id);
        return i;
    }

    @Override
    public PageInfo<LabOrderDetil> getOrderDetail(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        Map<String, Object> item = pageQuery.getItem();
        String id = (String)item.get("loId");
        List<LabOrderDetil> orderUserList = labOrderMapper.getOrderDetail(id);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        PageInfo<LabOrderDetil> pageInfo = new PageInfo<>(orderUserList);
        List<LabOrderDetil> userPageList = pageInfo.getList();
        for (LabOrderDetil user : userPageList) {
            int userId = user.getUserId();
            User userById = userMapper.getUserById(userId);
            user.setLoginName(userById.getLoginName());
            user.setRealName(userById.getRealName());
            user.setInstitute(userById.getInstitute());
            user.setMajor(userById.getMajor());
            user.setRoles(userById.getRoles());
        }
        pageInfo.setList(userPageList);
        return pageInfo;
    }

    @Override
    public int deleteLabOrder(int id) {
        int i = labOrderMapper.deleteLabOrder(id);
        return i;
    }

    public PageInfo<LabOrder> setFullAndOver(PageInfo<LabOrder> pageInfo) {
        if (pageInfo.getList().size() == 0){
            return pageInfo;
        }
        // TODO: 2021/4/18 ?????????????????????????????? ?????????????????????
        // ????????????????????????????????????

        //??????pageInfo ?????????????????????????????????????????????
        for(LabOrder tLO : pageInfo.getList()){
            int odd = tLO.getLoOdd();
            if (odd==0){
                tLO.setIsFull(Constant.TRUE);
            }else {
                tLO.setIsFull(Constant.FALSE);
            }
            long startTime = tLO.getStartTime().getTime();
            long endTime = tLO.getEndTime().getTime();
            long nowTime = new Date().getTime();
            if (nowTime<startTime){
                // ?????????????????? ?????????
                tLO.setLoState(Constant.NOT_START);
            }else if(nowTime < endTime){
                // ?????????????????? ?????????????????? ?????????
                tLO.setLoState(Constant.IN_HAND);
            }else {
                // ?????????????????? ?????????
                tLO.setLoState(Constant.IS_OVER);
            }
        }
        return pageInfo;
    }

    public boolean isCanOrder(LabOrder labOrder){
        // TODO: 2021/4/18  ??????????????? ????????????????????????????????????????????????  ?????? 4/19
        // ?????????????????????
        Integer lId = labOrder.getLId();
        Date startTime = labOrder.getStartTime();
        Date endTime = labOrder.getEndTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // ??????????????????????????????
        String theDay = df.format(startTime);
        String dayBegin = theDay + " 00:00:00";
        String dayEnd = theDay + " 23:59:59";
        Date dayBeginTime = null;
        Date dayEndTime = null;
        try {
            dayBeginTime = df2.parse(dayBegin);
            dayEndTime = df2.parse(dayEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ?????????????????????????????????????????????
        List<LabOrder> labOrders = labOrderMapper.labOrderInTime(lId, dayBeginTime, dayEndTime);
        // ??????????????????????????????
        for (LabOrder order : labOrders) {
            // ?????????????????????????????????????????????
            if (order.getStartTime().getTime() < startTime.getTime()){
                if (order.getEndTime().getTime() > startTime.getTime()){
                   return false;
                }
            }
            if (order.getStartTime().getTime() < endTime.getTime()){
                if (order.getEndTime().getTime() > endTime.getTime()){
                    return false;
                }
            }
            // ??????????????????????????????????????????
            if (order.getStartTime().getTime() > startTime.getTime()){
                if (order.getEndTime().getTime() < endTime.getTime()){
                    return false;
                }
            }
        }
        return true;
    }

}
