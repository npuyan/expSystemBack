package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Pojo.UserCourse;
import com.zty.springboot01login.Service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class UserCourseController {
    @Autowired
    UserCourseService userCourseService;

    /*返回所有的选课信息*/
    @GetMapping(value = "/getallusercourse")
    public List<UserCourse> getAllUserCourse(@RequestParam(defaultValue = "10") Integer results,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "") String sortField,
                                             @RequestParam(defaultValue = "ascend") String sortOrder) {
        List<UserCourse> allUserCourse = userCourseService.getAllUserCourse();
        allUserCourse.sort(new Comparator<UserCourse>() {
            @Override
            public int compare(UserCourse o1, UserCourse o2) {
                return o1.getId() - o2.getId();
            }
        });
        if (sortOrder.equals("descend")) {
            Collections.reverse(allUserCourse);
        }
        return allUserCourse;
    }

    /*给某一用户选择某一课程*/
    @PostMapping(value = "/choosecourse")
    public RespBean chooseCourse(@RequestBody Map<String, Object> param) {
        String success = "选课成功";
        String beenChosen = "此课程已经选过";
        String failure = "选课失败";
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        int courseId = JSON.parseObject(JSON.toJSONString(param.get("courseid")), Integer.class);

        try {
            // 如果此学生已经选过此课程
            if (userCourseService.hasBeenChosen(username, courseId)) {
                return RespBean.error(beenChosen);
            }
            // 如果此学生没有选过此课程，则选课
            userCourseService.chooseCourse(username, courseId);
            return RespBean.ok(success);
        } catch (Exception e) {
            // 选课异常
            e.printStackTrace();
            return RespBean.error(failure);
        }
    }

    /*给某一用户退掉某一课程*/
    @PostMapping(value = "/dropcourse")
    public RespBean dropCourse(@RequestBody Map<String, Object> param) {

        String success = "退课成功";
        String failure = "退课异常";
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        int courseId = JSON.parseObject(JSON.toJSONString(param.get("courseid")), Integer.class);

        try {
            userCourseService.dropCourse(username, courseId);
            return RespBean.ok(success);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
    }

    /*根据选课id退课*/
    @PostMapping(value = "/dropcoursebyid")
    public RespBean dropCourseByid(@RequestBody Map<String,Object> param) {
        String success = "删除成功";
        String failure = "删除异常";
        int id=JSON.parseObject(JSON.toJSONString(param.get("id")),Integer.class);
        try {
            userCourseService.dropCourseByid(id);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }

    /*根据记录中的id更新一条数据*/
    //TODO 更改传参方式，能否直接传对象参数，这样太麻烦
    @PostMapping(value = "/updateusercourse")
    public RespBean updateUserCourse(@RequestBody String obj) {
        String success = "更新成功";
        String failure = "更新异常";
        UserCourse userCourse = JSON.parseObject(obj).toJavaObject(UserCourse.class);
        try {
            userCourseService.updateUserCourse(userCourse);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(failure);
        }
        return RespBean.ok(success);
    }
}
