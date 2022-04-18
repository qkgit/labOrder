package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.Course;
import com.bdu.laborder.entity.CourseTable;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.service.CourseService;
import com.bdu.laborder.service.CourseTableService;
import com.bdu.laborder.service.CourseTimeService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private CourseTableService tableService;

    /** ############################## 课程 start ################################ */

    @PostMapping("/list")
    public Result getCourseList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        Course course = getParam(pageQuery, Course.class);
        List<Course> courseList = courseService.getCourseList(course);
        return getPageInfo(courseList);
    }

    @GetMapping("/getByName/{param}")
    public Result getCourseSelect(@PathVariable("param") String param){
        return success(courseService.getCourseListByNameOrLeader(param));
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

    /** ############################## 课程 end ################################ */


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

    @GetMapping("/defaultTime")
    public Result getDefaultTime(){
        return success(timeService.getDefaultTime());
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

    /** ############################## 课程表 start ################################ */

    @PostMapping("/table/list")
    public Result getTable(@RequestBody CourseTable table){
        // 必填项未填返回空数据
        if (StringUtils.isEmpty(table.getYear())
                ||StringUtils.isEmpty(table.getSemester())
                ||StringUtils.isEmpty(table.getDeptId())){
            return success();
        }
        return success(tableService.getCourseTableList(table));
    }

    @PostMapping("/table")
    public Result addTableInfo(@RequestBody CourseTable table){
        // 非空校验

        // 重复校验
        if (UserConstants.NOT_UNIQUE.equals(tableService.checkCourseTableUnique(table))){
            return error("添加失败！该班级课程表在该时间已存在课程！");
        }
        table.setUuid(UuidUtil.getUuid());
        table.setCreateBy(getUserName());
        return toResult(tableService.addCourseTable(table));
    }

    @GetMapping("/table/{id}")
    public Result getTableInfoById(@PathVariable String id){
        return success(tableService.getCourseTableById(id));
    }

    @PutMapping("/table")
    public Result editTableInfo(@RequestBody CourseTable table){
        // 重复校验
        if (UserConstants.NOT_UNIQUE.equals(tableService.checkCourseTableUnique(table))){
            return error("添加失败！该班级课程表在该时间已存在课程！");
        }
        table.setUpdateBy(getUserName());
        return toResult(tableService.updateCourseTable(table));
    }

    @DeleteMapping("/table/{ids}")
    public Result removeTableInfoByIds(@PathVariable String[] ids){
        return toResult(tableService.deleteCourseTableByIds(ids));
    }


    /**
     * 查询各楼层教室课表（预约使用）
     */


    /**
     * 查询用户课表
     *   1.老师  =>  所教课程的课表
     *   2.学生  =>  所在班级的课表
     */
    @GetMapping("/table")
    public Result getTableByUser(){
        // 获取用户数据
        SysUser loginUser = getLoginUser();
        String[] roleIds = loginUser.getRoleIds();
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        String nowYear = String.valueOf(now.getYear());
        int nowMonth = now.getMonthValue();
        Map<String,Object> tablesResponse = new HashMap<>();
        // 查询课表结果集
        List<CourseTable> courseTableList = new ArrayList<>();

        // 如果当前用户是：教师
        if(ArrayUtils.contains(roleIds, UserConstants.TEACHER_ROLE_ID)) {
            // 设置默认查询属性
            CourseTable table = new CourseTable();
            Course course = new Course();
            course.setLeaderId(loginUser.getUserId());
            table.setCourse(course);
            table.setYear(nowYear);
            table.setSemester(nowMonth > 6 ? Constant.SECOND_SEMESTER : Constant.FIRST_SEMESTER);
            // 查询课程表数据
            courseTableList = tableService.getCourseTableList(table);

        }
        // 如果当前用户是：学生
        if(ArrayUtils.contains(roleIds, UserConstants.STUDENT_ROLE_ID)) {
            // 设置默认查询属性
            CourseTable table = new CourseTable();
            table.setDeptId(loginUser.getDeptId());
            table.setYear(nowYear);
            table.setSemester(nowMonth > 6 ? Constant.SECOND_SEMESTER : Constant.FIRST_SEMESTER);
            // 查询课程表数据
            courseTableList = tableService.getCourseTableList(table);
        }
        // 设置返回数据
        if (!courseTableList.isEmpty()){
            CourseTime courseTime = timeService.getTime(courseTableList.get(0).getCourseTimeId());
            // 格式化时间
            List<String> timeList = courseTime.getTimes().stream()
                    .map(c -> c.getStartTime().toString() + "-" + c.getEndTime().toString())
                    .collect(Collectors.toList());
            // 添加到返回值中
            tablesResponse.put("courseTime",timeList);
            // 格式化课程
            List<CourseTable[]> courseTables = buildCourseList(courseTableList, courseTime.getNum());
            // 添加到返回值中
            tablesResponse.put("courses",courseTables);
        }
        return success(tablesResponse);

    }

    /**
     * 构建前端课程表数据
     * @param courseTableList  课程数据
     * @param num  课程节数
     * @return  前端课程表数据
     *  格式： （num =4）
     *         1   2   3   4
     *  周一： [xx,xxx,xxx,xxx]，
     *  周二： [xx,xxx,xxx,xxx]，
     *  周三： [xx,xxx,xxx,xxx]，
     *  周四： [xx,xxx,xxx,xxx]，
     *  周五： [xx,xxx,xxx,xxx]，
     *  周六： [xx,xxx,xxx,xxx]，
     *  周日： [xx,xxx,xxx,xxx]
     */
    private List<CourseTable[]> buildCourseList(List<CourseTable> courseTableList,int num){
        // 创建一个空的容器
        List<CourseTable[]> courseList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            courseList.add(i,new CourseTable[num]);
        }
        // 进行添加数据
        System.out.println("添加开始。。。");
        long startTime = new Date().getTime();
        courseTableList.forEach(c->{
            int weekIndex = Integer.valueOf(c.getWeek()) - 1;
            int nodeIndex = Integer.valueOf(c.getNode()) -1;
            CourseTable[] courseTables = courseList.get(weekIndex);
            courseTables[nodeIndex] = c;
        });
        long endTime = new Date().getTime();
        System.out.println("添加完成 耗时"+ (endTime - startTime));
        return courseList;
    }


    /** ############################## 课程表 end ################################ */



}
