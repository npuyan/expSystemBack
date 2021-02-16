package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseEnvMapper;
import com.zty.springboot01login.Pojo.CourseEnv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CourseEnvService {

    @Autowired
    CourseEnvMapper courseEnvMapper;

    public List<CourseEnv> getAllCourseEnv() {
        return courseEnvMapper.selectAll();
    }

    public boolean delCourseEnvById(@RequestParam() Integer id) throws Exception{
        courseEnvMapper.deleteByPrimaryKey(id);
        return true;
    }

    public boolean updateCourseEnv(CourseEnv courseEnv) throws Exception{
        courseEnvMapper.updateByPrimaryKey(courseEnv);
        return true;
    }
}
