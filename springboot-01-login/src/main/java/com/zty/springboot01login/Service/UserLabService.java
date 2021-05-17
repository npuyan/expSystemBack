package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.UserLabMapper;
import com.zty.springboot01login.Pojo.*;
import com.zty.springboot01login.Utils.DockerConnect;
import com.zty.springboot01login.Utils.K8sConnect;
import com.zty.springboot01login.Utils.Pod;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    CourseLabService courseLabService;
    @Autowired
    UserService userService;

    /*通过实验和用户创建或者打开容器*/
    public int openLabEnv(String username, CourseLab courseLab) throws Exception {
        User user = userService.getByUserName(username);
        UserLab userLab = userLabMapper.selectByUserIdAndLabId(user.getUserId(), courseLab.getLabId());
        CourseEnv courseEnv = courseEnvService.getByEnvId(courseLab.getEnvId());

        /*TODO podname为{pod_username_envid},后期可以修改*/
        String deployName = Pod.PodName(username, courseLab.getLabId());
        /*先查询对应的实验容器是否已经创建过*/
        if (userLab != null) {
            /*创建过容器*/
            V1Deployment deployment = null;
            try {
                deployment = K8sConnect.getDeploymentByName(null, deployName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (deployment != null) {
                /*查询对应容器的名称是否已经打开，如果已经打开就取消容器的暂停直接返回port*/
                /*unpause对应的容器*/
                V1Pod pod = K8sConnect.getPodByName(null, deployName);
                String containerId = pod.getStatus().getContainerStatuses().get(0).getContainerID();
                System.out.println(containerId);
                /*取消暂停*/
                try {
                    DockerConnect.unpasueContainer(containerId);
                } catch (Exception e) {
                    System.err.println("不需要取消暂停");
//                    e.printStackTrace();
                }
                return getServiceNodePortByDeployment(deployName);
            } else {
                CourseImage courseImage =null;
                /*如果没打开则查询是否有对应的镜像，如果有镜像则直接启动镜像并返回port*/
                try {
                    courseImage = courseImageService.getCourseImageByName(deployName);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (courseImage != null) {
                    System.err.println("imagename " + courseImage.getImageName());
                    /*创建deployment和service*/
                    V1Service service = createDeploymentByImageAndServiceByDeployemnt(courseImage, deployName);

                    /*得到service，返回port*/
                    return getServiceNodePortByDeployment(deployName);
                } else {
                    courseImage = courseImageService.getCourseImageById(courseEnv.getImageId());
                    V1Service service = createDeploymentByImageAndServiceByDeployemnt(courseImage, deployName);
                    return getServiceNodePortByDeployment(deployName);
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
                try {
                    userLabMapper.insert(userLabInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        service.getSpec().getSelector().replace("app", deployName);
        return service;
    }

    public RespBean deleteDeploymentAndService(int userid,int  labid) throws Exception{
        User user=userService.getByUserId(userid);
        CourseLab courseLab=courseLabService.getCourseLabById(labid);
        String deployname = Pod.PodName(user.getUsername(), courseLab.getEnvId());
        return deleteDeploymentAndService(deployname);
    }
    /*通多deployname删除deploy和service*/
    public RespBean deleteDeploymentAndService(String deployName) throws Exception{
        try {
            K8sConnect.deleteDeployment(null,deployName);
            K8sConnect.deleteService(null,deployName);
        }catch (Exception e){
            e.printStackTrace();;
            return RespBean.error("删除失败");
        }
        return RespBean.ok("删除成功");
    }

    /*通过镜像创建deploy和service*/
    public V1Service createDeploymentByImageAndServiceByDeployemnt(CourseImage courseImage, String deployName) throws Exception {
        /*创建deploy*/

        System.out.println(courseImage.getImageName());
        System.out.println(deployName);
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

    /*当用户关闭窗口时，发送请求暂停对应的容器*/
    public boolean pauseLabEnv(String username, CourseLab courseLab) throws Exception {
        User user = userService.getByUserName(username);
        UserLab userLab = userLabMapper.selectByUserIdAndLabId(user.getUserId(), courseLab.getLabId());
        CourseEnv courseEnv = courseEnvService.getByEnvId(courseLab.getEnvId());
        String deployName = Pod.PodName(username, courseLab.getLabId());
        if (userLab != null) {
            /*暂停*/
            V1Pod pod = K8sConnect.getPodByName(null, deployName);
            DockerConnect.pasueContainer(pod.getStatus().getContainerStatuses().get(0).getContainerID());
            return true;
        } else {
            throw new Exception("未找到要暂停的环境");
        }
    }

}