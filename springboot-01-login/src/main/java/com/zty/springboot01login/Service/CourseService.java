package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Mapper.CourseMapper;
import com.zty.springboot01login.Pojo.CourseLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseLabMapper courseLabMapper;

    public List<CourseLab> getCourseLabByCouseId(int courseId) {
        return courseLabMapper.selectByCourseId(courseId);
    }
}
