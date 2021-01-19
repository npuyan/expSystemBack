package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Utils.UseSSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@Repository
@CrossOrigin
public class Controller {
    @Autowired
    UseSSH useSSH;      
    @PostMapping(value = "/startNewPort")
    public int startNewPort(@RequestParam String port){
        useSSH.connect(port);
        return Integer.valueOf(port);
    }
}
