package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    /*通过用户名得到本学生选过的所有课程*/
    @PostMapping(value = "/getselectedcourses")
    public List<Course> getSelectedCourses(@RequestBody Map<String,Object> param) {
//        return new LinkedList<>();
        String username= param.get("username").toString();
        return userService.getSelectedCourse(username);
    }

    /*得到所有的用户*/
    @RequestMapping("/getalluser")
    public List<User> getAllUser(@RequestParam(defaultValue = "10") Integer results,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "") String sortField,
                                 @RequestParam(defaultValue = "ascend") String sortOrder) {
        List<User> users = userService.getAllUser();
        if (sortOrder.equals("descend")) {
            Collections.reverse(users);
        }
        return users;
    }

    /*根据类别得到user*/
    public List<User> getUserBytype(@RequestParam(defaultValue = "10") Integer results,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "") String sortField,
                                    @RequestParam(defaultValue = "ascend") String sortOrder,
                                    String type) {
        List<User> allUser = getAllUser(results, page, sortField, sortOrder);
        List<User> allTypeUser = new LinkedList<>();
        for (User user : allUser) {
            if (user.getUserType().equals(type)) {
                allTypeUser.add(user);
                System.err.println(user);
            }
        }
        return allTypeUser;
    }

    /*通过课程id删除课程*/
    @RequestMapping("/deluserbyid")
    public RespBean delUserById(@RequestBody Map<String,Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id=JSON.parseObject(JSON.toJSONString(param.get("id")),Integer.class);

        try {
            userService.delUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*根据记录中的id更新一条数据*/
    @PostMapping("/updateuser")
    public RespBean updateCourse(@RequestBody String obj) {
        String success = "更新成功";
        String failure = "更新异常";
        User user = JSON.parseObject(obj, User.class);
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*得到所有的学生，学生类别是2*/
    @RequestMapping("/getallstudent")
    public List<User> getAllStudent(@RequestParam(defaultValue = "10") Integer results,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "") String sortField,
                                    @RequestParam(defaultValue = "ascend") String sortOrder) {
        return getUserBytype(results,page,sortField,sortOrder,"2");
    }
    /*得到所有的老师，老师类别是1*/
    @RequestMapping("/getallteacher")
    public List<User> getAllTeacher(@RequestParam(defaultValue = "10") Integer results,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "") String sortField,
                                    @RequestParam(defaultValue = "ascend") String sortOrder) {
        return getUserBytype(results,page,sortField,sortOrder,"1");
    }
}
