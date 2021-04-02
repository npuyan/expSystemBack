package com.zty.springboot01login.Utils;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.zty.springboot01login.Pojo.RespBean;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.apache.sshd.sftp.client.fs.SftpFileSystem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/* *
 * @描述：sftp协议传输服务器的文件
 * @param null
 * @return
 */
@Component
@Data
public class SftpOperator {
    private SshClient client;
    private ClientSession session;
    private SftpFileSystem fs;

    private String host="10.168.4.167";
//    private String host = "124.70.84.98";
    private int port = 22;
    private String username = "root";
//    private String password = "Zty981115";
    private String password = "nwpu1102";
    /*只能从这个目录下下载和上传文件，禁止修改其他目录*/
    private String ftpdir = "/home/expSystemBack/docData/";

    /* *
     * @描述：登录
     * @param
     * @return com.zty.springboot01login.Utils.SftpOperator
     */
    public SftpOperator login() throws Exception {
        client = SshClient.setUpDefaultClient();
        client.start();
        session = client.connect(username, host, port).verify().getSession();
        session.addPasswordIdentity(password);
        if (session.auth().verify(100000).isFailure()) {
            System.err.println("sftp  连接失败");
            throw new Exception("sftp 连接失败");
        }
        fs = SftpClientFactory.instance().createSftpFileSystem(session);
        System.out.println("sftp 连接成功");
        return this;
    }

    /* *
     * @描述：登出
     * @param
     * @return void
     */
    public void logout() throws Exception {
        fs.close();
        session.close();
        client.stop();
        System.out.println("sftp 关闭连接");
    }

    /* *
     * @描述：上传文件，用文件的方式
     * @param ftpdir
     * @param ftpname
     * @param filename
     * @return void
     */
    public void upload(String ftpname, String filename) throws Exception {
        Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
        if (!Files.exists(remoteRoot))
            Files.createDirectories(remoteRoot);
        Path remoteFile = remoteRoot.resolve(ftpname);
        Files.deleteIfExists(remoteFile);
        Files.copy(Paths.get(filename), remoteFile);
    }

    /* *
     * @描述：上传文件，用流的方式
     * @param ftpdir
     * @param ftpname
     * @param inputStream
     * @return void
     */
    public void upload(String ftpname, InputStream inputStream) throws Exception {
        Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
        if (!Files.exists(remoteRoot))
            Files.createDirectories(remoteRoot);
        Path remoteFile = remoteRoot.resolve(ftpname);
        Files.deleteIfExists(remoteFile);
        Files.copy(inputStream, remoteFile);
    }

    /* *
     * @描述：下载文件，用文件名的方式
     * @param null
     * @return
     */
    public void download(String ftpname, String filename) throws Exception {
        Files.deleteIfExists(Paths.get(filename));
        Path remoteFile = fs.getDefaultDir().resolve(ftpdir).resolve(ftpname);
        Files.copy(remoteFile, Paths.get(filename));
    }

    /* *
     * @描述：下载文件，用流的方式
     * @param ftpdir
     * @param ftpname
     * @param outputStream
     * @return void
     */
    public void download(String ftpname, OutputStream outputStream) throws Exception {
        Path remoteFile = fs.getDefaultDir().resolve(ftpdir).resolve(ftpname);
        Files.copy(remoteFile, outputStream);
    }

    /* *
     * @描述：删除文件
     * @param ftpdir
     * @param ftpname
     * @return void
     */
    public void delete(String ftpname) throws Exception {
        Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
        if (StringUtils.isEmpty(ftpname)) {
            Files.deleteIfExists(remoteRoot);
            return;
        }
        Files.deleteIfExists(remoteRoot.resolve(ftpname));
    }

    /* *
     * @描述：创建目录
     * @param ftpdir
     * @return void
     */
    public void mkdir(String ftpdir) throws Exception {
        ftpdir = this.ftpdir + ftpdir;
        Path remoteRoot = fs.getDefaultDir().resolve(ftpdir);
        if (!Files.exists(remoteRoot))
            Files.createDirectories(remoteRoot);
    }

