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

    /**
     *  根据教室id获取教室信息
     * @param id
     * @return
     */
    public Classroom getRoomById(String id);

    /**
     * 新增教室
     * @param classroom
     * @return
     */
    public int addClassroom(Classroom classroom);

    /**
     *  更新教室信息
     * @param classroom
     * @return
     */
    public int updateClassroom(Classroom classroom);

    /**
     *  批量删除教室
     * @param ids
     * @return
     */
    public int deleteClassroomByIds(String[] ids);

    /**
     *  校验教室地址
     * @param classroom
     * @return
     */
    String checkAddressUnique(Classroom classroom);
}
