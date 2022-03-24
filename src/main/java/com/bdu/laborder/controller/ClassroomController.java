package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.service.ClassroomService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/list")
    public Result getRoomList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        Classroom classroom = getParam(pageQuery, Classroom.class);
        List<Classroom> roomList = classroomService.getRoomList(classroom);
        return getPageInfo(roomList);
    }

    @GetMapping("/{uuid}")
    public Result getInfo(@PathVariable String uuid){
        return success(classroomService.getRoomById(uuid));
    }

    @PostMapping
    public Result add(@RequestBody Classroom classroom){
        //校验地址不能重复
        if (StringUtils.isNotEmpty(classroom.getAddress())
                && UserConstants.NOT_UNIQUE.equals(classroomService.checkAddressUnique(classroom))){
            return error("修改教室 "+ classroom.getName()+" 失败, "+classroom.getAddress()+" 已存在教室");
        }
        classroom.setCreateBy(getUserName());
        return toResult(classroomService.addClassroom(classroom));
    }

    @PutMapping
    public Result edit(@RequestBody Classroom classroom){
        //校验地址不能重复
        if (StringUtils.isNotEmpty(classroom.getAddress())
            && UserConstants.NOT_UNIQUE.equals(classroomService.checkAddressUnique(classroom))){
            return error("修改教室 "+ classroom.getName()+" 失败, "+classroom.getAddress()+" 已存在教室");
        }
       classroom.setUpdateBy(getUserName());
        return toResult(classroomService.updateClassroom(classroom));
    }

    @DeleteMapping("/{ids}")
    public Result deleteClassroom(@PathVariable String[] ids){
        return toResult(classroomService.deleteClassroomByIds(ids));
    }
}
