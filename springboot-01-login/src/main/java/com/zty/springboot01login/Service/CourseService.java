package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Mapper.CourseMapper;
import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Utils.PinyinComparator;
import com.zty.springboot01login.Utils.SftpOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseLabService courseLabService;
    @Autowired
    UserCourseService userCourseService;

    @Autowired
    Course course;

    public Course getByPrimaryKey(int id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    /*得到所有的课程并排序*/
    public List<Course> getAllCourse() {
        List<Course> courses = courseMapper.selectAll();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                PinyinComparator pinyinComparator = new PinyinComparator();
                return pinyinComparator.compare(o1.getCourseName(), o2.getCourseName());
            }
        });
//        for (Course course : courses) {
//            System.out.println(course);
//        }
        return courses;
    }

    /*通过课程id得到本课程所有的实验*/
    public List<CourseLab> getCourseLabByCouseId(int courseId) {
        return courseLabService.getByCourseId(courseId);
    }

    /*通过课程id删除课程*/
    public boolean delCourseById(@RequestParam() Integer id) throws Exception {
        courseMapper.deleteByPrimaryKey(id);
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateCourse(Course course) throws Exception {
        courseMapper.updateByPrimaryKey(course);
        return true;
    }

    /*增加一条课程记录*/
    public boolean addCourse(Course course) throws Exception {
        course.setCreateTime(String.valueOf(new Date().getTime()));
        courseMapper.insert(course);
        return true;
    }

    /*根据课程作者得到所有课程*/
    public List<Course> getCourseByAuthor(String username) {
        return courseMapper.selectByAuthor(username);
    }

    /*上传课程图片*/
    public Object uploadImage(@RequestParam("courseid") Integer courseid, @RequestParam("file") MultipartFile multipartFiles, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        Course course1 = courseMapper.selectByPrimaryKey(courseid);
        if (course1 == null) {
            throw new Exception("课程不存在");
        }
        try {
            if (multipartFiles != null) {
                String filename = course1.getCourseId() + "_" + course1.getCourseName() + ".jpg";
                /*上传文件*/
                SftpOperator.uploadFile(filename, multipartFiles, response, request);
                /*改变course的doc*/
                course1.setPicture(filename);
                courseMapper.updateByPrimaryKey(course1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("上传成功");
    }

    /*下载课程图片*/
    public Object downloadImage(@RequestParam String filename, final HttpServletResponse response, final HttpServletRequest request) {
        return SftpOperator.downloadFile(filename, response, request);
    }
}
