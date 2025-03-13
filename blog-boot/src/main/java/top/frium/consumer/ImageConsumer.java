package top.frium.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.context.RabbitMQConstant;
import top.frium.uitls.FtpUtils;


import java.util.Base64;
import java.util.Map;

/**
 *
 * @date 2024-08-13 16:13:30
 * @description
 */
@Slf4j
@Component
public class ImageConsumer {
    @Autowired
    FtpUtils ftpUtils;

    @RabbitListener(queues = RabbitMQConstant.IMAGE_QUEUE)
    public void uploadImage(Map<String, Object> message) {
        try {
            String fileName = (String) message.get("fileName");
            log.info("存储文件" + fileName);
            String fileBase64 = (String) message.get("file");
            byte[] file = Base64.getDecoder().decode(fileBase64);
            ftpUtils.sshSftp(file, fileName);
        } catch (Exception e) {
           throw  new MyException(StatusCodeEnum.ERROR);
        }
    }
}
