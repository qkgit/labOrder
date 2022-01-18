package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.service.CourseService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Title   课程管理
 * @Author Qi
 * @data 2022/1/6 10:27
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/times")
    public Result getTimeList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        CourseTime courseTime = getParam(pageQuery, CourseTime.class);
        List<CourseTime> timeList = courseService.getTimeList(courseTime);
        for (CourseTime time : timeList) {
            System.out.println(time);
            time.setCreateTime(new Date());
        }
        return getPageInfo(timeList);
    }

    @GetMapping("/time/{id}")
    public Result getTimeById(@PathVariable() String id){
        return success(courseService.getTime(id));
    }

    @PostMapping("/time")
    public Result addTime(@RequestBody CourseTime courseTime){
        if (checkTimeSize(courseTime)){
            return error("课程节数与设置时间数量不一致！");
        }
       courseTime.setCreateBy(getUserName());
        return toResult(courseService.insertTime(courseTime));
    }

    @PutMapping("/time")
    public Result updateTime(@RequestBody CourseTime courseTime){
        if (checkTimeSize(courseTime)){
            return error("课程节数与设置时间数量不一致！");
        }
        courseTime.setUpdateBy(getUserName());
        return toResult(courseService.updateTime(courseTime));
    }

    @PutMapping("/time/{id}")
    public Result setDefaultTime(@PathVariable String id){
        return toResult(courseService.setDefaultTime(id));
    }

    @PostMapping("/time/{id}")
    public Result creatNewVersion(@PathVariable String id){
        return toResult(courseService.creatNewVersion(id,getUserName()));
    }

    @DeleteMapping("/time/{ids}")
    public Result removeTime(@PathVariable String[] ids){
        return toResult(courseService.deleteTimes(ids));
    }


    /**
     *  校验课程时间节数与设置时间数量是否不一致
     * @param courseTime
     * @return
     */
    private boolean checkTimeSize(CourseTime courseTime){
        return courseTime.getNum() != courseTime.getTimes().size();
    }
}