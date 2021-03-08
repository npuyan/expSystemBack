package com.zty.springboot01login.Controller;

import com.alibaba.fastjson.JSON;
import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.RespBean;
import com.zty.springboot01login.Service.UserLabService;
import com.zty.springboot01login.Utils.SftpOperator;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.sshd.common.util.io.IoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

@RestController
@Repository
@CrossOrigin
public class UserLabController {

    @Autowired
    UserLabService userLabService;

    /*通过实验和用户创建或者打开容器*/
    @RequestMapping("/openlabenv")
    public RespBean openLabEnv(@RequestBody Map<String, Object> param) throws Exception {
        String username = JSON.parseObject(JSON.toJSONString(param.get("username")), String.class);
        CourseLab courseLab = JSON.parseObject(JSON.toJSONString(param.get("courselab")), CourseLab.class);
        return RespBean.ok("成功得到端口", userLabService.openLabEnv(username, courseLab));
//        return RespBean.ok("成功得到端口",31805);
    }

    /*通过path得到文件 遗弃*/
//    @RequestMapping("/getfilebyurl")
//    public ResponseEntity<byte[]> getFileByurl(@RequestBody Map<String, Object> param) throws Exception {
//        String path = JSON.parseObject(JSON.toJSONString(param.get("path")), String.class);
//        SftpOperator sftpOperator = new SftpOperator();
//        try {
//            sftpOperator.login();
//            sftpOperator.list("").forEach(p -> System.out.println(p.toString()));
//            sftpOperator.download("chengji.pdf", "springboot-01-login/src/main/java/META-INF/chengji.pdf");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        File file = new File("springboot-01-login/src/main/java/META-INF/chengji.pdf");
//        if (!file.exists()) {
//            throw new FileNotFoundException(file.getName());
//        }
//        FileChannel channel = null;
//        FileInputStream fs = null;
//        try {
//            fs = new FileInputStream(file);
//            channel = fs.getChannel();
//            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
//            while ((channel.read(byteBuffer)) > 0) {
//                //no nothing
//            }
//            ResponseEntity<byte[]> response = null;
//            HttpHeaders headers = new HttpHeaders();
//            //这行代码比较重要，有这个属性则告知浏览器下载文件，无则使用浏览器打开PDF
////        headers.setContentDispositionFormData("name", "filename");
//            headers.setContentType(MediaType.parseMediaType("application/pdf"));
//            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//            byte[] content = byteBuffer.array();
//            response = new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            file.delete();
//        }
//        return null;
//    }

//    @RequestMapping("/downloadfile")
//    public Object downloadFile(@RequestParam String filename, final HttpServletResponse response, final HttpServletRequest request) {
//        return userLabService.downloadFile(filename,response,request);
//    }
//    @RequestMapping("/uploadfile")
//    public Object uploadFile(@RequestParam("labid") Integer labId,@RequestParam("file") MultipartFile multipartFiles , final HttpServletResponse response, final HttpServletRequest request)
//    {
//        System.err.println(labId);
//        return userLabService.uploadFile(multipartFiles,response,request);
//    }
}
