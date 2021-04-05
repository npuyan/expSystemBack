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
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class UserScoreService {
    @Autowired
    UserScoreMapper userScoreMapper;

    @Autowired
    UserService userService;
    @Autowired
    CourseLabService courseLabService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserCourseService userCourseService;

    @Autowired
    UserScore userScore;

    private String homeworkName(User user, CourseLab courseLab) {
        return user.getUsername() + "_" + courseLab.getLabName();
    }

    /*选课成功时，将课程下的所有实验加入到userscore*/
    public RespBean addUserScoreWhileAgreeChooseCourse(Integer courseId, Integer userid) {
        List<CourseLab> courseLabs = courseLabService.getByCourseId(courseId);
        for (CourseLab cl : courseLabs) {
            UserScore userScore1 = new UserScore();
            userScore1.setLabId(cl.getLabId());
            userScore1.setUserId(userid);
            userScore1.setScore(0);
            userScoreMapper.insert(userScore1);
        }
        return RespBean.ok("添加成功");
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
        userScore.setHomeworkType(split[1]);
        userScore.setHomeworkTime(new Date());

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
        UserScore userScore1 = userScoreMapper.selectByPrimaryKeyWithBLOBs(new UserScoreKey(userid, labid));
        homeworkname = homeworkname + "." + userScore1.getHomeworkType();
        byte[] homework = userScore1.getHomework();
        System.err.println(homework.length);
//        InputStream inputStream = new ByteArrayInputStream(homework);
        MultipartFile multipartFile = new MockMultipartFile(homeworkname, homeworkname, ContentType.APPLICATION_OCTET_STREAM.toString(), homework);

        return SftpOperator.downloadFile(multipartFile, response, request);
    }

    /*批量下载作业，打包成一个zip文件返回*/
    public RespBean downloadHomeworks(Integer labid, final HttpServletResponse response, final HttpServletRequest request) throws IOException {
        String homeworkname = null;
        /*得到本实验的名字作为压缩包的名字*/
        CourseLab courseLab = courseLabService.getCourseLabById(labid);
        File tempFile = File.createTempFile(courseLab.getLabName(), ".zip");
        System.err.println("temfilename " + tempFile.getName());
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempFile));

        try {
            List<UserScore> userScores = userScoreMapper.selectByLabIdWithBLOBs(labid);
            for (UserScore us : userScores) {
                User user = userService.getByUserId(us.getUserId());
                /*作业全称*/
                homeworkname = homeworkName(user, courseLab) + "." + us.getHomeworkType();

                byte[] homework = us.getHomework();
                if (homework != null && homework.length != 0) {
                    System.err.println(homework.length);
                    System.err.println(homeworkname);

                    /*將文件加入到一个临时文件zip中*/
                    zipOutputStream.putNextEntry(new ZipEntry(homeworkname));
                    zipOutputStream.write(homework);
                    zipOutputStream.closeEntry();
                    System.err.println("写入文件成功一次");
                }
            }
            zipOutputStream.close();
            /*把压缩包传回去*/
            FileInputStream fileInputStream = new FileInputStream(tempFile);
            MultipartFile multipartFile = new MockMultipartFile(tempFile.getName(), tempFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            SftpOperator.downloadFile(multipartFile, response, request);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("批量下载失败");
        }
        /*关闭file和zip*/
        tempFile.delete();
        return RespBean.ok("批量下载成功");
    }

    /*根据实验id得到所有学生的成绩*/
    public List<Map<String, Object>> getScoresByLabid(Integer labid) {
        CourseLab courseLabById = courseLabService.getCourseLabById(labid);
        List<UserCourse> userCourses = userCourseService.getByCourseId(courseLabById.getCourseId());
        /*找到选择本门实验的所有学生*/
//        List<User> users = new LinkedList<>();
//        for (UserCourse uc : userCourses) {
//            users.add(userService.getByUserId(uc.getUserId()));
//        }

        /*找到本门实验已经提交过作业或者给出分数的学生，及在user_score中有本实验的记录,放入map*/
        List<UserScore> userScores = userScoreMapper.selectByLabId(labid);
//        Map<Integer, UserScore> userScoreMap = new HashMap<>();
//        for (UserScore us : userScores) {
//            userScoreMap.put(us.getUserId(), us);
//        }
        /*为了添加username列，只能使用map，增加username，不返回实验报告索引，因为可以直接通过userid和labid预览或下载报告*/
        List<Map<String, Object>> listmap = new LinkedList<>();
//        for (User us : users) {
//            Map<String, Object> map = new HashMap<>();
//            boolean containUser = userScoreMap.containsKey(us.getUserId());
//            map.put("lab_id", labid);
//            map.put("user_id", us.getUserId());
//            map.put("user_name", us.getUsername());
//            map.put("score", containUser ? userScoreMap.get(us.getUserId()).getScore() : 0);
//            map.put("score_time", containUser ? userScoreMap.get(us.getUserId()).getScoreTime() : null);
//            map.put("homework_time", containUser ? userScoreMap.get(us.getUserId()).getHomeworkTime() : null);)
//        }
        for (UserScore us : userScores) {
            Map<String, Object> map = new HashMap<>();
            map.put("lab_id", us.getLabId());
            map.put("user_id", us.getUserId());
            map.put("user_name", userService.getByUserId(us.getUserId()).getUsername());
            map.put("score", us.getScore());
            map.put("score_time", us.getScoreTime());
            map.put("homework_time", us.getHomeworkTime());
            listmap.add(map);
        }
        return listmap;
    }


    /*提交单个學生成績*/
    public RespBean putUserScore(String username, Integer labid, Integer score) throws Exception {
        User user = userService.getByUserName(username);
        if (user == null) {
            throw new Exception("未找到用户");
        }
        UserScore userScore1 = new UserScore();
        userScore1.setUserId(user.getUserId());
        userScore1.setLabId(labid);
        userScore1.setScore(score);
        userScore1.setScoreTime(new Date());
        UserScore selectUserScore = userScoreMapper.selectByPrimaryKey(userScore1);
        if (selectUserScore != null) {
            userScore1.setHomeworkType(selectUserScore.getHomeworkType());
            userScore1.setHomeworkTime(selectUserScore.getHomeworkTime());
            userScoreMapper.updateByPrimaryKey(userScore1);
        } else {
            throw new Exception("无法设置分数，不存在这一记录");
        }
        return RespBean.ok("上传分数成功");
    }

    /*获取实验成绩*/
    public UserScore getUserScore(String username, Integer labid) throws Exception {
        User user = userService.getByUserName(username);
        if (user == null) {
            throw new Exception("未找到用户");
        }
        return userScoreMapper.selectByPrimaryKey(new UserScoreKey(user.getUserId(), labid));
    }
}
