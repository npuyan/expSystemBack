package com.zty.springboot01login;

import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Utils.UseSSH;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootTest
class Springboot01LoginApplicationTests {

    @Autowired
    UserMapper mapper1;

    @Autowired
    UseSSH ssh;
    @Test
    void test1() {
        System.out.println(mapper1.selectByUserName("zty"));
        System.err.println(mapper1.selectByUserName("zty"));
        System.err.println(mapper1.selectByPrimaryKey("1"));
        System.out.println(new BCryptPasswordEncoder(10).encode("11111"));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        String encode1=bCryptPasswordEncoder.encode("zty981115");
        String encode2=bCryptPasswordEncoder.encode("1111");
        System.err.println(encode1);
        System.err.println(bCryptPasswordEncoder.matches("1111", encode1));
    }
    @Test
    public void testSSH(){
        ssh.connect("0");
    }
}
