package com.zty.springboot01login.Utils;

import org.springframework.stereotype.Component;

@Component
public class Pod {
    /*pod的名称格式为username-envid*/
    /*后续可以更改为其他*/
    /*不能用下划线只能用中划线，我服了，此处排错1一个小时*/
    public static String PodName(String username, Integer envid) {
        return "pod-"+username +"-"+ envid;
    }
}
