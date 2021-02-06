package com.zty.springboot01login.Utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class UseSSH {
    // 华为云
    private static String ip="124.70.84.98";
    private static int port=22;
    private static String user="root";
    private static String pswd="Zty981115";
    public void connect(String newport){
        try {
            Connection connection=new Connection(ip,port);
            connection.connect();
            boolean isAuthenticated=connection.authenticateWithPassword(user,pswd);
            if(!isAuthenticated){
                throw new Exception("Authenticated faild");
            }
            Session session=connection.openSession();
//            session.execCommand("docker ps -a");
//            session.execCommand("docker stop $(docker ps -a | grep "+newport+") &&" +
//                            "docker rm $(docker ps -a | grep"+newport+" && "+
//                    "docker run -p "+newport+":80 -e RESOLUTION=1960x1080 -d -v /dev/shm:/dev/shm zty/novnc-vscode\n");

            session.execCommand("docker stop $(docker ps -l -q) && docker rm $(docker ps -a -l -q) && docker run -p "+newport+":80 -e RESOLUTION=1960x1080 -d -v /dev/shm:/dev/shm zty/novnc-vscode\n");
            InputStream stdout=new StreamGobbler(session.getStdout());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stdout));
            while (true){
                String line=bufferedReader.readLine();
                if(line==null) break;
                System.out.println(line);
            }
            session.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
