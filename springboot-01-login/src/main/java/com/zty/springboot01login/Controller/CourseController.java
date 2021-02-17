package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class CourseController {
    @Autowired
    CourseService courseService;

    /*通过课程id得到本课程所有的实验*/
    @RequestMapping("/getlabbycourseid")
    public List<CourseLab> getCourseLabBycourseId(@RequestBody Map<String,Object> param) {
        int courseId=JSON.parseObject(JSON.toJSONString(param.get("courseid")),Integer.class);
        return courseService.getCourseLabByCouseId(courseId);
    }

    /*得到所有的课程并按照拼音排序*/
    @RequestMapping("/getallcourse")
    public List<Course> getAllCourse(@RequestParam(defaultValue = "10") Integer results,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "") String sortField,
                                     @RequestParam(defaultValue = "ascend") String sortOrder) {
        List<Course> allcourse = courseService.getAllCourse();
        if (sortOrder.equals("descend")) {
            Collections.reverse(allcourse);
        }
        return allcourse;
    }

    /*通过课程id删除课程*/
    @RequestMapping("/delcoursebyid")
    public RespBean delCourseById(@RequestBody Map<String,Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id=JSON.parseObject(JSON.toJSONString(param.get("id")),Integer.class);
        System.err.println(id);
        try {
            courseService.delCourseById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*根据记录中的id更新一条数据*/
    @PostMapping("/updatecourse")
    public RespBean updateCourse(@RequestBody String obj) {
        String success = "更新成功";
        String failure = "更新异常";
        Course course = JSON.parseObject(obj, Course.class);
        try {
            courseService.updateCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }
}
