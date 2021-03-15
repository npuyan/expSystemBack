package com.zty.springboot01login.Controller;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseImage;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.CourseEnvService;
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
public class CourseEnvController {

    @Autowired
    CourseEnvService courseEnvService;

    @Autowired
    CourseEnv courseEnv;

    /*返回所有的环境信息*/
    @GetMapping("/getallcourseenv")
    public List<CourseEnv> getAllCourseEnv(@RequestParam(defaultValue = "10") Integer results,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "") String sortField,
                                           @RequestParam(defaultValue = "ascend") String sortOrder) {
        List<CourseEnv> courseEnvs = courseEnvService.getAllCourseEnv();
        if (sortOrder.equals("descend")) {
            Collections.reverse(courseEnvs);
        }
        return courseEnvs;
    }

    @RequestMapping("/delcourseenvbyid")
    public RespBean delCourseEnvById(@RequestBody Map<String, Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id = JSON.parseObject(JSON.toJSONString(param.get("id")), Integer.class);
        try {
            courseEnvService.delCourseEnvById(id);
        } catch (Exception e) {
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    @PostMapping("/updatecourseenv")
    public RespBean updateCourseEnv(@RequestBody String obj) {
        String success = "更新成功";
        String failure = "更新异常";
        CourseEnv courseEnv = JSON.parseObject(obj, CourseEnv.class);
        try {
            courseEnvService.updateCourseEnv(courseEnv);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*教师通过某一镜像给某一实验创建一个环境.打开这个环境并返回端口*/
    @PostMapping("/addcourseenv")
    public RespBean addCourseEnv(@RequestBody Map<String, Object> param) {
        /*得到目标实验，目标镜像和要创建的环境基本信息*/
        CourseLab courseLab = JSON.parseObject(JSON.toJSONString(param.get("courselab")), CourseLab.class);
        CourseImage courseImage = JSON.parseObject(JSON.toJSONString(param.get("courseimage")), CourseImage.class);
        CourseEnv courseEnv = JSON.parseObject(JSON.toJSONString(param.get("courseenv")), CourseEnv.class);
        Integer port = 0;
        try {
            port = courseEnvService.addCourseEnv(courseLab, courseImage, courseEnv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("创建环境成功", port);
    }

    @PostMapping("/savecourseenvtoimage")
    public RespBean saveCourseEnvToImage(@RequestBody Map<String, Object> param) {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        CourseEnv courseEnv = JSON.parseObject(JSON.toJSONString(param.get("courseenv")), CourseEnv.class);
        courseEnvService.saveCourseEnvToImage(username, courseEnv);
        return RespBean.ok("保存镜像成功");
    }
}
