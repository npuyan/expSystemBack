package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Repository
@CrossOrigin
public class CourseController {
    @Autowired
    CourseService courseService;
    @PostMapping("/getlabbycouseid")
    public List<CourseLab> getCourseLabBycouseId(@RequestParam("courseid") int courseId){
        return courseService.getCourseLabByCouseId(courseId);
    }
}
