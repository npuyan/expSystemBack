package com.zty.springboot01login;

import com.zty.springboot01login.Mapper.CourseMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot01LoginApplicationTests {


    @Autowired
    CourseMapper mapper;

    @Autowired
    UserMapper mapper1;
    @Test
    void test1() {

        System.out.println(mapper.selectByPrimaryKey("1"));
        System.out.println(mapper1.selectByPrimaryKey("1"));
        User user=new User("2","zty","11111","1828282","zty2804@",2,"2");
        mapper1.insert(user);
        System.out.println(1);
    }
}
