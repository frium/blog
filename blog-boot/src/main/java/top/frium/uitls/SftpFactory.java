package top.frium.uitls;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import com.jcraft.jsch.*;

public class SftpFactory implements PooledObjectFactory<ChannelSftp> {

    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public SftpFactory(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public PooledObject<ChannelSftp> makeObject() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(30000);
        Channel channel = session.openChannel("sftp");
        channel.connect(1000);
        return new DefaultPooledObject<>((ChannelSftp) channel);
    }

    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) throws JSchException {
        ChannelSftp sftp = p.getObject();
        if (sftp.isConnected()) sftp.disconnect();
        Session session = sftp.getSession();
        if (session.isConnected()) session.disconnect();
    }

    @Override
    public boolean validateObject(PooledObject<ChannelSftp> p) {
        try {
            p.getObject().stat(".");
            return true;
        } catch (SftpException e) {
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<ChannelSftp> p) {}
    @Override
    public void passivateObject(PooledObject<ChannelSftp> p) {}
}