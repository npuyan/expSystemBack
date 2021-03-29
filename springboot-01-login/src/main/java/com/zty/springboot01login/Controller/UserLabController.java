package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.UserLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class UserLabController {

    @Autowired
    UserLabService userLabService;

    /*通过实验和用户创建或者打开容器*/
    @RequestMapping("/openlabenv")
    public RespBean openLabEnv(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        CourseLab courseLab = JSON.parseObject(JSON.toJSONString(param.get("courselab")), CourseLab.class);
        int i = userLabService.openLabEnv(username, courseLab);
        return RespBean.ok("成功得到端口", i);
    }

    /*当用户关闭窗口时，发送请求暂停对应的容器*/
    @RequestMapping("/pauselabenv")
    public RespBean pauseLabEnv(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        CourseLab courseLab = JSON.parseObject(JSON.toJSONString(param.get("courselab")), CourseLab.class);
        return RespBean.ok("成功暂停", userLabService.pauseLabEnv(username, courseLab));
    }
}
