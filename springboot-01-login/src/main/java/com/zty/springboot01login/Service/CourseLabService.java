package com.zty.springboot01login.Service;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Mapper.CourseEnvMapper;
import com.zty.springboot01login.Mapper.CourseImageMapper;
import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Utils.PinyinComparator;
import com.zty.springboot01login.Utils.SftpOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class CourseLabService {
    @Autowired
    CourseLabMapper courseLabMapper;

    @Autowired
    CourseEnvService courseEnvService;

    /*
    根据用户名和实验查询对应的实验环境：
    如果该用户做过本实验，则启动并返回原有的容器环境
    如果该用户没有做过本实验，则启动并返回一个新的容器环境
    */
    public CourseEnv getCourseEnvByCourseLab(String username, CourseLab courseLab) {
        return null;
    }

    /*通过id的得到所有的实验*/
    public List<CourseLab> getAllcourseLab() {
        List<CourseLab> courseslabs = courseLabMapper.selectAll();
        return courseslabs;
    }

    /*通过实验id删除实验*/
    public boolean delCourseLabById(@RequestParam() Integer id) throws Exception {
        courseLabMapper.deleteByPrimaryKey(id);
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateCourseLab(CourseLab course) throws Exception {
        courseLabMapper.updateByPrimaryKey(course);
        return true;
    }

    public List<CourseLab> getByCourseId(int id) {
        List<CourseLab> courseslabs = courseLabMapper.selectByCourseId(id);
        return courseslabs;
    }

    /*下载文件*/
    public Object downloadFile(String filename, final HttpServletResponse response, final HttpServletRequest request) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            // 取得输出流
            outputStream = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            //下载文件
            SftpOperator sftpOperator = new SftpOperator();
            try {
                sftpOperator.login();
                sftpOperator.download(filename, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //刷新
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }//文件的关闭放在finally中
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* *
     * @描述：上传文件
     * @param multipartFiles
     * @param response
     * @param request
     * @return java.lang.Object
     */
    public Object uploadFile(Integer labid, MultipartFile multipartFiles, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        CourseLab courseLab = courseLabMapper.selectByPrimaryKey(labid);
        if (courseLab == null) {
            throw new Exception("not find couselab");
        }
        try {
            if (multipartFiles != null) {
                SftpOperator sftpOperator = new SftpOperator();
//                String filename=multipartFiles.getOriginalFilename();
                String filename = labid.toString() + ".pdf";
                /*上传文件*/
                sftpOperator.login();
                sftpOperator.upload(filename, multipartFiles.getInputStream());
                /*改变couselab的docpath*/
                courseLab.setDocPath(filename);
                courseLabMapper.updateByPrimaryKey(courseLab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("上传成功");
    }
}
