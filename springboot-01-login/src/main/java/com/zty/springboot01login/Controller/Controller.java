package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Utils.UseSSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class Controller {
    @Autowired
    UseSSH useSSH;      
    @PostMapping(value = "/startNewPort")
    public int startNewPort(@RequestBody Map<String,Object> param){
        String port= JSON.parseObject(JSON.toJSONString(param.get("port")),String.class);
        useSSH.connect(port);
        return Integer.valueOf(port);
    }
}
