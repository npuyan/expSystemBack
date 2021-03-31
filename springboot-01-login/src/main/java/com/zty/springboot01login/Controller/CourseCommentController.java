package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseComment;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class CourseCommentController {
    @Autowired
    CourseCommentService courseCommentService;

    /*提交评论*/
    @RequestMapping("/submitcoursecomment")
    public RespBean submitCourseComment(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        Integer courseid = JSON.parseObject(JSON.toJSONString(param.get("courseid")), Integer.class);
        String text = JSON.parseObject(JSON.toJSONString(param.get("text")), String.class);
        return courseCommentService.submitCourseComment(username, courseid, text);
    }

    /*根据课程id得到所有的评论，按照时间先后排序(或后先排序)*/
    @RequestMapping("/getcoursecomment")
    public List<CourseComment> getCourseComment(@RequestBody Map<String, Object> param) throws Exception {
        Integer courseid = JSON.parseObject(JSON.toJSONString(param.get("courseid")), Integer.class);
        Boolean asc = JSON.parseObject(JSON.toJSONString(param.get("asc")), Boolean.class);
        return courseCommentService.getCourseComment(courseid, asc);
    }
}
