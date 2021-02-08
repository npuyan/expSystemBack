package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseEnvMapper;
import com.zty.springboot01login.Mapper.CourseImageMapper;
import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseLabService {
    @Autowired
    CourseLabMapper courseLabMapper;
    @Autowired
    CourseEnvMapper courseEnvMapper;
    @Autowired
    CourseImageMapper courseImageMapper;

    /*
    根据用户名和实验查询对应的实验环境：
    如果该用户做过本实验，则启动并返回原有的容器环境
    如果该用户没有做过本实验，则启动并返回一个新的容器环境
    */
    public CourseEnv getCourseEnvByCourseLab(String username, CourseLab courseLab){
        return null;
    }
}
