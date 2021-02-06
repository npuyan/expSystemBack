package com.zty.springboot01login;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseTest {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseLabMapper courseLabMapper;
    @Test
    public void getCourseLabBycouseIdTest(){
        System.out.println(courseLabMapper.selectByCourseId(1).size());
        //System.out.println(courseService.getCourseLabBycouseId(1));
    }
}
