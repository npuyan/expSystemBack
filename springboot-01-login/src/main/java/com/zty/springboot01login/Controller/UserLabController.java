package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.UserLabService;
import com.zty.springboot01login.Utils.SftpOperator;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.sshd.common.util.io.IoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
        return RespBean.ok("成功得到端口", userLabService.openLabEnv(username, courseLab));
    }
}
