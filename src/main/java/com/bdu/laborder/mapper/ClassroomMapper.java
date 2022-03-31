package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/24 16:45
 */
@Mapper
@Repository
public interface ClassroomMapper {

    List<Classroom> selectClassroomList(Classroom classroom);

    Classroom getClassroom(String id);

    int insertClassroom(Classroom classroom);

    int updateClassroom(Classroom classroom);

    int deleteClassroomByIds(String[] ids);

    Classroom checkAddressUnique(Classroom classroom);

    List<Classroom> selectRoomByNameOrAdd(String param);
}
