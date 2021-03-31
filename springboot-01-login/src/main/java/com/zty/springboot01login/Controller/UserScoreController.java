package com.zty.springboot01login.Controller;

import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Repository
@CrossOrigin
public class UserScoreController {
    @Autowired
    UserScoreService userScoreService;

    /*TODO 参数改为map形式*/
    /*上传作业*/
    @RequestMapping("/uploadhomework")
    public RespBean uploadHomework(@RequestParam("userid") Integer userid,
                                   @RequestParam("labid") Integer labid,
                                   @RequestParam("file") MultipartFile multipartFile,
                                   final HttpServletResponse response, final HttpServletRequest request) throws Exception {
//        File file=new File("springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createDeployment.yaml");
//        InputStream inputStream = Files.newInputStream(file.toPath());
//        MultipartFile multipartFile=new MockMultipartFile("test.docx","test.docx", ContentType.APPLICATION_OCTET_STREAM.toString(),inputStream);
        return userScoreService.uploadHomework(userid, labid, multipartFile, response, request);
    }

    /*下载或预览单个作业*/
    @RequestMapping("/downloadHomework")
    public RespBean downloadhomework(@RequestParam("userid") Integer userid,
                                     @RequestParam("labid") Integer labid,
                                     final HttpServletResponse response, final HttpServletRequest request) throws IOException {
        return userScoreService.downloadhomework(userid, labid, response, request);
    }
}
