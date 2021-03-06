package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.UserLabMapper;
import com.zty.springboot01login.Pojo.*;
import com.zty.springboot01login.Utils.K8sConnect;
import com.zty.springboot01login.Utils.Pod;
import com.zty.springboot01login.Utils.SftpOperator;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Service;
import org.apache.commons.compress.utils.IOUtils;
import org.eclipse.sisu.space.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class UserLabService {

    @Autowired
    UserLabMapper userLabMapper;
    @Autowired
    UserLab userLabInstance;

    @Autowired
    CourseEnvService courseEnvService;
    @Autowired
    CourseImageService courseImageService;
    @Autowired
    UserService userService;

    /*通过实验和用户创建或者打开容器*/
    public int openLabEnv(String username, CourseLab courseLab) throws Exception {
        User user = userService.getByUserName(username);
        UserLab userLab = userLabMapper.selectByUserIdAndLabId(user.getUserId(), courseLab.getLabId());
        CourseEnv courseEnv = courseEnvService.getByEnvId(courseLab.getCourseId());
        /*podname为{username_envid},后期可以修改*/
        String deployName = Pod.PodName(username, courseEnv.getEnvId());
        /*先查询对应的实验容器是否已经创建过*/
        if (userLab != null) {
            /*创建过容器*/
            V1Deployment deployment = K8sConnect.getDeploymentByName(null, deployName);
            if (deployment != null) {
                /*查询对应容器的名称是否已经打开，如果已经打开直接返回port*/
                return getServiceNodePortByDeployment(deployName);
            } else {
                /*如果没打开则查询是否有对应的镜像，如果有镜像则直接启动镜像并返回port*/
                CourseImage courseImage = courseImageService.getCourseImageByName(deployName);
                if (courseImage != null) {
                    /*创建deployment和service*/
                    V1Service service = createDeploymentByImageAndServiceByDeployemnt(courseImage, deployName);

                    /*得到service，返回port*/
                    return getServiceNodePortByDeployment(deployName);
                } else {
                    throw new Exception("异常:实验表中有数据，但是镜像未创建！");
                }
            }
        } else {
            /*没创建过容器*/
            /*没有容器也没有镜像，那么去查找对应的基础环境并启动容器*/
            CourseImage courseImage = courseImageService.getCourseImageById(courseEnv.getImageId());
            if (courseImage != null) {
                /*启动镜像*/
                /*创建deployment和service*/
                V1Service service = createDeploymentByImageAndServiceByDeployemnt(courseImage, deployName);

                /*把本用户创建的实验记录加入userlab*/
                userLabInstance.setUserId(user.getUserId());
                userLabInstance.setLabId(courseLab.getLabId());
                userLabInstance.setEnvId(courseEnv.getEnvId());
                userLabInstance.setFlag("0");
                userLabInstance.setStartTime(String.valueOf(new Date().getTime()));
                userLabMapper.insert(userLabInstance);

                /*得到service，返回port*/
                return getServiceNodePortByDeployment(deployName);
            } else {
                throw new Exception("异常:实验表中有数据，但是镜像未创建！");
            }
        }
    }


    /*TODO 一个一个设置太麻烦，有没有简单的方法能够直接批量设置*/
    /*通过镜像创建deploy*/
    public V1Deployment createDeploymentByImage(CourseImage courseImage, String deployName) throws Exception {
        V1Deployment deployment1 = (V1Deployment) K8sConnect.loadYaml(K8sConnect.deploymentPath);
        deployment1.getMetadata().getLabels().replace("app", deployName);
        deployment1.getMetadata().setName(deployName);
        deployment1.getSpec().getSelector().getMatchLabels().replace("app", deployName);
        deployment1.getSpec().getTemplate().getMetadata().getLabels().replace("app", deployName);
        deployment1.getSpec().getTemplate().getSpec().getContainers().get(0).setImage(courseImage.getImageName());
//        deployment1.getSpec().getTemplate().getSpec().getContainers().get(0).setName(deployName);
        return deployment1;
    }

    /*通过deploy创建service*/
    public V1Service createServiceByDeployemnt(String deployName) throws Exception {
        V1Service service = (V1Service) K8sConnect.loadYaml(K8sConnect.servicePath);
        service.getMetadata().getLabels().replace("app", deployName);
        service.getMetadata().setName(deployName);
        return service;
    }

    /*通过镜像创建deploy和service*/
    public V1Service createDeploymentByImageAndServiceByDeployemnt(CourseImage courseImage, String deployName) throws Exception {
        /*创建deploy*/
        V1Deployment deployment1 = createDeploymentByImage(courseImage, deployName);
        K8sConnect.createDeployment(null, deployment1);

        /*创建service*/
        V1Service service = createServiceByDeployemnt(deployName);
        K8sConnect.createService(null, service);
        return service;
    }

    /*通过deployname得到service的端口*/
    public Integer getServiceNodePortByDeployment(String deployName) throws Exception {
        V1Service service = K8sConnect.getServiceByName(null, deployName);
        if (service != null) {
            return service.getSpec().getPorts().get(0).getNodePort();
        } else {
            throw new NoSuchFieldException("k8s未找到对应的Service！");
        }
    }
}


/*
https://view.officeapps.live.com/op/view.aspx?src=http://storage.xuetangx.com/public_assets/xuetangx/PDF/1.xls
 */