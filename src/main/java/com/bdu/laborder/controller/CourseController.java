package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.ClassTime;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.service.CourseService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title
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
       courseTime.setCreateBy(getUserName());
        return toResult(courseService.insertTime(courseTime));
    }

    @PutMapping("/time")
    public Result updateTime(@RequestBody CourseTime courseTime){
        return toResult(courseService.updateTime(courseTime));
    }

    @GetMapping("/time")
    public Result getDefaultTime(){
        return success();
    }

    @PostMapping("/time/{id}")
    public Result creatNewVersion(@PathVariable String id){
        return success();
    }

    @DeleteMapping("/time/{id}")
    public Result removeTime(@PathVariable String id){
        return success();
    }
}
