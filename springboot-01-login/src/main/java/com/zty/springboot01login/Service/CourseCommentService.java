package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseCommentMapper;
import com.zty.springboot01login.Pojo.CourseComment;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CourseCommentService {

    @Autowired
    UserService userService;

    @Autowired
    CourseCommentMapper courseCommentMapper;
    @Autowired
    CourseComment courseComment;

    /*提交评论*/
    public RespBean submitCourseComment(String username, Integer courseid, String text) throws Exception {
        User user = userService.getByUserName(username);
        if (user == null) {
            throw new Exception("未找到用户");
        }
        courseComment.setUserId(user.getUserId());
        courseComment.setCourseId(courseid);
        courseComment.setText(text);
        courseComment.setTime(new Date());
        courseCommentMapper.insert(courseComment);
        return RespBean.ok("提交成功");
    }

    /*根据课程id得到所有的评论，按照时间先后排序(或后先排序)*/
    public List<CourseComment> getCourseComment(Integer courseid, Boolean asc) {
        List<CourseComment> courseComments = courseCommentMapper.selectByCourseId(courseid);
        /*按照时间排序*/
        if (asc) {
            courseComments.sort(new Comparator<CourseComment>() {
                @Override
                public int compare(CourseComment o1, CourseComment o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
        } else {
            courseComments.sort(new Comparator<CourseComment>() {
                @Override
                public int compare(CourseComment o1, CourseComment o2) {
                    return o2.getTime().compareTo(o1.getTime());
                }
            });
        }
        return courseComments;
    }
}
