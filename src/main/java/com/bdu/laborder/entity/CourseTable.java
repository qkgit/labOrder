package com.bdu.laborder.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;
import com.bdu.laborder.common.core.domain.entity.SysDept;

import java.io.Serializable;

/**
 * @Title 课程表实体类
 * @Author Qi
 * @data 2022/3/26 10:27
 */
public class CourseTable extends BaseEntity implements Serializable {
    /** ID */
    private String uuid;
    // 时间
    /** 学年 */
    private String year;
    /** 学期 */
    private String semester;
    /** 开始周 */
    private String periodStart;
    /** 结束周 */
    private String periodEnd;
    /** 周几 */
    private String week;
    /** 单双周 */
    private String limitWeek;
    /** 节数 */
    private Integer node;
    /** 课程时间id */
    private String courseTimeId;
    // 地点
    /** 教室 */
    private String classRoomId;
    private Classroom classroom;
    // 人物
    /** 课程 */
    private String courseId;
    private Course course;
    /** 班级 */
    private String deptId;
    private SysDept dept;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public String getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(String courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public String getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(String classRoomId) {
        this.classRoomId = classRoomId;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public String getLimitWeek() {
        return limitWeek;
    }

    public void setLimitWeek(String limitWeek) {
        this.limitWeek = limitWeek;
    }
}
