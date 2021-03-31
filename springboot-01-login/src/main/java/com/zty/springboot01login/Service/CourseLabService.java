package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Utils.SftpOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /*通过实验id得到实验*/
    public CourseLab getCourseLabById(Integer id) {
        return courseLabMapper.selectByPrimaryKey(id);
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

    /*增加一条实验记录*/
    public CourseLab addCourseLab(CourseLab courseLab) throws Exception {
        courseLabMapper.insert(courseLab);
        int i = courseLabMapper.selectLastInsertId();
        CourseLab courseLab1 = courseLabMapper.selectByPrimaryKey(i);
        return courseLab1;
    }

    public List<CourseLab> getByCourseId(int id) {
        List<CourseLab> courseslabs = courseLabMapper.selectByCourseId(id);
        return courseslabs;
    }

    /* *
     * @描述：下载实验文件
     * @param filename
     * @param response
     * @param request
     * @return java.lang.Object
     */
    public RespBean downloadFile(@RequestParam String filename, final HttpServletResponse response, final HttpServletRequest request) {
        return SftpOperator.downloadFile(filename, response, request);
    }

    /* *
     * @描述：上传实验文件
     * @param multipartFiles
     * @param response
     * @param request
     * @return java.lang.Object
     */
    public RespBean uploadFile(Integer labid, MultipartFile multipartFile, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        CourseLab courseLab = courseLabMapper.selectByPrimaryKey(labid);
        if (courseLab == null) {
            throw new Exception("not find couselab");
        }
        try {
            if (multipartFile != null) {
                String filename = labid.toString() + ".pdf";
                /*上传文件*/
                SftpOperator.uploadFile(filename, multipartFile, response, request);
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
