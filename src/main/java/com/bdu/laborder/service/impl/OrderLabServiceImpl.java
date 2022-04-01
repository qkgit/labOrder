package com.bdu.laborder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.entity.LabOrder;
import com.bdu.laborder.entity.LabOrderDetil;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.LabOrderMapper;
import com.bdu.laborder.mapper.OrderLabMapper;
import com.bdu.laborder.service.OrderLabService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.JwtUtils;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/4/5 17:52
 */
@Service
public class OrderLabServiceImpl implements OrderLabService {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    LabOrderMapper labOrderMapper;
    @Autowired
    OrderLabMapper orderLabMapper;

    @Override
    public int orderLab(HttpServletRequest request) {
        String id = getUserId(request);
        // 获取实验室预约id
        JSONObject jsonParam = this.getJSONParam(request);
        String labOrderId = jsonParam.get("loId").toString();
        int loId = Integer.parseInt(labOrderId);
        LabOrder labOrder = labOrderMapper.getLabOrderById(loId);
        // TODO: 2021/4/5  判断该用户在该时间段内 有无预约记录  完成 4/20
        Date startTime = labOrder.getStartTime();
        Date endTime = labOrder.getEndTime();
        // 查询用户(已预约中)未开始的预约
        List<Integer> awaitLoIdList = orderLabMapper.getAwaitLoIdListByUser(id);
        // 遍历未开始的预约 查看时间是否冲突
        for (Integer awaitLoId : awaitLoIdList) {
            LabOrder awaitLabOrder = labOrderMapper.getLabOrderById(awaitLoId);
            if (awaitLabOrder.getStartTime().getTime() <= startTime.getTime()){
                if (awaitLabOrder.getEndTime().getTime() >= startTime.getTime()){
                    throw new LabOrderException(BussinessCode.ORDER_TIME_CLASH);
                }
            }
            if (awaitLabOrder.getStartTime().getTime() <= endTime.getTime()){
                if (awaitLabOrder.getEndTime().getTime() >= endTime.getTime()){
                    throw new LabOrderException(BussinessCode.ORDER_TIME_CLASH);
                }
            }
            if (awaitLabOrder.getStartTime().getTime() >= startTime.getTime()){
                if (awaitLabOrder.getEndTime().getTime() <= endTime.getTime()){
                    throw new LabOrderException(BussinessCode.ORDER_TIME_CLASH);
                }
            }
        }

        // 人数已满禁止预约
        if (labOrder.getLoOdd() == 0){
            // 余量为0 预约人数已满
            throw new LabOrderException(BussinessCode.LAB_NOT_ODD);
        }
        Integer newLoOdd = null;
        // 查询loType
        if (labOrder.getLoType().equals(Constant.OPEN_ORDER)){
            // 开放实验室余量-1
            newLoOdd = labOrder.getLoOdd()-1;
        }else {
            // 教学实验室余量=0
            newLoOdd = 0;
        }
        // 更新实验室预约表
        int i1 = labOrderMapper.updateOdd(loId,newLoOdd);
        // 将预约信息 放入预约详情表中
        if (i1 >0) {
            LabOrderDetil labOrderDetil = new LabOrderDetil();
            labOrderDetil.setLoId(loId);
            labOrderDetil.setUserId(id);
            labOrderDetil.setStartTime(labOrder.getStartTime());
            int i = orderLabMapper.userOrderLab(labOrderDetil);
            return i;
        }else {
            return 0;
        }
    }

    @Override
    public PageInfo<LabOrder> getOrderListByUser(HttpServletRequest request) {
        String id = getUserId(request);
        // 获取pageQuery
        JSONObject jsonParam = this.getJSONParam(request);
        Gson gson = CreateGson.createGson();
        PageQuery pageQuery = gson.fromJson(gson.toJson(jsonParam), PageQuery.class);
        PageInfo page = pageQuery.getPage();
        try {
            // 根据用户id 查询用户预约了哪些实验室
            List<String> loIdList = orderLabMapper.getLoIdListByUserId(id);
            List<LabOrder> userOrderList = labOrderMapper.getLabOrderInId(loIdList);

            // 先分页
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            PageInfo<LabOrder> loIdPageInfo = new PageInfo<>(userOrderList);
            setFullAndOver(loIdPageInfo);
            return loIdPageInfo;
        }catch (Exception e){
            return new PageInfo<>();
        }


    }

    @Override
    public int CancelOrder(HttpServletRequest request) {
        String id = getUserId(request);
        JSONObject jsonParam = this.getJSONParam(request);
        String labOrderId = jsonParam.get("loId").toString();
        int loId = Integer.parseInt(labOrderId);
        int i = orderLabMapper.deleteOrderByUser(loId);
        if (i > 0){
            // 取消成功 更新预约实验室余量
            LabOrder labOrder = labOrderMapper.getLabOrderById(loId);
            Integer newLoOdd = null;
            // 查询loType
            if (labOrder.getLoType().equals(Constant.OPEN_ORDER)){
                // 开放实验室余量+1
                newLoOdd = labOrder.getLoOdd()+1;
            }else {
                // 教学实验室余量=容量
                newLoOdd = labOrder.getLoCap();
            }
            labOrderMapper.updateOdd(loId,newLoOdd);

        }
        return i;
    }



    /**
     *  添加 满员、过期等信息
     * @param pageInfo
     * @return
     */
    public PageInfo<LabOrder> setFullAndOver(PageInfo<LabOrder> pageInfo) {
        if (pageInfo.getList().size() == 0){
            return pageInfo;
        }
        //遍历pageInfo 判断列表中实验室是否满员、过期
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
                // 早于开始时间 未开始
                tLO.setLoState(Constant.NOT_START);
            }else if(nowTime < endTime){
                // 大于开始时间 早于结束时间 进行中
                tLO.setLoState(Constant.IN_HAND);
            }else {
                // 晚于结束时间 已结束
                tLO.setLoState(Constant.IS_OVER);
            }
        }
        return pageInfo;
    }

    /**
     *  获取request中携带的参数
     * @param request
     * @return
     */
    public com.alibaba.fastjson.JSONObject getJSONParam(HttpServletRequest request){
        com.alibaba.fastjson.JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }

    /**
     *  获取用户id
     * @param request
     * @return
     */
    public String getUserId(HttpServletRequest request) {
        String userId = null;
        try {
            // 获取token
            String token = request.getHeader("X-Token");
            // 解析token
            Claims claims = jwtUtils.parseJwt(token);
            // 获取id
            userId = claims.getId();
        }catch (Exception e){
            // token解析失败 返回异常让用户重新登录
            throw new LabOrderException(BussinessCode.RESULT_INFO_FAIL);
        }
        return userId;
    }
}
