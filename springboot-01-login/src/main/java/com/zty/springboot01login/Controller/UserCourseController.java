package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Repository
@CrossOrigin
public class UserCourseController {
    @Autowired
    UserCourseService userCourseService;

    /*给某一用户选择某一课程*/
    @PostMapping(value = "/choosecourse")
    public RespBean chooseCourse(@RequestParam("username") String username,
                                 @RequestParam("courseid") int courseId) {
        String success = "选课成功";
        String beenChosen = "此课程已经选过";
        String failure = "选课失败";
        try {
            // 如果此学生已经选过此课程
            if(userCourseService.hasBeenChosen(username, courseId)) {
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
}
