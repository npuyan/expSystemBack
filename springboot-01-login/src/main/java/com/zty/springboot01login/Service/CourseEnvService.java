package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseEnvMapper;
import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseImage;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.User;
import com.zty.springboot01login.Utils.DockerConnect;
import com.zty.springboot01login.Utils.K8sConnect;
import com.zty.springboot01login.Utils.Pod;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
public class CourseEnvService {

    @Autowired
    CourseEnvMapper courseEnvMapper;

    @Autowired
    UserLabService userLabService;
    @Autowired
    UserService userService;
    @Autowired
    CourseImageService courseImageService;
    @Autowired
    CourseLabService courseLabService;

    @Autowired
    CourseImage courseImage;
    @Autowired
    CourseEnv courseEnv;


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

    /*教师通过某一镜像给某一实验创建一个环境.打开这个环境并返回端口*/
    public int addCourseEnv(CourseLab courseLab, CourseImage courseImage) throws Exception {
        User user = userService.getByUserId(courseImage.getCreatorId());
        String deployName = Pod.PodName(user.getUsername(), courseLab.getLabId());
        System.out.println("deployName" + deployName);
        if (courseImage != null) {
            V1Service service = userLabService.createDeploymentByImageAndServiceByDeployemnt(courseImage, deployName);
            Integer port = userLabService.getServiceNodePortByDeployment(deployName);

            /**/
            return port;
        } else {
            throw new Exception("无法创建环境：找不到镜像");
        }
    }

    /**/
    public  boolean useAlreadyImage(CourseLab courseLab, CourseImage courseImage) {

        try {

            String deployName = courseImage.getImageName();
            int envId  = courseEnvMapper.selectByCourseEnvName(deployName).getEnvId();

            /*关联试验和环境*/
            courseLab.setEnvId(envId);
            courseLabService.updateCourseLab(courseLab);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
    /*教师将某一环境保存未经想*/
    public boolean saveCourseEnvToImage(String username,CourseLab courseLab) {
        User user = userService.getByUserName(username);
        //
        String deployName = Pod.PodName(user.getUsername(),  courseLab.getLabId());
        try {
            V1Deployment deployment = K8sConnect.getDeploymentByName(null, deployName);
            V1Pod pod = K8sConnect.getPodByName(null, deployName);
            if (pod != null) {
                /* 保存镜像 得到镜像id*/
                String version = "" + RandomUtils.nextInt(Integer.MAX_VALUE);
                String imageId = DockerConnect.commitContainer(pod.getStatus().getContainerStatuses().get(0).getContainerID(), deployName, version);

                /*将镜像信息加入数据库*/
                courseImage.setImageId(imageId);
                courseImage.setCreatorId(user.getUserId());
                courseImage.setImageName(deployName);
                courseImage.setVersion(version);
                courseImage.setPath("node1");
                courseImageService.addCourseImage(courseImage);
                courseImage=courseImageService.getCourseImageByName(deployName);

                /*将环境信息加入数据库*/
                courseEnv.setCreateTime(String.valueOf(new Date().getTime()));
                courseEnv.setEnvName(deployName);
                courseEnv.setNodeName("node1");
                courseEnv.setCpu(1);
                courseEnv.setMemsize(2);
                courseEnv.setCreatorId(String.valueOf(userService.getByUserName(username).getUserId()));
                courseEnv.setImageId(courseImage.getId());
                courseEnvMapper.insert(courseEnv);
                courseEnv=courseEnvMapper.selectByCourseEnvName(courseEnv.getEnvName());

                /*关联试验和环境*/
                courseLab.setEnvId(courseEnv.getEnvId());
                courseLabService.updateCourseLab(courseLab);

                /*关闭deploy和svc*/
                K8sConnect.deleteDeployment(null,deployName);
                K8sConnect.deleteService(null,deployName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
