package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseRequestMapper;
import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Pojo.CourseRequest;
import com.zty.springboot01login.Pojo.NowTimeFormat;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Pojo.UserCourse;
import com.zty.springboot01login.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseService {

    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    CourseRequestMapper courseRequestMapper;

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserScoreService userScoreService;

    @Autowired
    UserCourse userCourse;
    @Autowired
    CourseRequest courseRequest;

    /*返回所有的选课信息*/
    public List<UserCourse> getAllUserCourse() {
        return userCourseMapper.selectAll();
    }

    /*通过用户id得到所有的已选课程*/
    public List<UserCourse> getByUserId(int userId) {
        return userCourseMapper.selectByUserId(userId);
    }

    /*通过课程id得到所有的已选课程*/
    public List<UserCourse> getByCourseId(int courseId) {
        return userCourseMapper.selectByCourseaId(courseId);
    }

    /*给某一用户选择某一课程,添加到请求队列当中*/
    public void chooseCourse(String username, int courseId) throws Exception {
        User user = userService.getByUserName(username);
        int userId = user.getUserId();
        courseRequest.setRequestType(RequestType.choose);
        courseRequest.setRequestUserId(user.getUserId());
        courseRequest.setCourseId(courseId);
        courseRequest.setCheckUserId(userService.getByUserName(courseService.getByPrimaryKey(courseId).getAuthor()).getUserId());
        courseRequest.setState(0);
        /*添加申请时间*/
        courseRequest.setRequestTime(NowTimeFormat.NowTime());
        courseRequestMapper.insert(courseRequest);
    }

    /*同意给某一用户选择某一课程,添加到选课表当中*/
    public void agreeChooseCourse(CourseRequest courseRequest) {
        UserCourse userCourse = userCourseMapper.selectByUserIdAndCourseId(courseRequest.getRequestUserId(), courseRequest.getCourseId());
        if(userCourse==null) {
            this.userCourse.setUserId(courseRequest.getRequestUserId());
            this.userCourse.setCourseId(courseRequest.getCourseId());
            userCourseMapper.insert(this.userCourse);
            userScoreService.addUserScoreWhileAgreeChooseCourse(courseRequest.getCourseId(), (courseRequest.getRequestUserId()));
        }
    }

    /*给某一用户退掉某一课程*/
    public void dropCourse(String username, int courseId) throws Exception {
        User user = userService.getByUserName(username);
        int userId = user.getUserId();
        UserCourse userCourse = userCourseMapper.selectByUserIdAndCourseId(userId, courseId);
        if (userCourse != null) {
            userCourseMapper.deleteByPrimaryKey(userCourse.getId());
        } else {
            throw new Exception("未选择此课程");
        }
    }

    /*根据选课id退课*/
    public boolean dropCourseByid(Integer id) throws Exception {
        userCourseMapper.deleteByPrimaryKey(id);
        return true;
    }

    /*判断某一用户是否选过某一课程*/
    public boolean hasBeenChosen(String username, int courseId) {
        User user = userService.getByUserName(username);
        int userId = user.getUserId();
        UserCourse userCourse = userCourseMapper.selectByUserIdAndCourseId(userId, courseId);
        if (userCourse == null) {
            return false;
        }
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateUserCourse(UserCourse userCourse) throws Exception {
        userCourseMapper.updateByPrimaryKey(userCourse);
        return true;
    }

}
