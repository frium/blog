package top.frium.uitls;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

/**
 * Created by jlm on 2019-09-17 17:44
 */
@Component
public class FtpUtils {
    @Value("${ecs.ip}")
    private String ip;
    @Value("${ecs.user}")
    private String user;
    @Value("${ecs.password}")
    private String password;
    @Value("${ecs.port}")
    private Integer port;
    @Value("${ecs.path}")
    private String path;

    /**
     * 利用JSch包实现SFTP上传文件
     */
    public void sshSftp(byte[] file, String fileName) throws Exception {
        Session session;
        Channel channel = null;
        JSch jsch = new JSch();
        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip, port);
        }
        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(password);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);
        OutputStream outstream = null;
        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;
            //进入服务器指定的文件夹
            sftp.cd(path);
            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            outstream = sftp.put(fileName);
            outstream.write(file);
        } catch (Exception e) {
            throw new Exception();
        } finally {
            //关流操作
            if (outstream != null) {
                outstream.flush();
                outstream.close();
            }
            session.disconnect();
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

}