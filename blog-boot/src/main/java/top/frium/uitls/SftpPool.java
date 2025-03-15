package top.frium.uitls;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import com.jcraft.jsch.ChannelSftp;

public class SftpPool {
    private final GenericObjectPool<ChannelSftp> pool;

    public SftpPool(String host, int port, String user, String password) {
        GenericObjectPoolConfig<ChannelSftp> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(20);
        config.setMinIdle(5);
        config.setMaxWaitMillis(5000);
        config.setTestOnBorrow(true);

        this.pool = new GenericObjectPool<>(
                new SftpFactory(host, port, user, password),
                config
        );
    }

    public ChannelSftp borrowObject() throws Exception {
        return pool.borrowObject();
    }

    public void returnObject(ChannelSftp sftp) {
        pool.returnObject(sftp);
    }
}