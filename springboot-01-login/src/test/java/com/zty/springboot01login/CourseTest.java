package com.zty.springboot01login;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Mapper.CourseMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CourseTest {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseLabMapper courseLabMapper;
    @Autowired
    CourseMapper courseMapper;
    @Test
    public void getCourseLabBycouseIdTest(){
        System.out.println(courseLabMapper.selectByCourseId(1).size());
        //System.out.println(courseService.getCourseLabBycouseId(1));
    }

    @Test
    public void getAllcourseTest(){
        courseService.getAllCourse();
    }

    @Test
    public void testaddcourse(){
        Course course=new Course();
        course.setCourseName("test1");
        course.setCreateTime(String.valueOf(new Date().getTime()));
        course.setAuthor("111");
        course.setType("11");
        int insert = courseMapper.insert(course);
        System.err.println(insert);
        System.err.println(courseMapper.selectLastInsertId());
    }
}
