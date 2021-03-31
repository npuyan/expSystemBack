package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.UserScoreMapper;
import com.zty.springboot01login.Pojo.*;
import com.zty.springboot01login.Utils.SftpOperator;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class UserScoreService {
    @Autowired
    UserScoreMapper userScoreMapper;

    @Autowired
    UserService userService;
    @Autowired
    CourseLabService courseLabService;

    @Autowired
    UserScore userScore;

    private String homeworkName(User user, CourseLab courseLab) {
        return user.getUsername() + "_" + courseLab.getLabName();
    }

    /*上传作业*/
    public RespBean uploadHomework(Integer userid, Integer labid, MultipartFile multipartFile,
                                   final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        String[] split = null;
        try {
            User user = userService.getByUserId(userid);
            CourseLab courseLab = courseLabService.getCourseLabById(labid);
            System.out.println(multipartFile.getOriginalFilename());
            split = multipartFile.getOriginalFilename().split("\\.");
            /*重命名*/
            File file = File.createTempFile(homeworkName(user, courseLab), "." + split[1]);
            multipartFile.transferTo(file);
            InputStream inputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("上传失败，未找到用户或实验，参数错误");
        }
        userScore.setLabId(labid);
        userScore.setUserId(userid);
        userScore.setHomework(multipartFile.getBytes());
        userScore.setScore(0);
        userScore.setHomeworktype(split[1]);

        UserScore select = userScoreMapper.selectByPrimaryKey(this.userScore);
        if (select == null) {
            //如果之前没有记录则直接上传
            userScoreMapper.insert(userScore);
        } else {
            //有记录则更新文件
            userScoreMapper.updateByPrimaryKeyWithBLOBs(userScore);
        }
        return RespBean.ok("上传成功");
    }

    /*下载或预览单个作业*/
    public RespBean downloadhomework(Integer userid, Integer labid, final HttpServletResponse response, final HttpServletRequest request) throws IOException {
        String homeworkname = null;
        try {
            User user = userService.getByUserId(userid);
            CourseLab courseLab = courseLabService.getCourseLabById(labid);
            homeworkname = homeworkName(user, courseLab);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("下载失败，未找到用户或实验，参数错误");
        }
        //下载文件
        UserScore userScore1 = userScoreMapper.selectByPrimaryKey(new UserScoreKey(userid, labid));
        homeworkname=homeworkname+"."+userScore1.getHomeworktype();
        byte[] homework = userScore1.getHomework();
        System.err.println(homework.length);
//        InputStream inputStream = new ByteArrayInputStream(homework);
        MultipartFile multipartFile = new MockMultipartFile(homeworkname, homeworkname, ContentType.APPLICATION_OCTET_STREAM.toString(), homework);
        return SftpOperator.downloadFile(multipartFile, response, request);
    }
}
