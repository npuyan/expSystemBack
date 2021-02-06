package com.zty.springboot01login;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Pojo.UserCourse;
import com.zty.springboot01login.Service.UserService;
import com.zty.springboot01login.Utils.UseSSH;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class Springboot01LoginApplicationTests {

    @Autowired
    UserMapper mapper1;

    @Autowired
    UserService userService;
    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    User user;
    @Autowired
    CourseLabMapper courseLabMapper;
    @Autowired
    UseSSH ssh;
    @Test
    void test1() {
        System.out.println(mapper1.selectByUserName("zty"));
        System.err.println(mapper1.selectByUserName("zty"));
        System.err.println(mapper1.selectByPrimaryKey(1));
        System.out.println(new BCryptPasswordEncoder(10).encode("11111"));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        String encode1=bCryptPasswordEncoder.encode("zty981115");
        String encode2=bCryptPasswordEncoder.encode("1111");
        String encode3=bCryptPasswordEncoder.encode("111");
        System.err.println(encode1);
        System.err.println(bCryptPasswordEncoder.matches("1111", encode1));
        String lmx= bCryptPasswordEncoder.encode("lmx");
        System.err.println(lmx);
    }
    @Test
    public void testSSH(){
        ssh.connect("0");
    }
    @Test
    public void addUser(){
        user.setUserName("testtoo");
        user.setPassword(new BCryptPasswordEncoder(10).encode("ccccc"));
        mapper1.insert(user);
    }
    @Test
    public void testGetSelectCourse(){
//        System.out.println(userService.getSelectedCourse("111"));
        User user = mapper1.selectByUserName("111");
        System.out.println(user.getUserId());
        List<UserCourse> userCourses = userCourseMapper.selectUserCourseByUserId(user.getUserId());
        System.out.println(userCourses.size());

    }
    @Test
    public void getCourseLabBycouseIdTest(){
        courseLabMapper.selectByCourseId(1);
        //System.out.println(courseService.getCourseLabBycouseId(1));
    }
}
