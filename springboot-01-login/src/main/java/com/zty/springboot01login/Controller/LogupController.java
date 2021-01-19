package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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
    public String logup(@RequestParam(name = "username") String username,
                     @RequestParam(name = "password") String password,
                     @RequestParam(name = "phone") String phone,
                     @RequestParam(name = "email") String email
                     ) {
        try {
            user.setUserName(username);
            user.setPassword(password);
            user.setPhone(phone);
            user.setEmail(email);
            this.userService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return "注册失败";
        }
        return "注册成功";
    }
}
