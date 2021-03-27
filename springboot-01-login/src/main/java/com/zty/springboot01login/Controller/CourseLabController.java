package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.CourseLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    /*通过实验id得到实验*/
    @RequestMapping("/getcourselabbyid")
    public CourseLab getCourseLabById(@RequestBody Map<String, Object> param) {
        int id = JSON.parseObject(JSON.toJSONString(param.get("id")), Integer.class);
        return courseLabService.getCourseLabById(id);
    }

    /*通过实验id删除实验*/
    @RequestMapping("/delcourselabbyid")
    public RespBean delCourseLabById(@RequestBody Map<String, Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id = JSON.parseObject(JSON.toJSONString(param.get("id")), Integer.class);

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

    /*增加一条实验记录*/
    @PostMapping("/addcourselab")
    public RespBean addCourseLab(@RequestBody Map<String, Object> param) {
        String success = "插入成功";
        String failure = "插入异常";
        CourseLab courseLab = JSON.parseObject(JSON.toJSONString(param.get("courselab")), CourseLab.class);
        CourseLab courseLab1 = null;
        try {
            courseLab1 = courseLabService.addCourseLab(courseLab);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success, courseLab1);
    }

    /*通过文件名下载文件*/
    @RequestMapping("/downloadfile")
    public RespBean downloadLabFile(@RequestParam String filename, final HttpServletResponse response, final HttpServletRequest request) {
        return courseLabService.downloadFile(filename, response, request);
    }

    /*上传实验id和实验文件*/
    @RequestMapping("/uploadfile")
    public RespBean uploadLabFile(@RequestParam("labid") Integer labId, @RequestParam("file") MultipartFile multipartFiles, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        return courseLabService.uploadFile(labId, multipartFiles, response, request);
    }


}
