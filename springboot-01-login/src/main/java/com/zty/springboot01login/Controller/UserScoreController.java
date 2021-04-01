package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Pojo.UserScore;
import com.zty.springboot01login.Service.UserScoreService;
import com.zty.springboot01login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class UserScoreController {
    @Autowired
    UserScoreService userScoreService;

    @Autowired
    UserService userService;

    /*TODO 参数改为map形式*/
    /*上传作业*/
    @RequestMapping("/uploadhomework")
    public RespBean uploadHomework(@RequestParam("userid") Integer userid,
                                   @RequestParam("labid") Integer labid,
                                   @RequestParam("file") MultipartFile multipartFile,
                                   final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        /*test*/
//        File file = new File("springboot-01-login/src/main/java/com/zty/springboot01login/Utils/test.zip");
//        InputStream inputStream = Files.newInputStream(file.toPath());
//        MultipartFile multipartFile = new MockMultipartFile("test.zip", "test.zip", ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

        /*判断上传问价你的大小*/
        Float size = Float.parseFloat(String.valueOf(multipartFile.getSize())) / 1024;
        BigDecimal b = new BigDecimal(size);
        // 2表示2位 ROUND_HALF_UP表明四舍五入，
        size = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        size = size / 1024;
        if (size > 16) {
            return RespBean.error("文件过大");
        }
        return userScoreService.uploadHomework(userid, labid, multipartFile, response, request);
    }

    /*下载或预览单个作业*/
    @RequestMapping("/downloadhomework")
    public RespBean downloadHomework(@RequestParam("userid") Integer userid,
                                     @RequestParam("labid") Integer labid,
                                     final HttpServletResponse response, final HttpServletRequest request) throws IOException {
        return userScoreService.downloadhomework(userid, labid, response, request);
    }

    /*批量下载作业，打包成一个zip文件返回*/
    @RequestMapping("/downloadhomeworks")
    public RespBean downloadHomeworks(@RequestParam("labid") Integer labis,
                                      final HttpServletResponse response, final HttpServletRequest request) throws IOException {
        return userScoreService.downloadHomeworks(labis, response, request);
    }

    /*根据实验id得到所有学生的成绩*/
    @RequestMapping("/getscoresbylabid")
    public List<Map<String, Object>> getScoresByLabid(@RequestBody Map<String, Object> param) {
        Integer labid = JSON.parseObject(JSON.toJSONString(param.get("labid")), Integer.class);
        return userScoreService.getScoresByLabid(labid);
    }

    /*提交學生成績*/
    @RequestMapping("/putuserscore")
    public RespBean putUserScore(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        Integer labid = JSON.parseObject(JSON.toJSONString(param.get("labid")), Integer.class);
        Integer score = JSON.parseObject(JSON.toJSONString(param.get("score")), Integer.class);
        return userScoreService.putUserScore(username, labid, score);
    }

    /*批量提交學生成績*/
    @RequestMapping("/putuserscores")
    public RespBean putUserScores(@RequestBody Map<String, Object> param) throws Exception {
        List<Map<String, Object>> maps = JSON.parseObject(JSON.toJSONString(param.get("arrays")), List.class);
        for (Map<String, Object> map : maps) {
            String username = JSON.parseObject(JSON.toJSONString(map.get("username")), String.class);
            Integer labid = JSON.parseObject(JSON.toJSONString(map.get("labid")), Integer.class);
            Integer score = JSON.parseObject(JSON.toJSONString(map.get("score")), Integer.class);
            userScoreService.putUserScore(username, labid, score);
        }
        return RespBean.ok("批量上传成绩成功");
    }

    /*获取实验成绩*/
    @RequestMapping("/getuserscore")
    public UserScore getUserScore(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        Integer labid = JSON.parseObject(JSON.toJSONString(param.get("labid")), Integer.class);
        return userScoreService.getUserScore(username, labid);
    }
}
