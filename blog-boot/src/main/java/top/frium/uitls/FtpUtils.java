package top.frium.uitls;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;

import java.io.OutputStream;

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

    private Session session;
    private ChannelSftp sftp;

    /**
     * 确保 SFTP 连接可用
     */
    private void ensureConnected() throws JSchException {
        if (sftp != null && sftp.isConnected()) {
            return; // 连接已建立
        }
        connect();
    }

    /**
     * 连接 SFTP 服务器
     */
    private void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(user, ip, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(30000);
        Channel channel = session.openChannel("sftp");
        channel.connect(1000);
        sftp = (ChannelSftp) channel;
    }

    /**
     * 断开 SFTP 连接
     */
    public void disconnect() {
        if (sftp != null && sftp.isConnected()) {
            sftp.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    /**
     * 检查文件是否存在
     */
    public boolean fileExists(String relativePath) {
        try {
            ensureConnected();
            String fullPath = path + "/" + relativePath;
            sftp.stat(fullPath);
            return true;
        } catch (SftpException e) {
            return e.id != ChannelSftp.SSH_FX_NO_SUCH_FILE;
        } catch (JSchException e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
    }

    /**
     * 上传文件到 SFTP 服务器
     */
    public void sshSftp(byte[] file, String fileName) {
        OutputStream outstream = null;
        try {
            ensureConnected();
            sftp.cd(path);
            outstream = sftp.put(fileName);
            outstream.write(file);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        } finally {
            try {
                if (outstream != null) {
                    outstream.flush();
                    outstream.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}
