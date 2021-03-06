package com.zty.springboot01login;

import com.zty.springboot01login.Mapper.CourseLabMapper;
import com.zty.springboot01login.Mapper.CourseRequestMapper;
import com.zty.springboot01login.Mapper.UserCourseMapper;
import com.zty.springboot01login.Mapper.UserMapper;
import com.zty.springboot01login.Pojo.*;
import com.zty.springboot01login.Service.UserService;
import com.zty.springboot01login.Utils.K8sConnect;
import com.zty.springboot01login.Utils.RequestType;
import com.zty.springboot01login.Utils.SftpOperator;
import com.zty.springboot01login.Utils.UseSSH;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.util.List;

@SpringBootTest
class Springboot01LoginApplicationTests {

    @Autowired
    UserMapper mapper1;

    @Autowired
    UserService userService;
    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    User user;
    @Autowired
    CourseLabMapper courseLabMapper;
    @Autowired
    UseSSH ssh;
    @Autowired
    CourseRequestMapper courseRequestMapper;
    @Test
    void test1() {
        System.out.println(mapper1.selectByUserName("zty"));
        System.err.println(mapper1.selectByUserName("zty"));
        System.err.println(mapper1.selectByPrimaryKey(1));
        System.out.println(new BCryptPasswordEncoder(10).encode("11111"));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        String encode1=bCryptPasswordEncoder.encode("zty981115");
        String encode2=bCryptPasswordEncoder.encode("1111");
        String encode3=bCryptPasswordEncoder.encode("111");
        System.err.println(encode1);
        System.err.println(bCryptPasswordEncoder.matches("1111", encode1));
        String lmx= bCryptPasswordEncoder.encode("lmx");
        System.err.println(lmx);
    }
    @Test
    public void testSSH(){
        ssh.connect("0");
    }
    @Test
    public void addUser(){
        user.setUserName("admin");
        user.setPassword(new BCryptPasswordEncoder(10).encode("admin"));
        user.setUserType("0");
        mapper1.insert(user);
    }
    @Test
    public void testGetSelectCourse(){
//        System.out.println(userService.getSelectedCourse("111"));
        User user = mapper1.selectByUserName("111");
        System.out.println(user.getUserId());
        List<UserCourse> userCourses = userCourseMapper.selectByUserId(user.getUserId());
        System.out.println(userCourses.size());

    }
    @Test
    public void getCourseLabBycouseIdTest(){
        courseLabMapper.selectByCourseId(1);
        //System.out.println(courseService.getCourseLabBycouseId(1));
    }
    @Test
    public void showAttName(){
        User courseImage=new User();
        Field[] fields=courseImage.getClass().getDeclaredFields();
        for(Field f:fields){
            System.err.println(f);
        }
    }

    @Test
    public void testenum(){
        CourseRequest courseRequest=new CourseRequest();
        courseRequest.setCourseId(1);
        courseRequest.setCheckUserId(1);
        courseRequest.setRequestType(RequestType.add);
        courseRequest.setRequestUserId(1);
        courseRequestMapper.insert(courseRequest);
    }

    @Test
    public void testK8s() throws Exception {
        V1Service dorowu2 = K8sConnect.getServiceByName(null, "dorowu2");
        System.out.println(dorowu2);
        V1Deployment deployment1 = (V1Deployment) K8sConnect.loadYaml("src/main/java/com/zty/springboot01login/Utils/createDeployment.yaml");
//        K8sConnect.createDeployment(null, deployment1);
    }

    @Test
    public void testSshd() throws Exception{
        SftpOperator sftpOperator=new SftpOperator();
        try {
            sftpOperator.login();
            sftpOperator.download("chengji.pdf","src/main/java/META-INF/chengji.pdf");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                sftpOperator.logout();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        V1Deployment deployment1 = (V1Deployment) K8sConnect.loadYaml("src/main/java/com/zty/springboot01login/Utils/createDeployment.yaml");
//        K8sConnect.createDeployment(null, deployment1);

        V1Service service = (V1Service) K8sConnect.loadYaml("src/main/java/com/zty/springboot01login/Utils/createService.yaml");
        K8sConnect.createService(null,service);
    }
}
