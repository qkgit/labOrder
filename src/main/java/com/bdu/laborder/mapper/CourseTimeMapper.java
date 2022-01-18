package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.CourseTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/5 17:11
 */
@Mapper
@Repository
public interface CourseTimeMapper {

    List<CourseTime> selectTimeList(CourseTime time);

    CourseTime getTimeById(String id);

    int getMaxVersion();

    CourseTime getDefaultTime();

    int addCourseTime(CourseTime courseTime);

    int updateCourseTime(CourseTime courseTime);

    int updateCourseTimeDefault(@Param("id") String id, @Param("value") String value);

    int deletCourseTimeByIds(String[] ids);
}
