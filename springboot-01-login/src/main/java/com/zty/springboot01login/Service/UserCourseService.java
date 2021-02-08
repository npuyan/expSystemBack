package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Pojo.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseService {

    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserCourse userCourse;
    public void chooseCourse(String username, int courseId) throws Exception{
        User user = userMapper.selectByUserName(username);
        int userId = user.getUserId();
        userCourse.setUserId(userId);
        userCourse.setCourseId(courseId);
        userCourseMapper.insert(userCourse);
    }

    public boolean hasBeenChosen(String username,int courseId){
        User user = userMapper.selectByUserName(username);
        int userId = user.getUserId();
        UserCourse userCourse = userCourseMapper.selectByUserIdAndCourseId(userId, courseId);
        if(userCourse==null){
            return false;
        }
        return true;
    }
}
