package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.CourseTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2022/3/27 15:56
 */
@Mapper
@Repository
public interface CourseTableMapper {

    List<CourseTable> getCourseTableList(CourseTable courseTable);
}
