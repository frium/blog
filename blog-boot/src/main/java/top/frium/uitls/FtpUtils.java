package top.frium.uitls;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import javax.annotation.PostConstruct;
import java.io.OutputStream;

@Component
public class FtpUtils {
    @Value("${ecs.ip}") private String ip;
    @Value("${ecs.port}") private Integer port;
    @Value("${ecs.user}") private String user;
    @Value("${ecs.password}") private String password;
    @Value("${ecs.path}") private String path;

    private SftpPool sftpPool;

    @PostConstruct
    public void init() {
        this.sftpPool = new SftpPool(ip, port, user, password);
    }

    public boolean fileExists(String relativePath) {
        ChannelSftp sftp = null;
        try {
            sftp = sftpPool.borrowObject();
            sftp.stat(path + "/" + relativePath);
            return true;
        } catch (SftpException e) {
            return e.id != ChannelSftp.SSH_FX_NO_SUCH_FILE;
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        } finally {
            if (sftp != null) sftpPool.returnObject(sftp);
        }
    }

    // FtpUtils.java 修改后关键代码
    public void sshSftp(byte[] file, String fileName) {
        ChannelSftp sftp = null;
        try {
            sftp = sftpPool.borrowObject();
            try {
                sftp.cd(path);
            } catch (SftpException e) {
                sftp.mkdir(path);
                sftp.cd(path);
            }

            try (OutputStream out = sftp.put(fileName)) {
                out.write(file);
                out.flush();
            }
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.ERROR);
        } finally {
            if (sftp != null) {
                try {
                    sftpPool.returnObject(sftp); // 确保连接归还
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}