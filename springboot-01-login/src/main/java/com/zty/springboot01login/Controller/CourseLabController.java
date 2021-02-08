package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Service.CourseLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@Repository
@CrossOrigin
public class CourseLabController {
    @Autowired
    CourseLabService courseLabService;

    /*
        根据用户名和实验查询对应的实验环境：
        如果该用户做过本实验，则启动并返回原有的容器环境
        如果该用户没有做过本实验，则启动并返回一个新的容器环境
    */
    @PostMapping(value = "getcourseenvbycourselab")
    public CourseEnv getCourseEnvByCourseLab(@RequestParam("username") String username, @RequestBody CourseLab courseLab) {
        return courseLabService.getCourseEnvByCourseLab(username, courseLab);
    }
}
