package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.Course;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.service.CourseService;
import com.bdu.laborder.service.CourseTimeService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private CourseService courseService;
    @Autowired
    CourseTimeService timeService;

    /** ############################## 课程 start ################################ */

    @PostMapping("/list")
    public Result getCourseList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        Course course = getParam(pageQuery, Course.class);
        List<Course> courseList = courseService.getCourseList(course);
        return getPageInfo(courseList);
    }

    @GetMapping("/{id}")
    public Result getCourseInfo(@PathVariable("id") String id){
        return success(courseService.getCourseById(id));
    }

    @PostMapping
    public Result addCourse(@RequestBody Course course){
        course.setCreateBy(getUserName());
        return toResult(courseService.addCourse(course));
    }

    @PutMapping
    public Result editCourse(@RequestBody Course course){
        course.setCreateBy(getUserName());
        return toResult(courseService.updateCourse(course));
    }

    @DeleteMapping("/{ids}")
    public Result removeCourse(@PathVariable("ids") String[] ids){
        // 校验已分配不可删除
        return toResult(courseService.removeCourseByIds(ids));
    }

    /** ############################## 课程 start ################################ */


    /** ############################## 课程时间 start ################################ */

    @PostMapping("/times")
    public Result getTimeList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        CourseTime courseTime = getParam(pageQuery, CourseTime.class);
        List<CourseTime> timeList = timeService.getTimeList(courseTime);
        return getPageInfo(timeList);
    }

    @GetMapping("/time/{id}")
    public Result getTimeById(@PathVariable() String id){
        return success(timeService.getTime(id));
    }

    @PostMapping("/time")
    public Result addTime(@RequestBody CourseTime courseTime){
        if (checkTimeSize(courseTime)){
            return error("课程节数与设置时间数量不一致！");
        }
       courseTime.setCreateBy(getUserName());
        return toResult(timeService.insertTime(courseTime));
    }

    @PutMapping("/time")
    public Result updateTime(@RequestBody CourseTime courseTime){
        if (checkTimeSize(courseTime)){
            return error("课程节数与设置时间数量不一致！");
        }
        courseTime.setUpdateBy(getUserName());
        return toResult(timeService.updateTime(courseTime));
    }

    @PutMapping("/time/{id}")
    public Result setDefaultTime(@PathVariable String id){
        return toResult(timeService.setDefaultTime(id));
    }

    @PostMapping("/time/{id}")
    public Result creatNewVersion(@PathVariable String id){
        return toResult(timeService.creatNewVersion(id,getUserName()));
    }

    @DeleteMapping("/time/{ids}")
    public Result removeTime(@PathVariable String[] ids){
        return toResult(timeService.deleteTimes(ids));
    }

    /**
     *  校验课程时间节数与设置时间数量是否不一致
     * @param courseTime
     * @return
     */
    private boolean checkTimeSize(CourseTime courseTime){
        return courseTime.getNum() != courseTime.getTimes().size();
    }

    /** ############################## 课程时间 end ################################ */
}
