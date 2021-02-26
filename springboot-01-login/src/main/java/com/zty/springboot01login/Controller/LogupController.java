package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class LogupController {
    @Autowired
    private User user;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logup")
//    public String logup(@RequestBody(required = false) User user){
//        try {
//            this.userService.addUser(user);
//        }catch (Exception e){
//            e.printStackTrace();
//            return "error";
//        }
//        return "注册成功";
//    }
    public String logup(@RequestBody Map<String, Object> param) {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        String password = JSON.parseObject(JSON.toJSONString(param.get("password")), String.class);
        String phone = JSON.parseObject(JSON.toJSONString(param.get("phone")), String.class);
        String email = JSON.parseObject(JSON.toJSONString(param.get("email")), String.class);
        String type=JSON.parseObject(JSON.toJSONString(param.get("type")),String.class);
        try {
            user.setUserName(username);
            user.setPassword(password);
            user.setPhone(phone);
            user.setEmail(email);
            if(type.equals("学生")){
                user.setUserType("2");
            }else if(type.equals("教师")){
                user.setUserType("1");
            }else if(type.equals("管理员")){
                user.setUserType("0");
            }
            this.userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "注册失败";
        }
        return "注册成功";
    }
}
