package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.entity.User;
import com.bdu.laborder.service.UserService;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qi
 * @data 2021/2/4 13:56
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public Result getUserList(@RequestBody PageQuery pageQuery){
        PageInfo<User> userList = userService.getUserList(pageQuery);
        return  ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,userList);
    }

    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable Integer id){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        User user = userService.getUserById(id);
        if (user == null){
            return result;
        }
        result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS,user);
        return result;
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody User user) {
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        String loginName = user.getLoginName();
        if (loginName.isEmpty()){
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_FIELD_NULL);
        }
        String password = loginName.substring(loginName.length()-6);
        user.setPassword(password);
        int i = userService.addUser(user);
        if (i != 0) {
            result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }

    @PutMapping("/user")
    public Result updateUser(@RequestBody User user){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        int i = userService.updateUser(user);
        if (i != 0) {
            result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }

    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable Integer id){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        int i = userService.deleteUser(id);
        if (i != 0) {
            result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }

    @PutMapping("/user/updatePwd")
    public Result updataPwd(HttpServletRequest request ){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        int i = userService.updatePwd(request);
        if (i != 0) {
            result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }

    @PutMapping("/resetPwd/{id}")
    public Result resetPwd(@PathVariable Integer id){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        int i = userService.restPwd(id);
        if (i != 0) {
            result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }



}
