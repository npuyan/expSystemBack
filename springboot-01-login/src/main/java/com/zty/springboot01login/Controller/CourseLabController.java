package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.CourseLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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

    /*得到所有的实验*/
    @RequestMapping("/getallcourselab")
    public List<CourseLab> getAllcourseLab(@RequestParam(defaultValue = "10") Integer results,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "") String sortField,
                                     @RequestParam(defaultValue = "ascend") String sortOrder) {
        List<CourseLab> allcourse = courseLabService.getAllcourseLab();
        if (sortOrder.equals("descend")) {
            Collections.reverse(allcourse);
        }
        return allcourse;
    }

    /*通过实验id删除实验*/
    @RequestMapping("/delcourselabbyid")
    public RespBean delCourseLabById(@RequestParam() Integer id) {
        String success = "删除成功";
        String failure = "删除异常";

        try {
            courseLabService.delCourseLabById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }
    /*根据记录中的id更新一条数据*/
    @PostMapping("/updatecourselab")
    public RespBean updateCourseLab(@RequestBody String obj) {
        String success = "更新成功";
        String failure = "更新异常";
        CourseLab courseLab = JSON.parseObject(obj, CourseLab.class);
        try {
            courseLabService.updateCourseLab(courseLab);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }
}
