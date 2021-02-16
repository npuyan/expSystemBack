package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Pojo.UserCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserCourseService {

    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserCourse userCourse;

    /*返回所有的选课信息*/
    public List<UserCourse> getAllUserCourse(){
        return userCourseMapper.selectAll();
    }

    /*给某一用户选择某一课程*/
    public void chooseCourse(String username, int courseId) throws Exception {
        User user = userMapper.selectByUserName(username);
        int userId = user.getUserId();
        userCourse.setUserId(userId);
        userCourse.setCourseId(courseId);
        userCourseMapper.insert(userCourse);
    }

    /*给某一用户退掉某一课程*/
    public void dropCourse(String username, int courseId) throws Exception {
        User user = userMapper.selectByUserName(username);
        int userId = user.getUserId();
        UserCourse userCourse = userCourseMapper.selectByUserIdAndCourseId(userId, courseId);
        if(userCourse!=null){
            userCourseMapper.deleteByPrimaryKey(userCourse.getId());
        }else {
            throw new Exception("未选择此课程");
        }
    }
    /*根据选课id退课*/
    public boolean dropCourseByid(Integer id) throws Exception{
        userCourseMapper.deleteByPrimaryKey(id);
        return true;
    }
    /*判断某一用户是否选过某一课程*/
    public boolean hasBeenChosen(String username, int courseId) {
        User user = userMapper.selectByUserName(username);
        int userId = user.getUserId();
        UserCourse userCourse = userCourseMapper.selectByUserIdAndCourseId(userId, courseId);
        if (userCourse == null) {
            return false;
        }
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateUserCourse(UserCourse userCourse) throws Exception{
        userCourseMapper.updateByPrimaryKey(userCourse);
        return true;
    }
}
