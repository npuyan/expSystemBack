package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseEnvMapper;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseImage;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Utils.K8sConnect;
import com.zty.springboot01login.Utils.Pod;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CourseEnvService {

    @Autowired
    CourseEnvMapper courseEnvMapper;

    @Autowired
    UserLabService userLabService;
    @Autowired
    UserService userService;

    public List<CourseEnv> getAllCourseEnv() {
        return courseEnvMapper.selectAll();
    }

    public boolean delCourseEnvById(@RequestParam() Integer id) throws Exception {
        courseEnvMapper.deleteByPrimaryKey(id);
        return true;
    }

    public boolean updateCourseEnv(CourseEnv courseEnv) throws Exception {
        courseEnvMapper.updateByPrimaryKey(courseEnv);
        return true;
    }

    public CourseEnv getByEnvId(int envId) {
        return courseEnvMapper.selectByPrimaryKey(envId);
    }

    public int addCourseEnv(CourseLab courseLab, CourseImage courseImage, CourseEnv courseEnv) throws Exception {
        User user = userService.getByUserId(courseImage.getCreatorId());
        String deployName = Pod.PodName(user.getUsername(), courseEnv.getEnvId());
        if (courseImage != null) {
            V1Service service = userLabService.createDeploymentByImageAndServiceByDeployemnt(courseImage, deployName);
            return userLabService.getServiceNodePortByDeployment(deployName);
        } else {
            throw new Exception("无法创建环境：找不到镜像");
        }
    }

    public boolean saveCourseEnvToImage(String username, CourseEnv courseEnv) {
        User user = userService.getByUserName(username);
        String deployName = Pod.PodName(user.getUsername(), courseEnv.getEnvId());
        try {
            V1Deployment deployment = K8sConnect.getDeploymentByName(null, deployName);
            if(deployment!=null){

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
