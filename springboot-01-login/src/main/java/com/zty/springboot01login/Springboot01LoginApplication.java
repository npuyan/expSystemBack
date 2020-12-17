package com.zty.springboot01login;

import com.zty.springboot01login.Pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot01LoginApplication {
    static User user;
    public static void main(String[] args) {
        SpringApplication.run(Springboot01LoginApplication.class, args);
    }

}
