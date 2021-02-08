package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Repository
@CrossOrigin
public class CourseController {
    @Autowired
    CourseService courseService;

    /*得到所有的课程并按照拼音排序*/
    @PostMapping("/getallcourse")
    public List<Course> getAllcourse() {
        return courseService.getAllcourse();
    }

    /*通过课程id得到本课程所有的实验*/
    @PostMapping("/getlabbycourseid")
    public List<CourseLab> getCourseLabBycourseId(@RequestParam("courseid") int courseId) {
        return courseService.getCourseLabByCouseId(courseId);
    }

}
