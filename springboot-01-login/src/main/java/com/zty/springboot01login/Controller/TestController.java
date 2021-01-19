package com.zty.springboot01login.Controller;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Repository
@CrossOrigin
public class TestController {
    @RequestMapping(value = "/test")
    public String test(){
        return "test";
    }
}
