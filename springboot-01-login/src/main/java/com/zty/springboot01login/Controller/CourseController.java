package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zty.springboot01login.Pojo.*;
import com.zty.springboot01login.Service.CourseLabService;
import com.zty.springboot01login.Service.CourseService;
import com.zty.springboot01login.Service.UserCourseService;
import com.zty.springboot01login.Service.UserService;
import com.zty.springboot01login.Utils.SftpOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.ObjectView;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    UserCourseService userCourseService;
    @Autowired
    CourseLabService courseLabService;
    @Autowired
    UserService userService;

    /*通过课程id得到本课程所有的实验*/
    @RequestMapping("/getlabbycourseid")
    public List<CourseLab> getCourseLabBycourseId(@RequestBody Map<String, Object> param) {
        int courseId = JSON.parseObject(JSON.toJSONString(param.get("courseid")), Integer.class);
        return courseService.getCourseLabByCouseId(courseId);
    }

    /*选择当前课程的所有学生*/
    @RequestMapping("getallstudentbycourse")
    public List<User> getAllstudentByCourse(@RequestBody Map<String, Object> param) {
        int courseId = JSON.parseObject(JSON.toJSONString(param.get("courseid")), Integer.class);
        List<UserCourse> userCourses = userCourseService.getByCourseId(courseId);
        List<User> users = new LinkedList<>();
        for (UserCourse userCourse : userCourses) {
            users.add(userService.getByUserId(userCourse.getUserId()));
        }
        return users;
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
    public RespBean delCourseById(@RequestBody Map<String, Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id = JSON.parseObject(JSON.toJSONString(param.get("id")), Integer.class);
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

    /*增加一条课程记录*/
    @PostMapping("/addcourse")
    public RespBean addCourse(@RequestBody Map<String, Object> param) {
        String success = "插入成功";
        String failure = "插入异常";
        Course course = JSON.parseObject(JSON.toJSONString(param.get("course")), Course.class);
        try {
            courseService.addCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*上传课程图片*/
    @RequestMapping("/uploadcoursepicture")
    public Object uploadPicture(@RequestParam("courseid") Integer courseid, @RequestParam("file") MultipartFile multipartFiles, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        return courseService.uploadImage(courseid, multipartFiles, response, request);
    }

    /*下载课程图片*/
    @RequestMapping("/downloadcoursepicture")
    public Object downloadPicture(@RequestParam String filename, final HttpServletResponse response, final HttpServletRequest request) {
        return SftpOperator.downloadFile(filename, response, request);
    }
}
