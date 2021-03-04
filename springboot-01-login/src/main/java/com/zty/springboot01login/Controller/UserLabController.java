package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Service.UserLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

public class UserLabController {

    @Autowired
    UserLabService userLabService;
    /*通过实验和用户创建或者打开容器*/
    @RequestMapping("/openlabenv")
    public int openLabEnv(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        CourseLab courseLab = JSON.parseObject(JSON.toJSONString(param.get("courselab")), CourseLab.class);
        return userLabService.openLabEnv(username, courseLab);
    }
}
