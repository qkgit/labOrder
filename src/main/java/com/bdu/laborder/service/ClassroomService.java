package com.bdu.laborder.service;

import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.entity.CourseTime;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/24 17:30
 */
public interface ClassroomService {

    /**
     *  获取教室列表
     * @param classroom
     * @return
     */
    public List<Classroom> getRoomList(Classroom classroom);
}
