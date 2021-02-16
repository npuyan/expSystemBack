package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseMapper;
import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Pojo.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import sun.font.CoreMetrics;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper mapper;
    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    CourseMapper courseMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = mapper.selectByUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.err.println(user);
        return user;
    }

    /*添加用户*/
    public void addUser(User record) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder(10);
        record.setPassword(encode.encode(record.getPassword()));
        mapper.insert(record);
    }

    /*通过用户名选择此用户已选择的所有课程*/
    public List<Course> getSelectedCourse(String username) {
        User user = mapper.selectByUserName(username);
        int userId = user.getUserId();
        List<UserCourse> userCourses = userCourseMapper.selectByUserId(userId);
        List<Course> courses = new LinkedList<>();
        for (UserCourse userCourse : userCourses) {
            courses.add(courseMapper.selectByPrimaryKey(userCourse.getCourseId()));
        }
        return courses;
    }

    /*得到所有用户*/
    public List<User> getAllUser(){
        return mapper.selectAll();
    }

    /*通过用户id删除用户*/
    public boolean delUserById(@RequestParam() Integer id) throws Exception {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateUser(User user) throws Exception {
        mapper.updateByPrimaryKey(user);
        return true;
    }
}