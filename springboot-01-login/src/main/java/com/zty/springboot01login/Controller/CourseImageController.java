package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseImage;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.CourseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class CourseImageController {
    @Autowired
    CourseImageService courseImageService;

    /*得到所有镜像*/
    @RequestMapping("/getallcourseimage")
    public List<CourseImage> getAllcourseImage(@RequestParam(defaultValue = "10") Integer results,
                                               @RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "") String sortField,
                                               @RequestParam(defaultValue = "ascend") String sortOrder) {
        List<CourseImage> allcourseimage = courseImageService.getAllcourseImage();
        if (sortOrder.equals("descend")) {
            Collections.reverse(allcourseimage);
        }
        return allcourseimage;
    }

    /*通过镜像id删除课程*/
    @RequestMapping("/delcourseimagebyid")
    public RespBean delCourseImageById(@RequestBody Map<String, Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id = JSON.parseObject(JSON.toJSONString(param.get("id")), Integer.class);
        try {
            courseImageService.delCourseImageById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*根据记录中的id更新一条数据*/
    @PostMapping("/updatecourseimage")
    public RespBean updateCourseImage(@RequestBody String obj) {
        String success = "更新成功";
        String failure = "更新异常";
        CourseImage course = JSON.parseObject(obj, CourseImage.class);
        try {
            courseImageService.updateCourseImage(course);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*根据用户id得到本用户创建的所有镜像加上基础镜像*/
    @PostMapping("/getimagebycreatorid")
    public List<CourseImage> getCourseImageByCreatorId(@RequestBody Map<String, Object> param) {
        int creatorid = JSON.parseObject(JSON.toJSONString(param.get("creatorid")), Integer.class);
        return courseImageService.getCourseImageByCreatorId(creatorid);
    }
}
