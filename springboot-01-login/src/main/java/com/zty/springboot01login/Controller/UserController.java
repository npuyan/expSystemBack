package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@Repository
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/getselectedcourses")
    public List<Course> getSelectedCourses(@RequestParam("username")String username){
//        return new LinkedList<>();
        return userService.getSelectedCourse(username);
    }
}
