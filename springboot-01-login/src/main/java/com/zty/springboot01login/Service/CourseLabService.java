package com.zty.springboot01login.Service;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Mapper.CourseEnvMapper;
import com.zty.springboot01login.Mapper.CourseImageMapper;
import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Utils.PinyinComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class CourseLabService {
    @Autowired
    CourseLabMapper courseLabMapper;

    @Autowired
    CourseEnvService courseEnvService;
    /*
    根据用户名和实验查询对应的实验环境：
    如果该用户做过本实验，则启动并返回原有的容器环境
    如果该用户没有做过本实验，则启动并返回一个新的容器环境
    */
    public CourseEnv getCourseEnvByCourseLab(String username, CourseLab courseLab) {
        return null;
    }

    /*通过id的得到所有的实验*/
    public List<CourseLab> getAllcourseLab() {
        List<CourseLab> courseslabs = courseLabMapper.selectAll();
        return courseslabs;
    }

    /*通过实验id删除实验*/
    public boolean delCourseLabById(@RequestParam() Integer id) throws Exception {
        courseLabMapper.deleteByPrimaryKey(id);
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateCourseLab(CourseLab course) throws Exception {
        courseLabMapper.updateByPrimaryKey(course);
        return true;
    }

    public List<CourseLab> getByCourseId(int id) {
        List<CourseLab> courseslabs = courseLabMapper.selectByCourseId(id);
        return courseslabs;
    }
}
