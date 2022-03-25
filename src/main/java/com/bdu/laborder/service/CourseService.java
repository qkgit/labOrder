package com.bdu.laborder.service;


import com.bdu.laborder.entity.Course;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/3/24 16:56
 */
public interface CourseService {

   public List<Course> getCourseList(Course course);

   public Course getCourseById(String id);

   public int addCourse(Course course);

   public int updateCourse(Course course);

   public int removeCourseByIds(String[] ids);
}