    /* *
     * @描述：判断文件是否存在
     * @param null
     * @return
     */
    public boolean exists(String path) throws Exception {
        path = this.ftpdir + path;
        System.err.println("判断文件是否存在");
        return Files.exists(fs.getDefaultDir().resolve(path));
    }

    /* *
     * @描述：ls目录
     * @param ftpdir
     * @return java.util.List<java.nio.file.Path>
     */
    public List<Path> list(String ftpdir) throws Exception {
        ftpdir = this.ftpdir + ftpdir;
        Path remoteFile = fs.getDefaultDir().resolve(ftpdir);
        return Files.list(remoteFile).collect(Collectors.toList());
    }

    /*下载文件,并流式返回*/
    public static RespBean downloadFile(String filename, final HttpServletResponse response, final HttpServletRequest request) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            // 取得输出流
            outputStream = response.getOutputStream();
            // 清空输出流
            response.reset();
//            response.setContentType("application/x-download;charset=GBK");
            String s = MimeUtility.encodeWord(filename);
            response.setHeader("Content-Disposition", "attachment;filename=" + s);
            //下载文件
            SftpOperator sftpOperator = new SftpOperator();
            try {
                sftpOperator.login();
                if (sftpOperator.exists(filename)) {
                    System.err.println("下载文件名"+filename);
                    sftpOperator.download(filename, outputStream);
                } else {
                    sftpOperator.download("default.jpg", outputStream);
                }
                sftpOperator.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //刷新
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
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
        return RespBean.ok("下载成功");
    }

    /*上传文件*/
    public static RespBean uploadFile(String rename, MultipartFile multipartFile, final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        SftpOperator sftpOperator = new SftpOperator();
        try {
            if (multipartFile != null) {
                sftpOperator.login();
                sftpOperator.upload(rename, multipartFile.getInputStream());
            }
            sftpOperator.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("上传 成功");
    }

    /*下載文件，用流的方式下载，流的方式赶回*/
    public static RespBean downloadFile(MultipartFile multipartFile, final HttpServletResponse response, final HttpServletRequest request) {
        OutputStream outputStream = null;
        try {
            // 取得输出流
            outputStream = response.getOutputStream();
            /*清空输出流*/
            response.reset();
//            response.setContentType("application/x-download;charset=GBK");
//            response.setContentType("application/x-download;");
//            response.setCharacterEncoding("UTF-8");
            /*必须河阳传回名字，因为....中文乱码*/
            String s = MimeUtility.encodeWord(multipartFile.getOriginalFilename());
            response.setHeader("Content-Disposition", "attachment;filename=" + s);
            System.err.println("originalname" + multipartFile.getOriginalFilename());
            try {
                if (multipartFile != null) {
                    String[] split = multipartFile.getOriginalFilename().split("\\.");
                    File file = File.createTempFile(split[0], "." + split[1]);
                    multipartFile.transferTo(file);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    Files.copy(file.toPath(), outputStream);
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("下载失败");
            }
            //刷新
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error("下载失败");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RespBean.ok("下载成功");
    }

    /*下載文件,file，用流的方式下载，流的方式赶回*/
    public static RespBean downloadFile(File file, final HttpServletResponse response, final HttpServletRequest request) {
        OutputStream outputStream = null;
        try {
            // 取得输出流
            outputStream = response.getOutputStream();
            /*清空输出流*/
            response.reset();
//            response.setContentType("application/x-download;charset=GBK");
            String s = MimeUtility.encodeWord(file.getName());
            response.setHeader("Content-Disposition", "attachment;filename=" + s);

            System.err.println("originalname" + file.getName());
            try {
                if (file != null) {
                    Files.copy(file.toPath(), outputStream);
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return RespBean.error("下载失败");
            }
            //刷新
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error("下载失败");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RespBean.ok("下载成功");
    }
}
