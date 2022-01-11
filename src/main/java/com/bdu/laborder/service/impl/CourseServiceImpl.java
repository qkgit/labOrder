package com.bdu.laborder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdu.laborder.entity.ClassTime;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.CourseTimeMapper;
import com.bdu.laborder.service.CourseService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.UuidUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/6 11:28
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseTimeMapper courseTimeMapper;
    @Override
    public List<CourseTime> getTimeList(CourseTime courseTime) {
        List<CourseTime> courseTimes = courseTimeMapper.selectTimeList(courseTime);
        return courseTimes;
    }

    @Override
    public CourseTime getTime(String id) {
        CourseTime courseTime = courseTimeMapper.getTimeById(id);
        String jsonClassTime = courseTime.getClassTime();
        // json --> list
        try {
            Gson gson = CreateGson.createGson();
            Type collectionType = new TypeToken<ArrayList<ClassTime>>(){}.getType();
            List<ClassTime> times = gson.fromJson(jsonClassTime, collectionType);
            courseTime.setTimes(times);
            return courseTime;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw new LabOrderException("解析时间出错");
        }
    }

    @Override
    public int insertTime(CourseTime courseTime) {
        // 设置uuid
        courseTime.setUuid(UuidUtil.getUuid());
        // 设置版本号
        courseTime.setVersion(courseTimeMapper.getMaxVersion()+1);
        // list --> jsonString
        // 将时间段转换为json 存入classTime中
        Gson gson = CreateGson.createGson();
        courseTime.setClassTime(gson.toJson(courseTime.getTimes()));
        return courseTimeMapper.addCourseTime(courseTime);
    }

    @Override
    public int updateTime(CourseTime courseTime) {
        return 0;
    }
}
