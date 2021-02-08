package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Mapper.CourseMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Utils.PinyinComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseLabMapper courseLabMapper;

    /*得到所有的课程并排序*/
    public List<Course> getAllcourse() {
        List<Course> courses = courseMapper.selectAll();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                PinyinComparator pinyinComparator = new PinyinComparator();
                return pinyinComparator.compare(o1.getCourseName(), o2.getCourseName());
            }
        });
        for (Course course : courses) {
            System.out.println(course);
        }
        return courses;
    }

    /*通过课程id得到本课程所有的实验*/
    public List<CourseLab> getCourseLabByCouseId(int courseId) {
        return courseLabMapper.selectByCourseId(courseId);
    }
}
