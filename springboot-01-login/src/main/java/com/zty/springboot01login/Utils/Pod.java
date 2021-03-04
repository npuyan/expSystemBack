package com.zty.springboot01login.Utils;

import org.springframework.stereotype.Component;

@Component
public class Pod {
    /*pod的名称格式为username_envid*/
    /*后续可以更改为其他*/
    public static String PodName(String username, Integer envid) {
        return username + envid;
    }
}
