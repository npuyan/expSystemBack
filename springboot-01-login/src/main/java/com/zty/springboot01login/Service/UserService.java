package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseRequestMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseRequestMapper courseRequestMapper;

    @Autowired
    UserCourseService userCourseService;
    @Autowired
    CourseService courseService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.err.println(user);
        return user;
    }

    /*通过id得到用户*/
    public User getByUserId(int userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    /*通过用户名得到用户*/
    public User getByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    /*添加用户*/
    public void addUser(User record) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder(10);
        record.setPassword(encode.encode(record.getPassword()));
        userMapper.insert(record);
    }

    /*通过用户名选择此用户已选择的所有课程*/
    public List<Course> getSelectedCourse(String username) {
        User user = userMapper.selectByUserName(username);
        int userId = user.getUserId();
        List<UserCourse> userCourses = userCourseService.getByUserId(userId);
        List<Course> courses = new LinkedList<>();
        for (UserCourse userCourse : userCourses) {
            courses.add(courseService.getByPrimaryKey(userCourse.getCourseId()));
        }
        return courses;
    }

    /*得到所有用户*/
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    /*通过用户id删除用户*/
    public boolean delUserById(@RequestParam() Integer id) throws Exception {
        userMapper.deleteByPrimaryKey(id);
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateUser(User user) throws Exception {
        userMapper.updateByPrimaryKey(user);
        return true;
    }

    /*当前登录教师的所有课程*/
    public List<Course> getCourseByTeacher(String username) {
        return courseService.getCourseByAuthor(username);
    }

    /*审核者得到本人需要审核的队列*/
    public List<Map<String, Object>> getCourseRequestQueue(String username) {
        User user = getByUserName(username);
        List<CourseRequest> courseRequests = courseRequestMapper.selectByCheckUserId(user.getUserId());
        courseRequests.sort(new Comparator<CourseRequest>() {
            @Override
            public int compare(CourseRequest o1, CourseRequest o2) {
                return o1.getState() - o2.getState();
            }
        });
        List<Map<String, Object>> joinList = new LinkedList<>();
        Field[] fields=CourseRequest.class.getDeclaredFields();
        System.err.println(fields.length);
        for(Field field:fields){
            System.err.println(field.getName());
        }
        for (CourseRequest courseRequest : courseRequests) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field field = fields[i];
                    field.setAccessible(true);
                    map.put(field.getName(),field.get(courseRequest));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            map.put("requestusername",getByUserId(courseRequest.getRequestUserId()).getUsername());
            map.put("coursename",courseService.getCourseByCourseId(courseRequest.getCourseId()).getCourseName());
            if(courseRequest.getCheckUserId()!=null) {
                map.put("checkusername", getByUserId(courseRequest.getCheckUserId()).getUsername());
            }
            joinList.add(map);
        }
        return joinList;
    }

    /*审核者审核申请，同意或者拒绝审核队列中的某一个申请*/
    public boolean checkCourseRequest(CourseRequest courseRequest, boolean agree) throws Exception {
        if (courseRequest.getState() == 0) {
            if (agree) {//同意申请
                switch (courseRequest.getRequestType()) {
                    case choose: {//选课
                        userCourseService.agreeChooseCourse(courseRequest);
                        break;
                    }
                    case drop: {
                        break;
                    }
                    case add: {
                        break;
                    }
                    case delete: {
                        break;
                    }
                    case update: {
                        break;
                    }
                    default:
                        throw new Exception("申请类别错误");
                }
                //同意申请，将状态改为1
                courseRequest.setState(1);
                courseRequest.setCheckTime(NowTimeFormat.NowTime());
                courseRequestMapper.updateByPrimaryKey(courseRequest);
                return true;
            } else {//不同意申请,将状态改为2
                courseRequest.setState(2);
                courseRequest.setCheckTime(NowTimeFormat.NowTime());
                courseRequestMapper.updateByPrimaryKey(courseRequest);
                return false;
            }
        } else {
            throw new Exception("请求状态不为待审核");
        }
    }
}