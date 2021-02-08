package com.zty.springboot01login;

import com.zty.springboot01login.Controller.UserCourseController;
import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Pojo.UserCourse;
import com.zty.springboot01login.Service.UserCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserCourseTest {
    @Autowired
    UserCourseController userCourseController;
    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    UserCourseService userCourseService;
    @Autowired
    UserCourse userCourse;
    @Test
    public void  chooseCourseTest(){
//        System.out.println(userCourseService.hasBeenChosen("111", 1));
//        userCourse.setUserId(2);
//        userCourse.setCourseId(2);
//        userCourseMapper.insert(userCourse);
        System.out.println(userCourseController.chooseCourse("111", 1));
    }
}
