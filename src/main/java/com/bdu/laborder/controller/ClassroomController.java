package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.service.ClassroomService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/18 14:04
 */
@RestController
@RequestMapping("/room")
public class ClassroomController extends BaseController {

    @Autowired
    ClassroomService classroomService;

    @PostMapping("/rooms")
    public Result getRoomList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        Classroom classroom = getParam(pageQuery, Classroom.class);
        List<Classroom> roomList = classroomService.getRoomList(classroom);
        return getPageInfo(roomList);
    }
}
