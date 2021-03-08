package com.zty.springboot01login.Utils;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.apache.sshd.sftp.client.fs.SftpFileSystem;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
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

    private String host = "124.70.84.98";
    private int port = 22;
    private String username = "root";
    private String password = "Zty981115";

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
        if (session.auth().verify(3000).isFailure()) {
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
}
